package korenski.controller.businesslogic;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import korenski.intercepting.CustomAnnotation;
import korenski.model.autorizacija.User;
import korenski.model.infrastruktura.Bank;
import korenski.model.infrastruktura.Racun;
import korenski.model.nalog_za_prenos.NalogZaPrenos;
import korenski.model.nalog_za_prenos.NalogZaPrenos.PodaciOPlacanju;
import korenski.model.nalog_za_prenos.Nalozi;
import korenski.model.nalog_za_prenos.TFinansijskiPodaci;
import korenski.model.nalog_za_prenos.TLice;
import korenski.repository.institutions.BankRepository;
import korenski.repository.institutions.DnevnoStanjeRepository;
import korenski.repository.institutions.RacunRepository;
import korenski.service.infrastruktura.BusinessLogicService;

@Controller
public class ImportController {

	@Autowired 
	RacunRepository racunRepository;
	@Autowired
	BankRepository bankRepository;
	@Autowired
	DnevnoStanjeRepository dnevnoStanjeRepository;
	
	@Autowired
	BusinessLogicService blService;
	
	@CustomAnnotation(value = "IMPORT_FROM_XML")
	@RequestMapping(
			value = "/importChosenXML",
			method = RequestMethod.POST,
			consumes =  MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> importChosenXML(@RequestBody String fileName , @Context HttpServletRequest request) throws Exception {
		
		JAXBContext context = JAXBContext.newInstance("korenski.model.nalog_za_prenos");

		// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni
		// model
		Unmarshaller unmarshaller = context.createUnmarshaller();

		/*
		 * 
		 * povezi sa xsd semom za validaciju xml-a
		 */
		
		
		// Unmarshalling generiše objektni model na osnovu XML fajla
		Nalozi nalozi = (Nalozi) unmarshaller.unmarshal(new File("./files/xmls/"+fileName));
		
		//printNalozi(nalozi);

		User user = (User)request.getSession().getAttribute("user");
		
		Bank bank = user.getBank();
		
		List<NalogZaPrenos> nal = nalozi.getNalogZaPrenos();

		for (NalogZaPrenos nalog : nal) {
			
			TFinansijskiPodaci podaciDuznika = nalog.getPodaciOPlacanju().getFinansijskiPodaciDuznik();
			TFinansijskiPodaci podaciPoverioca = nalog.getPodaciOPlacanju().getFinansijskiPodaciPoverilac();
			
			String racunDuznika = podaciDuznika.getBrojRacuna();
			String racunPoverioca = podaciPoverioca.getBrojRacuna();
			
			Racun duznik = racunRepository.findByBankAndBrojRacunaAndStatusTrue(bank, racunDuznika);
			Racun poverilac = racunRepository.findByBankAndBrojRacunaAndStatusTrue(bank, racunPoverioca);
			
			if(duznik == null && poverilac == null){
				System.out.println("Nije moja banka");
				continue;
			}
			
			if(duznik != null && poverilac != null){
				System.out.println("Racuni su iz iste banke!");
				blService.sameBankTransfer(nalog, duznik, poverilac);
				
			}else if(duznik!= null && poverilac == null){
				System.out.println("Racuni su iz razlicitih banaka");
				blService.differentBanksTransfer(nalog, duznik, poverilac);
			}else{
				continue;
			}
			
			
		}
		
		return new ResponseEntity<String>("Sve ok!", HttpStatus.OK);
	}

	public void printNalozi(Nalozi nalozi) {

		System.out.println("Nalozi : " + "\n");
		List<NalogZaPrenos> nal = nalozi.getNalogZaPrenos();

		for (NalogZaPrenos nalog : nal) {
			printNalogZaPrenos(nalog);
		}

	}

	private void printNalogZaPrenos(NalogZaPrenos nalog) {

		System.out.println("\t Nalog : " + "\n");
		String hitno = nalog.getHitno();
		String svrha = nalog.getSvrhaPlacanja();

		TLice duznik = nalog.getPodaciODuzniku();
		TLice poverilac = nalog.getPodaciOPoveriocu();
		PodaciOPlacanju placanje = nalog.getPodaciOPlacanju();

		System.out.println("\t\t Hitno : " + hitno);
		System.out.println("\t\t Svrha placanja : " + svrha);

		System.out.println("\t\t podaci o duzniku : " );
		printPodaciOLicu(duznik);
		System.out.println("\t\t podaci o poveriocu : " );
		printPodaciOLicu(poverilac);
		System.out.println("\t\t podaci o placanju : " );
		printPodaciOPlacanju(placanje);

	}

	void printPodaciOLicu(TLice lice) {
		System.out.println("\t\t\t adresa : "+ lice.getAdresa() );
	}

	void printPodaciOPlacanju(PodaciOPlacanju placanje) {
		
		Date datum = placanje.getDatumValute();
		TFinansijskiPodaci finansijskiDuznik = placanje.getFinansijskiPodaciDuznik();
		TFinansijskiPodaci finansijskiPoverilac = placanje.getFinansijskiPodaciPoverilac();
		double iznos = placanje.getIznos();
		String sifra = placanje.getSifraPlacanja();
		String valuta = placanje.getValuta();
		
		System.out.println("\t\t\t Datum "+ datum);
		System.out.println("\t\t\t Iznos "+ iznos);
		System.out.println("\t\t\t Sifra placanja "+ sifra);
		System.out.println("\t\t\t valuta "+ valuta);
		System.out.println("\t\t\t Finansijski podaci duznika ");
		printFinansijskiPodaci(finansijskiDuznik);
		System.out.println("\t\t\t Finansijski podaci poverioca ");
		printFinansijskiPodaci(finansijskiPoverilac);
	}

	private void printFinansijskiPodaci(TFinansijskiPodaci finansijski) {
		String model = finansijski.getModel();
		String poziv = finansijski.getPozivNaBroj();
		String brojRacuna = finansijski.getBrojRacuna();
		
		System.out.println("\t\t\t\t Model " + model);
		System.out.println("\t\t\t\t Poziv na broj " + poziv);
		System.out.println("\t\t\t\t Broj racuna " + brojRacuna);
		
	}
	
}
