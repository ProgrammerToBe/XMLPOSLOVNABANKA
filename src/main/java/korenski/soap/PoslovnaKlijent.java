package korenski.soap;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import korenski.repository.soap.ClearingOdgovorRepository;
import korenski.repository.soap.ClearingRepository;
import korenski.repository.soap.RtgsOdgovorRepository;
import korenski.repository.soap.RtgsRepository;
import korenski.soap.clearing.ClearingRequest;
import korenski.soap.clearing.ClearingResponse;
import korenski.soap.clearing.Poruka;
import korenski.soap.odobrenje.OdobrenjeRequest;
import korenski.soap.odobrenje.OdobrenjeResponse;
import korenski.soap.rtgs.RtgsRequest;
import korenski.soap.rtgs.RtgsResponse;
import korenski.soap.zaduzenje.ZaduzenjeRequest;
import korenski.soap.zaduzenje.ZaduzenjeResponse;

@Component
public class PoslovnaKlijent {
	@Autowired
	private WebServiceTemplate webServiceTemplate;
	 
	public korenski.soap.rtgs.Poruka posaljiRtgs(RtgsRequest request) throws JAXBException {

		System.out.println("Pravim rtgs!");
		  
//		RtgsRequest request = new RtgsRequest();
//		  
//		JAXBContext context = JAXBContext.newInstance("korenski.soap.rtgs");
//		Unmarshaller unmarshaller = context.createUnmarshaller();
//		RtgsRequest rtgsreq = (RtgsRequest) unmarshaller.unmarshal(new File("./files/xmls/RTGS.xml"));
//		  
//
//		request.setZahtev(rtgsreq.getZahtev());
//		  
		System.out.println("Saljem rtgs!");
		
		webServiceTemplate.setDefaultUri("http://localhost:8090/ws_poslovne/rtgs");
		webServiceTemplate.setMarshaller(jaxb2Marshaller("korenski.soap.rtgs"));
		webServiceTemplate.setUnmarshaller(jaxb2Marshaller("korenski.soap.rtgs"));
		   
		
		
		RtgsResponse odgovor = (RtgsResponse) webServiceTemplate.marshalSendAndReceive(request);
		
	    System.out.println("Stigao odgovor!");
	   
	    return odgovor.getOdgovor();
	}
	  
	public Poruka posaljiClearing(ClearingRequest request) throws JAXBException {

		System.out.println("Pravim clearing!");
		  
//		ClearingRequest request = new ClearingRequest();
//	    
//		JAXBContext context = JAXBContext.newInstance("korenski.soap.clearing");
//		Unmarshaller unmarshaller = context.createUnmarshaller();
//		ClearingRequest clearingreq = (ClearingRequest) unmarshaller.unmarshal(new File("./files/xmls/Clearing.xml"));
//		  
//		
//		request.setZahtev(clearingreq.getZahtev());
//			  
		System.out.println("Saljem clearing!");
			
		webServiceTemplate.setDefaultUri("http://localhost:8090/ws_poslovne/clearing");
		webServiceTemplate.setMarshaller(jaxb2Marshaller("korenski.soap.clearing"));
		webServiceTemplate.setUnmarshaller(jaxb2Marshaller("korenski.soap.clearing"));
			
		ClearingResponse odgovor = (ClearingResponse) webServiceTemplate.marshalSendAndReceive(request);
		
		System.out.println("Stigao odgovor!");
		   
		return odgovor.getOdgovor();
	  }
	 
	  private Jaxb2Marshaller jaxb2Marshaller(String path) {

		  Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		  jaxb2Marshaller.setContextPath(path);
		  return jaxb2Marshaller;
	  }
}
