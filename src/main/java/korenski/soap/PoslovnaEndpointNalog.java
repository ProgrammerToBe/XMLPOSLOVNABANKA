package korenski.soap;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.jws.HandlerChain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import korenski.model.infrastruktura.AnalitikaIzvoda;
import korenski.model.infrastruktura.DnevnoStanjeRacuna;
import korenski.model.infrastruktura.Racun;
import korenski.repository.institutions.DnevnoStanjeRepository;
import korenski.repository.institutions.RacunRepository;
import korenski.repository.soap.IzvodRepository;
import korenski.repository.soap.NalogRepository;
import korenski.repository.soap.OdobrenjeClearingRepository;
import korenski.repository.soap.OdobrenjeRtgsRepository;
import korenski.repository.soap.PresekRepository;
import korenski.service.infrastruktura.BusinessLogicService;
import korenski.service.institutions.AnalitikaIzvodaService;
import korenski.service.xml_servisi.ImportPresekServis;
import korenski.soap.izvestaji_model.IzvestajRequest;
import korenski.soap.izvestaji_model.IzvestajResponse;
import korenski.soap.izvestaji_model.Presek;
import korenski.soap.izvestaji_model.Presek.StavkePreseka;
import korenski.soap.izvestaji_model.Presek.StavkePreseka.StavkaPreseka;
import korenski.soap.izvestaji_model.Presek.StavkePreseka.StavkaPreseka.PodaciOPlacanju;
import korenski.soap.izvestaji_model.TFinansijskiPodaci;
import korenski.soap.nalozi_model.NalogRequest;
import korenski.soap.nalozi_model.NalogResponse;
import korenski.soap.zaduzenje.ZaduzenjeRequest;
import korenski.soap.zaduzenje.ZaduzenjeResponse;

@Endpoint
@HandlerChain(file = "handlers.xml")

public class PoslovnaEndpointNalog {
	private static final String NAMESPACE_URI_NALOG = "http://korenski/soap/nalozi_model";
	private static final String NAMESPACE_URI_IZVESTAJ = "http://korenski/soap/izvestaji_model";

	@Autowired
	private DnevnoStanjeRepository dnevnoStanjeRepository;
	
	@Autowired
	private AnalitikaIzvodaService analitikaIzvodaService;
	
	@Autowired
	private RacunRepository racunRepository;
	
	@Autowired
	BusinessLogicService businessLogicService;
	
	@Autowired
	ImportPresekServis importService;	
	
	@Autowired
	IzvodRepository izvodRepository;
	
	@Autowired
	PresekRepository presekRepository;

	@Autowired
	NalogRepository nalogRepository;
	
	@Autowired
	OdobrenjeClearingRepository odobrenjeClearingRepository;
	
	@Autowired
	OdobrenjeRtgsRepository odobrenjeRtgsRepository;
	
	//obavezno brisi
	private static final String NAMESPACE_URI_ODOBRENJE_CLEARING = "http://korenski/soap/odobrenjeclearing";
	private static final String NAMESPACE_URI_ODOBRENJE_RTGS = "http://korenski/soap/odobrenjertgs";
	private static final String NAMESPACE_URI_ZADUZENJE = "http://korenski/soap/zaduzenje";

	
	@PayloadRoot(namespace = NAMESPACE_URI_NALOG, localPart = "nalogRequest")
	@ResponsePayload
	public NalogResponse getNalog(@RequestPayload NalogRequest request) {
		
		NalogResponse response = new NalogResponse();
		
		businessLogicService.processing(request);
		
		System.out.println("Pogodjena metoda sendNalogRequest");
		System.out.println("Primljen nalog sa svrhom placanja "+ request.getNalog().getSvrhaPlacanja());
		response.setCode("OK");
		response.setText("Primljen nalog sa svrhom placanja "+ request.getNalog().getSvrhaPlacanja());
		
		nalogRepository.save(request.getNalog());
		
		return response;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI_IZVESTAJ, localPart = "izvestajRequest")
	@ResponsePayload
	public IzvestajResponse getIzvestaj(@RequestPayload IzvestajRequest request) {
		IzvestajResponse response = new IzvestajResponse();
		
		System.out.println("Pogodjena metoda izvestajRequest");
		System.out.println("Primljen zahtev za izvestaj "+ request.getZahtev());
		
		Presek presek = repackToPresek(request);
		
		izvodRepository.save(request.getZahtev());
		
		//presekRepository.save(presek);
		
		response.setPresek(presek);

		return response;
	}

	public Presek repackToPresek(IzvestajRequest request){
		Presek presek = new Presek();
		
		Racun racun = racunRepository.findByBrojRacuna(request.getZahtev().getBrojRacuna());
		
		//System.out.println("Teodora ... : qweqweqw : " + request.getZahtev().getDatum().toGregorianCalendar().getTime());
		
		DnevnoStanjeRacuna dnevnoStanje = dnevnoStanjeRepository.findByDatumAndRacun(request.getZahtev().getDatum(), racun);
		
		Page<AnalitikaIzvoda> analitike = analitikaIzvodaService.findByPresek(request.getZahtev().getRedniBrojPreseka().intValue(), dnevnoStanje);
		
		
		StavkePreseka sp = new StavkePreseka();
		StavkaPreseka stavka = new StavkaPreseka();
		int brojPromenaUKorist = 0;
		int brojPromenaNaTeret = 0;
		double ukupnoUKorist = 0;
		double ukupnoNaTeret = 0;
		
		
		double pocetnoStanje = analitike.getContent().get(0).getPrethodnoStanje();
		double krajnjeStanje = pocetnoStanje;
		
		
		for(AnalitikaIzvoda analitika : analitike){
			stavka.setDuznikNalogodavac(analitika.getDuznik());
			stavka.setPrimalacPoverilac(analitika.getPrimalac());
			
			stavka.setSvrhaPlacanja(analitika.getSvrhaPlacanja());
			
			
			
			PodaciOPlacanju pop = new PodaciOPlacanju();
			
			pop.setDatumNaloga(analitika.getDatumNaloga());
			pop.setDatumValute(analitika.getDatumValute());
			TFinansijskiPodaci podaciDuznik = new TFinansijskiPodaci();
			podaciDuznik.setBrojRacuna(analitika.getRacunPrvi());
			podaciDuznik.setModel(analitika.getModelPrvi());
			podaciDuznik.setPozivNaBroj(analitika.getPozivNaBrojPrvi());
			
			TFinansijskiPodaci podaciPrimalac = new TFinansijskiPodaci();
			podaciPrimalac.setBrojRacuna(analitika.getRacunDrugi());
			podaciPrimalac.setModel(analitika.getModelDrugi());
			podaciPrimalac.setPozivNaBroj(analitika.getPozivNaBrojDrugi());
			
			pop.setFinansijskiPodaciDuznik(podaciDuznik);
			pop.setFinansijskiPodaciPoverilac(podaciPrimalac);
			pop.setIznos(new BigDecimal(analitika.getIznos()));
			pop.setOznakaValute(analitika.getSifraValute());
			pop.setDatumNaloga(analitika.getDatumNaloga());
			pop.setDatumValute(analitika.getDatumValute());
			
			stavka.setPodaciOPlacanju(pop);
			
			sp.getStavkaPreseka().add(stavka);
			
			if(analitika.getSmer().equals("T")){
				brojPromenaNaTeret++;
				ukupnoNaTeret += analitika.getIznos();
				krajnjeStanje -= analitika.getIznos();
			}else{
				brojPromenaUKorist++;
				ukupnoUKorist += analitika.getIznos();
				krajnjeStanje += analitika.getIznos();
			}
			
		}
		
		
		presek.setBrojPreseka(request.getZahtev().getRedniBrojPreseka());
		presek.setBrojPromenaNaTeret(new BigInteger(new Long(brojPromenaNaTeret).toString()));
		presek.setUkupnoNaTeret(new BigDecimal(ukupnoNaTeret));
		presek.setBrojPromenaUKorist(new BigInteger(new Long(brojPromenaUKorist).toString()));
		presek.setUkupnoUKorist(new BigDecimal(ukupnoUKorist));
		presek.setBrojRacuna(request.getZahtev().getBrojRacuna());
		presek.setDatum(request.getZahtev().getDatum());
		presek.setNovoStanje(new BigDecimal(krajnjeStanje));
		presek.setPrethodnoStanje(new BigDecimal(pocetnoStanje));
		presek.setStavkePreseka(sp);
		
		return presek;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI_ODOBRENJE_CLEARING, localPart = "odobrenjeRequest")
	@ResponsePayload
	public korenski.soap.odobrenjeclearing.OdobrenjeResponse getOdobrenjeClearing(@RequestPayload korenski.soap.odobrenjeclearing.OdobrenjeRequest request) {
		korenski.soap.odobrenjeclearing.OdobrenjeResponse response = new korenski.soap.odobrenjeclearing.OdobrenjeResponse();
		
		System.out.println("Pogodjena metoda odobrenjeRequest");
		System.out.println("Primljen zahtev za odobrenje Clearing "+ request.getZahtev().getClearingElement().getIznos());
		response.setOdgovor("Primljen zahtev za odobrenje Clearing "+ request.getZahtev().getClearingElement().getIznos());
		
		odobrenjeClearingRepository.save(request.getZahtev());
		
		
		List<korenski.soap.odobrenjeclearing.Clearing.StavkePrenosa.StavkaPrenosa> stavke = request.getZahtev().getClearingElement().getStavkePrenosa().getStavkaPrenosa();
		
		Racun racunPoverioca = null;
		for(korenski.soap.odobrenjeclearing.Clearing.StavkePrenosa.StavkaPrenosa s : stavke){
			racunPoverioca = racunRepository.findByBrojRacuna(s.getPodaciOPlacanju().getFinansijskiPodaciPoverilac().getBrojRacuna());
			businessLogicService.uplataClearing(s, racunPoverioca);
		}
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI_ODOBRENJE_RTGS, localPart = "odobrenjeRequest")
	@ResponsePayload
	public korenski.soap.odobrenjertgs.OdobrenjeResponse getOdobrenjeRtgs(@RequestPayload korenski.soap.odobrenjertgs.OdobrenjeRequest request) {
		korenski.soap.odobrenjertgs.OdobrenjeResponse response = new korenski.soap.odobrenjertgs.OdobrenjeResponse();
		
		System.out.println("Pogodjena metoda odobrenjeRequest");
		System.out.println("Primljen zahtev za odobrenje RTGS " + request.getZahtev().getRtgsElement().getIznos());
		response.setOdgovor("Primljen zahtev za odobrenje RTGS " + request.getZahtev().getRtgsElement().getIznos());
		
		odobrenjeRtgsRepository.save(request.getZahtev());
		
		
		korenski.soap.odobrenjertgs.RTGS.StavkaPrenosa stavka = request.getZahtev().getRtgsElement().getStavkaPrenosa();
		
		Racun racunPoverioca = racunRepository.findByBrojRacuna(stavka.getPodaciOPlacanju().getFinansijskiPodaciPoverilac().getBrojRacuna());
		
		businessLogicService.uplataRTGS(stavka, racunPoverioca);
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI_ZADUZENJE, localPart = "zaduzenjeRequest")
	@ResponsePayload
	public ZaduzenjeResponse getZaduzenje(@RequestPayload ZaduzenjeRequest request) {
		ZaduzenjeResponse response = new ZaduzenjeResponse();
		
		System.out.println("Pogodjena metoda zaduzenjeRequest");
		System.out.println("Primljen zahtev za zaduzenje "+ request.getZahtev().getBanka().getSwiftKod());
		response.setOdgovor("Primljen zahtev za zaduzenje "+ request.getZahtev().getBanka().getSwiftKod());

		return response;
	}

}