package korenski.service.infrastruktura;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import korenski.model.infrastruktura.AnalitikaIzvoda;
import korenski.model.infrastruktura.DnevnoStanjeRacuna;
import korenski.model.infrastruktura.Racun;
import korenski.model.nalog_za_prenos.NalogZaPrenos;
import korenski.repository.institutions.AnalitikaIzvodaRepository;
import korenski.repository.institutions.BankRepository;
import korenski.repository.institutions.DnevnoStanjeRepository;
import korenski.repository.institutions.RacunRepository;

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
	
	public void sameBankTransfer(NalogZaPrenos nalog, Racun racunDuznika, Racun racunPoverioca) {

		DnevnoStanjeRacuna dnevnoStanjeDuznika = dnevnoStanjeRepository.findByDatumAndRacun(new Date(), racunDuznika);
		DnevnoStanjeRacuna dnevnoStanjePoverioca = dnevnoStanjeRepository.findByDatumAndRacun(new Date(), racunPoverioca);
		
		if(dnevnoStanjeDuznika == null){
			dnevnoStanjeDuznika = new DnevnoStanjeRacuna(new Date(),racunDuznika.getStanje(),0,0,racunDuznika.getStanje(),racunDuznika);
			try{
				dnevnoStanjeRepository.save(dnevnoStanjeDuznika);
			}catch (Exception e) {
				// TODO: handle exception
				return;
			}
		}
		
		if(dnevnoStanjePoverioca == null){
			dnevnoStanjePoverioca=new DnevnoStanjeRacuna(new Date(),racunPoverioca.getStanje(),0,0,racunPoverioca.getStanje(),racunPoverioca);
			try{
				dnevnoStanjeRepository.save(dnevnoStanjePoverioca);
			}catch (Exception e) {
				// TODO: handle exception
				return;
			}
		}
		
		boolean hitno;
		
		if(nalog.getHitno().equals("Da")){
			hitno = true;
		}else{
			hitno = false;
		}
		
		AnalitikaIzvoda analitikaDuznika=new AnalitikaIzvoda(new Date(new Date().getTime()), "T", nalog.getPodaciODuzniku().getAdresa(),
				nalog.getSvrhaPlacanja(), nalog.getPodaciOPoveriocu().getAdresa(), nalog.getPodaciOPlacanju().getDatumValute(),
				nalog.getPodaciOPlacanju().getDatumValute(), racunDuznika.getBrojRacuna(),
				nalog.getPodaciOPlacanju().getFinansijskiPodaciDuznik().getModel(), 
				nalog.getPodaciOPlacanju().getFinansijskiPodaciDuznik().getPozivNaBroj(),
				racunPoverioca.getBrojRacuna(),
				nalog.getPodaciOPlacanju().getFinansijskiPodaciPoverilac().getModel(),
				nalog.getPodaciOPlacanju().getFinansijskiPodaciPoverilac().getPozivNaBroj(),
				nalog.getPodaciOPlacanju().getIznos(), nalog.getPodaciOPlacanju().getValuta(), hitno ,dnevnoStanjeDuznika);
		
		
		AnalitikaIzvoda analitikaPoverioca=new AnalitikaIzvoda(new Date(new Date().getTime()), "K", nalog.getPodaciODuzniku().getAdresa(),
				nalog.getSvrhaPlacanja(), nalog.getPodaciOPoveriocu().getAdresa(), 
				nalog.getPodaciOPlacanju().getDatumValute(),
				nalog.getPodaciOPlacanju().getDatumValute(), 
				racunDuznika.getBrojRacuna(), 
				nalog.getPodaciOPlacanju().getFinansijskiPodaciDuznik().getModel(), 
				nalog.getPodaciOPlacanju().getFinansijskiPodaciDuznik().getPozivNaBroj(), 
				racunPoverioca.getBrojRacuna(), nalog.getPodaciOPlacanju().getFinansijskiPodaciPoverilac().getModel(), 
				nalog.getPodaciOPlacanju().getFinansijskiPodaciPoverilac().getPozivNaBroj(), nalog.getPodaciOPlacanju().getIznos(),
				nalog.getPodaciOPlacanju().getValuta(), hitno,dnevnoStanjePoverioca);
		
//		if((dnevnoStanjeDuznika.getNovoStanje()-analitikaDuznika.getIznos()) < 0){
//			System.out.println("Nema dovoljno sredstava!");
//			return;
//		}
		
		try{
			analitikaIzvodaRepository.save(analitikaDuznika);
			dnevnoStanjeDuznika.setNovoStanje(dnevnoStanjeDuznika.getNovoStanje()-analitikaDuznika.getIznos());
			dnevnoStanjeDuznika.setPrometNaTeret(dnevnoStanjeDuznika.getPrometNaTeret()+analitikaDuznika.getIznos());
			dnevnoStanjeRepository.save(dnevnoStanjeDuznika);
			
			analitikaIzvodaRepository.save(analitikaPoverioca);
			dnevnoStanjePoverioca.setNovoStanje(dnevnoStanjePoverioca.getNovoStanje()+analitikaPoverioca.getIznos());
			dnevnoStanjePoverioca.setPrometUKorist(dnevnoStanjePoverioca.getPrometUKorist()+analitikaPoverioca.getIznos());
			dnevnoStanjeRepository.save(dnevnoStanjePoverioca);
		}catch (Exception e) {
			// TODO: handle exception
			return;
		}

	}

	public void differentBanksTransfer(NalogZaPrenos nalog, Racun racunDuznika, Racun racunPoverioca) {

	}

}
