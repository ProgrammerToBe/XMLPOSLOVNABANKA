package korenski.controller.businesslogic;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
//import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import korenski.intercepting.CustomAnnotation;

//import ch.qos.logback.classic.Level;
import korenski.controller.institutions.RacunController;
import korenski.controller.institutions.pomocni.ZatvaranjePomocni;
import korenski.controller.sifrarnici.MessageController;
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

	private FileHandler fh;
	/**
	 * Metoda koja ucitava naloge i na osnovu njih kreira analitike ili medjubankarske prenose
	 * u zavisnosti da li su ziroracuni i duznika i poverioca u istoj banci kao sto je i prijavljeni
	 * korisnik. Ukoliko ni jedan nije iz banke ulogovanog korisnika ne radi se nista.
	 * @param fileName naziv fajla koji sadrzi naloge.
	 * @param request Context
	 * @return
	 * @throws Exception
	 * @author  Teodora i Biljana
	 */
	@CustomAnnotation(value = "IMPORT_FROM_XML")
	@RequestMapping(value = "/importChosenXML", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> importChosenXML( @Context HttpServletRequest request)
			throws Exception {
//		if(!validateXML()){
//			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
		String fileName="Nalozi_primer.xml";
		Logger logger = LoggerFactory.getLogger(ImportController.class);


		JAXBContext context = JAXBContext.newInstance("korenski.model.nalog_za_prenos");

		// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni
		// model
		Unmarshaller unmarshaller = context.createUnmarshaller();

		/*
		 * 
		 * povezi sa xsd semom za validaciju xml-a
		 */

		// Unmarshalling generiše objektni model na osnovu XML fajla
		Nalozi nalozi = (Nalozi) unmarshaller.unmarshal(new File("./files/xmls/" + fileName));

		// printNalozi(nalozi);

		User user = (User) request.getSession().getAttribute("user");


		java.lang.reflect.Method m = ImportController.class.getMethod("importChosenXML", HttpServletRequest.class);
	
		
	
	
		Bank bank = user.getBank();

		List<NalogZaPrenos> nal = nalozi.getNalogZaPrenos();

		for (NalogZaPrenos nalog : nal) {
			
			TFinansijskiPodaci podaciDuznika = nalog.getPodaciOPlacanju().getFinansijskiPodaciDuznik();
			TFinansijskiPodaci podaciPoverioca = nalog.getPodaciOPlacanju().getFinansijskiPodaciPoverilac();

			if(podaciDuznika!=null && podaciPoverioca!=null){
			
			String racunDuznika = podaciDuznika.getBrojRacuna();
			String racunPoverioca = podaciPoverioca.getBrojRacuna();

			Racun duznik = racunRepository.findByBankAndBrojRacuna(bank, racunDuznika);
			Racun poverilac = racunRepository.findByBankAndBrojRacuna(bank, racunPoverioca);

			if (duznik == null && poverilac == null) {
				System.out.println("Nije moja banka");
				continue;
			}
			
			if (duznik != null && poverilac != null && duznik.getStanje() >= nalog.getPodaciOPlacanju().getIznos() && duznik.getStatus() && poverilac.getStatus()) {
				System.out.println("Racuni su iz iste banke!");
				try{
					//blService.sameBankTransfer(nalog, duznik, poverilac);
				}catch(Exception e ){
					e.printStackTrace();
					return new ResponseEntity<String>("Wrong",HttpStatus.INTERNAL_SERVER_ERROR);
				}
				//logger.debug("In bar my name is {}.", name);
				//logger.info("USER {} " + user.getId().toString() +" PERMISIJA " + " INERNI " + duznik.getBrojRacuna()+" "+ poverilac.getBrojRacuna()+" "+nalog.getPodaciOPlacanju().getIznos());
				logger.info("User {} Permisija interna tranasakcija duznik: {} poverilac: {} iznos: {} ", user.getId().toString(), duznik.getBrojRacuna(), poverilac.getBrojRacuna(), nalog.getPodaciOPlacanju().getIznos());;
			} else if (duznik != null && poverilac == null
					&& duznik.getStanje() >= nalog.getPodaciOPlacanju().getIznos()  && duznik.getStatus()) {
				System.out.println("Racuni su iz razlicitih banaka");
				try{
				//blService.differentBanksTransfer(nalog, duznik, racunPoverioca);
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					return new ResponseEntity<String>("Wrong",HttpStatus.INTERNAL_SERVER_ERROR);
				}
				//logger.info("USER " + user.getId().toString() +" PERMISIJA " + " Medjubankarski "+duznik.getBrojRacuna()+" "+ racunPoverioca+" "+ nalog.getPodaciOPlacanju().getIznos());

				
				logger.info("User {} Permisija medjubankarska transakcija duznik: {} poverilac: {} iznos: {}  hitno: {}",user.getId().toString(), duznik.getBrojRacuna(), racunPoverioca,nalog.getPodaciOPlacanju().getIznos(),nalog.getHitno() );
			} else if(duznik == null && poverilac != null  && poverilac.getStatus() ){// uplata iz druge banke
				System.out.println("Uplata iz druge banke!");
				try{
				//blService.otherBankPayment(nalog,  poverilac);
				}catch (Exception e) {
					// TODO: handle exception
					return new ResponseEntity<String>("Wrong",HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			}else if(podaciDuznika!=null && podaciPoverioca==null ){
				//islata

				String racunDuznika = podaciDuznika.getBrojRacuna();
			//	String racunPoverioca = podaciPoverioca.getBrojRacuna();

				Racun duznik = racunRepository.findByBankAndBrojRacuna(bank, racunDuznika);
			//	Racun poverilac = racunRepository.findByBankAndBrojRacunaAndStatusTrue(bank, racunPoverioca);
				if(duznik!=null && duznik.getStatus()){
					//blService.isplata(nalog, duznik);
				}
				
			}else if(podaciPoverioca!=null && podaciDuznika==null){
				//uplata
			
				String racunPoverioca = podaciPoverioca.getBrojRacuna();

			
				Racun poverilac = racunRepository.findByBankAndBrojRacuna(bank, racunPoverioca);
				if(poverilac!=null && poverilac.getStatus()){
					//blService.uplata(nalog,poverilac);
				}
			}
		}
	//	logger.debug("Leaving importChosenXML(): "+"Sve ok!");
		return new ResponseEntity<String>("Sve ok!", HttpStatus.OK);
	}

	
	//@RequestMapping(value = "/validateXML", method = RequestMethod.GET, produces=MediaType.TEXT_PLAIN)
	//@Context HttpServletRequest request
	public boolean validateXML() {
		
		String retVal;
		
		try
	    {
	        SchemaFactory factory = 
	            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	        Schema schema = factory.newSchema(new StreamSource(new File("./files/xml_tests/Faktura.xsd")));
	        Validator validator = schema.newValidator();
	        validator.validate(new StreamSource(new File("./files/xml_tests/Faktura.xml")));
	        retVal = "ok";
	    }
	    catch(Exception ex)
	    {
	        retVal = "notok";
	        return false;
	    }
		
		return true;
	}

}
