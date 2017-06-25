package korenski.service.infrastruktura;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import korenski.model.infrastruktura.AnalitikaIzvoda;
import korenski.model.infrastruktura.Bank;
import korenski.model.infrastruktura.DnevnoStanjeRacuna;
import korenski.model.infrastruktura.MedjubankarskiPrenos;
import korenski.model.infrastruktura.Racun;
import korenski.model.infrastruktura.StavkaPrenosa;
import korenski.repository.institutions.AnalitikaIzvodaRepository;
import korenski.repository.institutions.BankRepository;
import korenski.repository.institutions.DnevnoStanjeRepository;
import korenski.repository.institutions.MedjubankarskiPrenosRepository;
import korenski.repository.institutions.RacunRepository;
import korenski.repository.institutions.StavkaPrenosaRepository;
import korenski.repository.sifrarnici.MessageRepository;
import korenski.repository.soap.ClearingOdgovorRepository;
import korenski.repository.soap.ClearingRepository;
import korenski.repository.soap.RtgsOdgovorRepository;
import korenski.repository.soap.RtgsRepository;
import korenski.soap.PoslovnaKlijent;
import korenski.soap.clearing.Clearing;
import korenski.soap.clearing.ClearingRequest;
import korenski.soap.nalozi_model.NalogRequest;
import korenski.soap.nalozi_model.NalogZaPrenos;
import korenski.soap.nalozi_model.TFinansijskiPodaci;
import korenski.soap.rtgs.Poruka;
import korenski.soap.rtgs.RtgsRequest;

@Service
public class BusinessLogicService {

	@Autowired
	RacunRepository racunRepository;
	@Autowired
	BankRepository bankRepository;
	@Autowired
	DnevnoStanjeRepository dnevnoStanjeRepository;
	@Autowired
	AnalitikaIzvodaRepository analitikaIzvodaRepository;
	@Autowired
	MedjubankarskiPrenosRepository mBRepository;
	@Autowired
	StavkaPrenosaRepository sPRepository;
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	PoslovnaKlijent poslovnaKlijent;
	
	@Autowired
	private ClearingRepository clearingRepository;
	
	@Autowired
	private ClearingOdgovorRepository clearingOdgovorRepository;
	
	@Autowired
	private RtgsRepository rtgsRepository;
	
	@Autowired
	private RtgsOdgovorRepository rtgsOdgovorRepository;
	
	private PrenosHelper ph = new PrenosHelper();
	
	public void processing(NalogRequest request){
		
		TFinansijskiPodaci podaciDuznika = request.getNalog().getPodaciOPlacanju().getFinansijskiPodaciDuznik();
		TFinansijskiPodaci podaciPoverioca = request.getNalog().getPodaciOPlacanju().getFinansijskiPodaciPoverilac();

		String racunDuznika = podaciDuznika.getBrojRacuna();
		String racunPoverioca = podaciPoverioca.getBrojRacuna();
		
		Racun duznik = racunRepository.findByBrojRacuna(racunDuznika);
		Racun poverilac = racunRepository.findByBrojRacuna(racunPoverioca);

		if (duznik == null && poverilac == null) {
			System.out.println("Nije moja banka");
			return;
		}
		
		if (duznik != null && poverilac != null && duznik.getStatus() && poverilac.getStatus()) {
			
			System.out.println("Racuni su iz iste banke!");
			try {
				sameBankTransfer(request.getNalog(), duznik, poverilac);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if (duznik != null && poverilac == null
				&& duznik.getStanje() >= request.getNalog().getPodaciOPlacanju().getIznos().doubleValue()  && duznik.getStatus()) {
			
			System.out.println("Racuni su iz razlicitih banaka");
			try {
				differentBanksTransfer(request.getNalog(), duznik, racunPoverioca);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} 		
	}

	public boolean sameBankTransfer(NalogZaPrenos nalog, Racun racunDuznika, Racun racunPoverioca)
			throws ParseException {
		
		
		if(!(racunDuznika.getStanje() >= nalog.getPodaciOPlacanju().getIznos().doubleValue())){
			return false;
		}
		
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		Date today = new Date();
		Date todayWithZeroTime = null;
		try {
			todayWithZeroTime = formatter.parse(formatter.format(today));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		DnevnoStanjeRacuna dnevnoStanjeDuznika = dnevnoStanjeRepository.findByDatumAndRacun(todayWithZeroTime,
				racunDuznika);
		DnevnoStanjeRacuna dnevnoStanjePoverioca = dnevnoStanjeRepository.findByDatumAndRacun(todayWithZeroTime,
				racunPoverioca);

		if (dnevnoStanjeDuznika == null) {
			dnevnoStanjeDuznika = new DnevnoStanjeRacuna(todayWithZeroTime, racunDuznika.getStanje(), 0, 0,
					racunDuznika.getStanje(), racunDuznika);
			racunDuznika.getDnevnaStanjaRacuna().add(dnevnoStanjeDuznika);
			try {
				dnevnoStanjeRepository.save(dnevnoStanjeDuznika);
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}

		if (dnevnoStanjePoverioca == null) {
			dnevnoStanjePoverioca = new DnevnoStanjeRacuna(new Date(), racunPoverioca.getStanje(), 0, 0,
					racunPoverioca.getStanje(), racunPoverioca);
			racunPoverioca.getDnevnaStanjaRacuna().add(dnevnoStanjePoverioca);
			try {
				dnevnoStanjeRepository.save(dnevnoStanjePoverioca);
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}

		boolean hitno;

		if (nalog.getHitno().equals("Da")) {
			hitno = true;
		} else {
			hitno = false;
		}

		//nalog.getPodaciOPlacanju().getDatumValute()
		//nalog.getPodaciOPlacanju().getDatumValute()
		AnalitikaIzvoda analitikaDuznika = new AnalitikaIzvoda(new Date(new Date().getTime()), "T",
				nalog.getDuznikNalogodavac(), nalog.getSvrhaPlacanja(),
				nalog.getPrimalacPoverilac(), null,
				null, racunDuznika.getBrojRacuna(),
				nalog.getPodaciOPlacanju().getFinansijskiPodaciDuznik().getModel(),
				nalog.getPodaciOPlacanju().getFinansijskiPodaciDuznik().getPozivNaBroj(),
				racunPoverioca.getBrojRacuna(), nalog.getPodaciOPlacanju().getFinansijskiPodaciPoverilac().getModel(),
				nalog.getPodaciOPlacanju().getFinansijskiPodaciPoverilac().getPozivNaBroj(),
				nalog.getPodaciOPlacanju().getIznos().doubleValue(), nalog.getPodaciOPlacanju().getOznakaValute(), hitno,
				dnevnoStanjeDuznika, racunDuznika.getStanje());

		AnalitikaIzvoda analitikaPoverioca = new AnalitikaIzvoda(new Date(new Date().getTime()), "K",
				nalog.getDuznikNalogodavac(), nalog.getSvrhaPlacanja(),
				nalog.getPrimalacPoverilac(), null,
				null, racunDuznika.getBrojRacuna(),
				nalog.getPodaciOPlacanju().getFinansijskiPodaciDuznik().getModel(),
				nalog.getPodaciOPlacanju().getFinansijskiPodaciDuznik().getPozivNaBroj(),
				racunPoverioca.getBrojRacuna(), nalog.getPodaciOPlacanju().getFinansijskiPodaciPoverilac().getModel(),
				nalog.getPodaciOPlacanju().getFinansijskiPodaciPoverilac().getPozivNaBroj(),
				nalog.getPodaciOPlacanju().getIznos().doubleValue(), nalog.getPodaciOPlacanju().getOznakaValute(), hitno,
				dnevnoStanjePoverioca, racunPoverioca.getStanje());

		// if((dnevnoStanjeDuznika.getNovoStanje()-analitikaDuznika.getIznos())
		// < 0){
		// System.out.println("Nema dovoljno sredstava!");
		// return;
		// }

		try {
			analitikaDuznika=analitikaIzvodaRepository.save(analitikaDuznika);
			dnevnoStanjeDuznika.setNovoStanje(dnevnoStanjeDuznika.getNovoStanje() - analitikaDuznika.getIznos());
			dnevnoStanjeDuznika.setPrometNaTeret(dnevnoStanjeDuznika.getPrometNaTeret() + analitikaDuznika.getIznos());
			dnevnoStanjeDuznika.getAnalitike().add(analitikaDuznika);
			dnevnoStanjeRepository.save(dnevnoStanjeDuznika);

			
			racunDuznika.setStanje(racunDuznika.getStanje()-nalog.getPodaciOPlacanju().getIznos().doubleValue());
			racunPoverioca.setStanje(racunPoverioca.getStanje()+nalog.getPodaciOPlacanju().getIznos().doubleValue());
			
			racunRepository.save(racunDuznika);
			racunRepository.save(racunPoverioca);
			
			analitikaPoverioca=analitikaIzvodaRepository.save(analitikaPoverioca);
			dnevnoStanjePoverioca.setNovoStanje(dnevnoStanjePoverioca.getNovoStanje() + analitikaPoverioca.getIznos());
			dnevnoStanjePoverioca
					.setPrometUKorist(dnevnoStanjePoverioca.getPrometUKorist() + analitikaPoverioca.getIznos());
			dnevnoStanjePoverioca.getAnalitike().add(analitikaPoverioca);
			dnevnoStanjeRepository.save(dnevnoStanjePoverioca);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	/**
	 * Metoda koja obradjuje nalog u slucaju da se racun poverioca ne nalazi u
	 * istoj banci u kojoj je i duznik. Provjerava se status naloga da li je
	 * hitno ili je iznos veci od 250000. Ukoliko je slucaj jedan od ta dva
	 * ucitava se MT103 koja oznacava da se transfer obavlja u RTGS rezimu,a
	 * ukolio ovo nije slucaj ucitava se poruka MT102 i dodjeljuje prenosu sto
	 * je oznacava kao poruku za obradu kliring rezimu rada.
	 * 
	 * @param nalog
	 *            nalog koji se obradjuje
	 * @param racunDuznika
	 *            racun duznika iz nase banke za koji se generise analiti,
	 *            azurira dnevno stanje i stanje racuna
	 * @param racunPoverioca
	 *            broj racuna poverioca koji se nalazi u nekoj drugoj banci.
	 * @author Biljana
	 * @throws ParseException
	 */
	public boolean differentBanksTransfer(NalogZaPrenos nalog, Racun racunDuznika, String racunPoverioca)
			throws ParseException {

		if(!(racunDuznika.getStanje()-racunDuznika.getRezervisano() >= nalog.getPodaciOPlacanju().getIznos().doubleValue())){
			return false;
		}
		
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		Date today = new Date();
		Date todayWithZeroTime = null;
		boolean rtgs=false;
		try {
			todayWithZeroTime = formatter.parse(formatter.format(today));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		DnevnoStanjeRacuna dnevnoStanjeDuznika = dnevnoStanjeRepository.findByDatumAndRacun(todayWithZeroTime,
				racunDuznika);

		if (dnevnoStanjeDuznika == null) {
			dnevnoStanjeDuznika = new DnevnoStanjeRacuna(todayWithZeroTime, racunDuznika.getStanje(), 0, 0,
					racunDuznika.getStanje(), racunDuznika);
			List<DnevnoStanjeRacuna> dnevnaStanja = racunDuznika.getDnevnaStanjaRacuna();
			try {
				dnevnaStanja.add(dnevnoStanjeDuznika);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				dnevnoStanjeRepository.save(dnevnoStanjeDuznika);
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}

		String code = racunPoverioca.substring(0, 3);

		Bank bankaDruga = bankRepository.findByCode(code);
		
		Date maxDate = mBRepository.findMaxDate(racunDuznika.getBank(), bankaDruga);
		System.out.println("MaxDate " + maxDate);
		MedjubankarskiPrenos latestMBPrenos = mBRepository.findLatestMedjubankarskiPrenos(maxDate);
		Set<StavkaPrenosa> stavkePrenosa = sPRepository.findStavkaPrenosaByMedjubankarskiPrenos(latestMBPrenos);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date newDate = new Date();
		try {
			newDate = sdf.parse(sdf.format(today));
		} catch (ParseException e1) {
			
			e1.printStackTrace();
			return false;
		}
		
		
		
		AnalitikaIzvoda analitikaDuznika = new AnalitikaIzvoda(newDate, "T", nalog.getDuznikNalogodavac(),
				nalog.getSvrhaPlacanja(), nalog.getPrimalacPoverilac(),
				null, null,
				racunDuznika.getBrojRacuna(), nalog.getPodaciOPlacanju().getFinansijskiPodaciDuznik().getModel(),
				nalog.getPodaciOPlacanju().getFinansijskiPodaciDuznik().getPozivNaBroj(), racunPoverioca,
				nalog.getPodaciOPlacanju().getFinansijskiPodaciPoverilac().getModel(),
				nalog.getPodaciOPlacanju().getFinansijskiPodaciPoverilac().getPozivNaBroj(),
				nalog.getPodaciOPlacanju().getIznos().doubleValue(), nalog.getPodaciOPlacanju().getOznakaValute(), false,
				dnevnoStanjeDuznika, racunDuznika.getStanje());
		if (nalog.getHitno().equals("Da")) {
			analitikaDuznika.setHitno(true);
		}
		dnevnoStanjeDuznika.setPrometNaTeret(nalog.getPodaciOPlacanju().getIznos().doubleValue());
		dnevnoStanjeDuznika.setNovoStanje(dnevnoStanjeDuznika.getNovoStanje() - nalog.getPodaciOPlacanju().getIznos().doubleValue());

		try {
			
			dnevnoStanjeDuznika=dnevnoStanjeRepository.save(dnevnoStanjeDuznika);
			analitikaDuznika.setDnevnoStanjeRacuna(dnevnoStanjeDuznika);
			analitikaDuznika = analitikaIzvodaRepository.save(analitikaDuznika);

			racunDuznika.setRezervisano(racunDuznika.getRezervisano() + nalog.getPodaciOPlacanju().getIznos().doubleValue());
			Hibernate.initialize(dnevnoStanjeDuznika.getAnalitike());
			dnevnoStanjeDuznika.getAnalitike().add(analitikaDuznika);
			dnevnoStanjeRepository.save(dnevnoStanjeDuznika);
			racunRepository.save(racunDuznika);

		} catch (Exception e) {
			System.out.println("Problem sa cuvanjem analitika izvoda");
			return false;
		}

		if (maxDate == null || latestMBPrenos == null || stavkePrenosa.size() == 2 || nalog.getHitno().equals("Da")) {
			latestMBPrenos = new MedjubankarskiPrenos();
			long start = System.currentTimeMillis();
			long end = start + 1000; // 60 seconds * 1000 ms/sec
			while (System.currentTimeMillis() < end) {
				
			}
			latestMBPrenos.setDatum(new Timestamp((new Date().getTime())));
			latestMBPrenos.setBankaPrva(racunDuznika.getBank());

			latestMBPrenos.setBankaDruga(bankaDruga);
			latestMBPrenos.setIznos(nalog.getPodaciOPlacanju().getIznos().doubleValue());
			if (nalog.getPodaciOPlacanju().getIznos().doubleValue() > 250000 || nalog.getHitno().equals("Da")) {
				latestMBPrenos.setPoruka(messageRepository.findOne(new Long(2)));
				rtgs=true;
			} else {
				latestMBPrenos.setPoruka(messageRepository.findOne(new Long(1)));
			}
			try {

				MedjubankarskiPrenos newMedjubankarskiPrenos = mBRepository.save(latestMBPrenos);
				System.out.println("Uspijesno cuvanje medjubankarskog prenosa");
			} catch (Exception e) {
				return false;
			}
		} else {
			latestMBPrenos.setIznos(latestMBPrenos.getIznos() + nalog.getPodaciOPlacanju().getIznos().doubleValue());
		}

		StavkaPrenosa stavkaPrenosa = new StavkaPrenosa();
		stavkaPrenosa.setAnalitikaIzvoda(analitikaDuznika);

		try {

			mBRepository.save(latestMBPrenos);
			stavkaPrenosa.setStavkaPrenosa(latestMBPrenos);
			stavkaPrenosa = sPRepository.save(stavkaPrenosa);
			Hibernate.initialize(latestMBPrenos.getStavkePrenosa());
			latestMBPrenos.addStavkaPrenosa(stavkaPrenosa);
			latestMBPrenos=mBRepository.save(latestMBPrenos);
			System.out.println("**********************************************");
			System.out.println("Perzistovanje medjubankarskog prenosa");
			System.out.println("**********************************************");
			if(rtgs){
				RtgsRequest rr = ph.repackToRtgs(latestMBPrenos);
				Poruka rtgsResponse = null;
				try {
					rtgsResponse = poslovnaKlijent.posaljiRtgs(rr);
				} catch (Exception e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
					
				}
				
				try {
					rr.getZahtev().setId(null);
					rtgsResponse.setId(null);
					rtgsResponse.getBanka().setId(null);
					
					rtgsRepository.save(rr.getZahtev());
					rtgsOdgovorRepository.save(rtgsResponse);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				racunDuznika = racunRepository.findOne(racunDuznika.getId());
				racunDuznika.setStanje(racunDuznika.getStanje()-nalog.getPodaciOPlacanju().getIznos().doubleValue());
				racunDuznika.setRezervisano(racunDuznika.getRezervisano() - nalog.getPodaciOPlacanju().getIznos().doubleValue());
				
				try {
					racunRepository.save(racunDuznika);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				latestMBPrenos.setSend(true);
				try {
					mBRepository.save(latestMBPrenos);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else{
				if(latestMBPrenos.getStavkePrenosa().size() == 2){
					ClearingRequest cr = ph.repackToClearing(latestMBPrenos);
					korenski.soap.clearing.Poruka clearingResponse =null;
					try {
						clearingResponse = poslovnaKlijent.posaljiClearing(cr);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					try {
						cr.getZahtev().setId(null);
						clearingResponse.setId(null);
						clearingResponse.getBanka().setId(null);
						
						clearingRepository.save(cr.getZahtev());
						clearingOdgovorRepository.save(clearingResponse);
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					
					latestMBPrenos.setSend(true);
					Racun r ;
					for(Clearing.StavkePrenosa.StavkaPrenosa st : cr.getZahtev().getStavkePrenosa().getStavkaPrenosa()){
						
						r = racunRepository.findByBrojRacuna(st.getPodaciOPlacanju().getFinansijskiPodaciDuznik().getBrojRacuna());
						r.setStanje(r.getStanje()-st.getPodaciOPlacanju().getIznos().doubleValue());
						r.setRezervisano(r.getRezervisano()-st.getPodaciOPlacanju().getIznos().doubleValue());
						
						try {
							racunRepository.save(r);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
					try {
						mBRepository.save(latestMBPrenos);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	
	public boolean otherBankPayment(NalogZaPrenos nalog,Racun racunPoverioca)
			throws ParseException {
//		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//
//		Date today = new Date();
//		Date todayWithZeroTime = null;
//		try {
//			todayWithZeroTime = formatter.parse(formatter.format(today));
//		} catch (ParseException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//			return false;
//		}
//		
//		DnevnoStanjeRacuna dnevnoStanjePoverioca = dnevnoStanjeRepository.findByDatumAndRacun(todayWithZeroTime,
//				racunPoverioca);
//
//		
//
//		if (dnevnoStanjePoverioca == null) {
//			dnevnoStanjePoverioca = new DnevnoStanjeRacuna(new Date(), racunPoverioca.getStanje(), 0, 0,
//					racunPoverioca.getStanje(), racunPoverioca);
//			racunPoverioca.getDnevnaStanjaRacuna().add(dnevnoStanjePoverioca);
//			try {
//				dnevnoStanjeRepository.save(dnevnoStanjePoverioca);
//			} catch (Exception e) {
//				// TODO: handle exception
//				return false;
//			}
//		}
//
//		boolean hitno;
//
//		if (nalog.getHitno().equals("Da")) {
//			hitno = true;
//		} else {
//			hitno = false;
//		}
//
//		
//
//		AnalitikaIzvoda analitikaPoverioca = new AnalitikaIzvoda(new Date(new Date().getTime()), "K",
//				nalog.getPodaciODuzniku().getAdresa(), nalog.getSvrhaPlacanja(),
//				nalog.getPodaciOPoveriocu().getAdresa(), nalog.getPodaciOPlacanju().getDatumValute(),
//				nalog.getPodaciOPlacanju().getDatumValute(), nalog.getPodaciOPlacanju().getFinansijskiPodaciDuznik().getBrojRacuna(),
//				nalog.getPodaciOPlacanju().getFinansijskiPodaciDuznik().getModel(),
//				nalog.getPodaciOPlacanju().getFinansijskiPodaciDuznik().getPozivNaBroj(),
//				racunPoverioca.getBrojRacuna(), nalog.getPodaciOPlacanju().getFinansijskiPodaciPoverilac().getModel(),
//				nalog.getPodaciOPlacanju().getFinansijskiPodaciPoverilac().getPozivNaBroj(),
//				nalog.getPodaciOPlacanju().getIznos(), nalog.getPodaciOPlacanju().getValuta(), hitno,
//				dnevnoStanjePoverioca);
//
//		
//		try {
//			
//			racunPoverioca.setStanje(racunPoverioca.getStanje()+nalog.getPodaciOPlacanju().getIznos());
//			
//			racunRepository.save(racunPoverioca);
//			
//			analitikaPoverioca=analitikaIzvodaRepository.save(analitikaPoverioca);
//			dnevnoStanjePoverioca.setNovoStanje(dnevnoStanjePoverioca.getNovoStanje() + analitikaPoverioca.getIznos());
//			dnevnoStanjePoverioca
//					.setPrometUKorist(dnevnoStanjePoverioca.getPrometUKorist() + analitikaPoverioca.getIznos());
//			dnevnoStanjePoverioca.getAnalitike().add(analitikaPoverioca);
//			dnevnoStanjeRepository.save(dnevnoStanjePoverioca);
//		} catch (Exception e) {
//			// TODO: handle exception
//			return false;
//		}
		return true;
	}
	
	public boolean uplata(NalogZaPrenos  nalog, Racun racunPoverioca){
	
//			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//
//			Date today = new Date();
//			Date todayWithZeroTime = null;
//			try {
//				todayWithZeroTime = formatter.parse(formatter.format(today));
//			} catch (ParseException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//				e1.printStackTrace();
//				return false;
//			}
//			
//			DnevnoStanjeRacuna dnevnoStanjePoverioca = dnevnoStanjeRepository.findByDatumAndRacun(todayWithZeroTime,
//					racunPoverioca);
//
//			
//
//			if (dnevnoStanjePoverioca == null) {
//				dnevnoStanjePoverioca = new DnevnoStanjeRacuna(new Date(), racunPoverioca.getStanje(), 0, 0,
//						racunPoverioca.getStanje(), racunPoverioca);
//				racunPoverioca.getDnevnaStanjaRacuna().add(dnevnoStanjePoverioca);
//				try {
//					dnevnoStanjeRepository.save(dnevnoStanjePoverioca);
//				} catch (Exception e) {
//					e.printStackTrace();
//					return false;
//				}
//			}
//			boolean hitno;
//			if (nalog.getHitno().equals("Da")) {
//				hitno = true;
//			} else {
//				hitno = false;
//			}
//
//			AnalitikaIzvoda analitikaPoverioca = new AnalitikaIzvoda(new Date(new Date().getTime()), "K",
//					nalog.getPodaciODuzniku().getAdresa(), nalog.getSvrhaPlacanja(),
//					nalog.getPodaciOPoveriocu().getAdresa(), nalog.getPodaciOPlacanju().getDatumValute(),
//					nalog.getPodaciOPlacanju().getDatumValute(),null,
//					null,
//					null,
//					racunPoverioca.getBrojRacuna(), nalog.getPodaciOPlacanju().getFinansijskiPodaciPoverilac().getModel(),
//					nalog.getPodaciOPlacanju().getFinansijskiPodaciPoverilac().getPozivNaBroj(),
//					nalog.getPodaciOPlacanju().getIznos(), nalog.getPodaciOPlacanju().getValuta(), hitno,
//					dnevnoStanjePoverioca);
//			
//			try {
//				
//				racunPoverioca.setStanje(racunPoverioca.getStanje()+nalog.getPodaciOPlacanju().getIznos());
//				
//				racunRepository.save(racunPoverioca);
//				
//				analitikaPoverioca=analitikaIzvodaRepository.save(analitikaPoverioca);
//				dnevnoStanjePoverioca.setNovoStanje(dnevnoStanjePoverioca.getNovoStanje() + analitikaPoverioca.getIznos());
//				dnevnoStanjePoverioca
//						.setPrometUKorist(dnevnoStanjePoverioca.getPrometUKorist() + analitikaPoverioca.getIznos());
//				dnevnoStanjePoverioca.getAnalitike().add(analitikaPoverioca);
//				dnevnoStanjeRepository.save(dnevnoStanjePoverioca);
//			} catch (Exception e) {
//				// TODO: handle exception
//				return false;
//			}
//		
//			
			return true;
	}
	
	public boolean isplata(NalogZaPrenos nalog, Racun racunDuznika){
		
		
//		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//
//		Date today = new Date();
//		Date todayWithZeroTime = null;
//		try {
//			todayWithZeroTime = formatter.parse(formatter.format(today));
//		} catch (ParseException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//			return false;
//		}
//		DnevnoStanjeRacuna dnevnoStanjeDuznika = dnevnoStanjeRepository.findByDatumAndRacun(todayWithZeroTime,
//				racunDuznika);
////		DnevnoStanjeRacuna dnevnoStanjePoverioca = dnevnoStanjeRepository.findByDatumAndRacun(todayWithZeroTime,
////				racunPoverioca);
//
//		if (dnevnoStanjeDuznika == null) {
//			dnevnoStanjeDuznika = new DnevnoStanjeRacuna(todayWithZeroTime, racunDuznika.getStanje(), 0, 0,
//					racunDuznika.getStanje(), racunDuznika);
//			racunDuznika.getDnevnaStanjaRacuna().add(dnevnoStanjeDuznika);
//			try {
//				dnevnoStanjeRepository.save(dnevnoStanjeDuznika);
//			} catch (Exception e) {
//				// TODO: handle exception
//				return false;
//			}
//		}
//
////		if (dnevnoStanjePoverioca == null) {
////			dnevnoStanjePoverioca = new DnevnoStanjeRacuna(new Date(), racunPoverioca.getStanje(), 0, 0,
////					racunPoverioca.getStanje(), racunPoverioca);
////			racunPoverioca.getDnevnaStanjaRacuna().add(dnevnoStanjePoverioca);
////			try {
////				dnevnoStanjeRepository.save(dnevnoStanjePoverioca);
////			} catch (Exception e) {
////				// TODO: handle exception
////				return false;
////			}
////		}
//
//		boolean hitno;
//
//		if (nalog.getHitno().equals("Da")) {
//			hitno = true;
//		} else {
//			hitno = false;
//		}
//
//		AnalitikaIzvoda analitikaDuznika = new AnalitikaIzvoda(new Date(new Date().getTime()), "T",
//				nalog.getPodaciODuzniku().getAdresa(), nalog.getSvrhaPlacanja(),
//				nalog.getPodaciOPoveriocu().getAdresa(), nalog.getPodaciOPlacanju().getDatumValute(),
//				nalog.getPodaciOPlacanju().getDatumValute(), racunDuznika.getBrojRacuna(),
//				nalog.getPodaciOPlacanju().getFinansijskiPodaciDuznik().getModel(),
//				nalog.getPodaciOPlacanju().getFinansijskiPodaciDuznik().getPozivNaBroj(),
//				null, null,
//				null,
//				nalog.getPodaciOPlacanju().getIznos(), nalog.getPodaciOPlacanju().getValuta(), hitno,
//				dnevnoStanjeDuznika);
//
////		AnalitikaIzvoda analitikaPoverioca = new AnalitikaIzvoda(new Date(new Date().getTime()), "K",
////				nalog.getPodaciODuzniku().getAdresa(), nalog.getSvrhaPlacanja(),
////				nalog.getPodaciOPoveriocu().getAdresa(), nalog.getPodaciOPlacanju().getDatumValute(),
////				nalog.getPodaciOPlacanju().getDatumValute(), racunDuznika.getBrojRacuna(),
////				nalog.getPodaciOPlacanju().getFinansijskiPodaciDuznik().getModel(),
////				nalog.getPodaciOPlacanju().getFinansijskiPodaciDuznik().getPozivNaBroj(),
////				racunPoverioca.getBrojRacuna(), nalog.getPodaciOPlacanju().getFinansijskiPodaciPoverilac().getModel(),
////				nalog.getPodaciOPlacanju().getFinansijskiPodaciPoverilac().getPozivNaBroj(),
////				nalog.getPodaciOPlacanju().getIznos(), nalog.getPodaciOPlacanju().getValuta(), hitno,
////				dnevnoStanjePoverioca);
//
//		// if((dnevnoStanjeDuznika.getNovoStanje()-analitikaDuznika.getIznos())
//		// < 0){
//		// System.out.println("Nema dovoljno sredstava!");
//		// return;
//		// }
//
//		try {
//			
//			racunDuznika.setStanje(racunDuznika.getStanje()-nalog.getPodaciOPlacanju().getIznos());
//			
//			racunRepository.save(racunDuznika);
//			
//			analitikaDuznika=analitikaIzvodaRepository.save(analitikaDuznika);
//			dnevnoStanjeDuznika.setNovoStanje(dnevnoStanjeDuznika.getNovoStanje() - analitikaDuznika.getIznos());
//			dnevnoStanjeDuznika.setPrometNaTeret(dnevnoStanjeDuznika.getPrometNaTeret() + analitikaDuznika.getIznos());
//			dnevnoStanjeDuznika.getAnalitike().add(analitikaDuznika);
//			dnevnoStanjeRepository.save(dnevnoStanjeDuznika);
//
////			analitikaPoverioca=analitikaIzvodaRepository.save(analitikaPoverioca);
////			dnevnoStanjePoverioca.setNovoStanje(dnevnoStanjePoverioca.getNovoStanje() + analitikaPoverioca.getIznos());
////			dnevnoStanjePoverioca
////					.setPrometUKorist(dnevnoStanjePoverioca.getPrometUKorist() + analitikaPoverioca.getIznos());
////			dnevnoStanjePoverioca.getAnalitike().add(analitikaPoverioca);
////			dnevnoStanjeRepository.save(dnevnoStanjePoverioca);
//		} catch (Exception e) {
//			// TODO: handle exception
//			return false;
//		}
//		return true;
//	}
//	public boolean export(MedjubankarskiPrenos foundMedjubankarskiPrenos){
//		try{
//		//	MedjubankarskiPrenos foundMedjubankarskiPrenos=mbPrenosRepository.findOne(id);
//			File file = new File("./files/xmls/medjubankarskiPrenos"+foundMedjubankarskiPrenos.getId().toString()+".xml");
//			JAXBContext jaxbContext = JAXBContext.newInstance(MedjubankarskiPrenos.class);
//			Marshaller jaxbMarshaller =  jaxbContext.createMarshaller();
//			
//			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//			
//
//			jaxbMarshaller.marshal(foundMedjubankarskiPrenos, file);
//			foundMedjubankarskiPrenos.setSend(true);
//			mBRepository.save(foundMedjubankarskiPrenos);
//			//return new ResponseEntity<String>("OK",HttpStatus.OK);
//			return true;
//		}catch (Exception e) {
//			// TODO: handle exception
			return false;
		//}
		
	}
}
