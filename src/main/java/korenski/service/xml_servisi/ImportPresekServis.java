package korenski.service.xml_servisi;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Service;

import korenski.model.nalog_za_prenos.Nalozi;
import korenski.soap.izvestaji_model.Presek;

@Service
public class ImportPresekServis {

	public Presek importPresek(){
		JAXBContext context;
		try {
			context = JAXBContext.newInstance("korenski.soap.izvestaji_model");

			Unmarshaller unmarshaller = context.createUnmarshaller();

			// Unmarshalling generiše objektni model na osnovu XML fajla
			Presek presek = (Presek) unmarshaller.unmarshal(new File("./files/primeri/Presek.xml"));
		
			return presek;
			
		} catch (JAXBException e) {
			
			System.out.println("PUKAO IMPORT PRESEKA");
			Presek p = new Presek();
			p.setBrojRacuna("123-7894666-97");
			return p;
			
			
		}

			
	}
	
}
