package korenski.soap;

import javax.jws.HandlerChain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import korenski.service.xml_servisi.ImportPresekServis;
import korenski.soap.izvestaji_model.IzvestajRequest;
import korenski.soap.izvestaji_model.IzvestajResponse;
import korenski.soap.nalozi_model.NalogRequest;
import korenski.soap.nalozi_model.NalogResponse;

@Endpoint
@HandlerChain(file = "handlers.xml")

public class PoslovnaEndpointNalog {
	private static final String NAMESPACE_URI_NALOG = "http://korenski/soap/nalozi_model";
	private static final String NAMESPACE_URI_IZVESTAJ = "http://korenski/soap/izvestaji_model";

	
	@Autowired
	ImportPresekServis importService;	
	
	@PayloadRoot(namespace = NAMESPACE_URI_NALOG, localPart = "nalogRequest")
	@ResponsePayload
	public NalogResponse getNalog(@RequestPayload NalogRequest request) {
		NalogResponse response = new NalogResponse();
		
		System.out.println("Pogodjena metoda sendNalogRequest");
		System.out.println("Primljen nalog sa svrhom placanja "+ request.getNalog().getSvrhaPlacanja());
		response.setCode("OK");
		response.setText("Primljen nalog sa svrhom placanja "+ request.getNalog().getSvrhaPlacanja());

		return response;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI_IZVESTAJ, localPart = "izvestajRequest")
	@ResponsePayload
	public IzvestajResponse getIzvestaj(@RequestPayload IzvestajRequest request) {
		IzvestajResponse response = new IzvestajResponse();
		
		System.out.println("Pogodjena metoda izvestajRequest");
		System.out.println("Primljen zahtev za izvestaj "+ request.getZahtev());
		response.setPresek(importService.importPresek());

		return response;
	}
	
	
	
}