package korenski.soap;

import javax.jws.HandlerChain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


import korenski.service.xml_servisi.ImportPresekServis;

import korenski.soap.clearing.Clearing;
import korenski.soap.clearing.ClearingRequest;
import korenski.soap.clearing.ClearingResponse;

import korenski.soap.izvestaji_model.IzvestajRequest;
import korenski.soap.izvestaji_model.IzvestajResponse;
import korenski.soap.nalozi_model.NalogRequest;
import korenski.soap.nalozi_model.NalogResponse;
import korenski.soap.odobrenje.OdobrenjeRequest;
import korenski.soap.odobrenje.OdobrenjeResponse;
import korenski.soap.rtgs.RtgsRequest;
import korenski.soap.rtgs.RtgsResponse;
import korenski.soap.zaduzenje.ZaduzenjeRequest;
import korenski.soap.zaduzenje.ZaduzenjeResponse;

@Endpoint
@HandlerChain(file = "handlers.xml")

public class PoslovnaEndpointNalog {
	private static final String NAMESPACE_URI_NALOG = "http://korenski/soap/nalozi_model";
	private static final String NAMESPACE_URI_IZVESTAJ = "http://korenski/soap/izvestaji_model";


	
	@Autowired
	ImportPresekServis importService;	

	//obavezno brisi
	private static final String NAMESPACE_URI_ODOBRENJE = "http://korenski/soap/odobrenje";
	private static final String NAMESPACE_URI_ZADUZENJE = "http://korenski/soap/zaduzenje";

	
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

	@PayloadRoot(namespace = NAMESPACE_URI_ODOBRENJE, localPart = "odobrenjeRequest")
	@ResponsePayload
	public OdobrenjeResponse getOdobrenje(@RequestPayload OdobrenjeRequest request) {
		OdobrenjeResponse response = new OdobrenjeResponse();
		
		System.out.println("Pogodjena metoda odobrenjeRequest");
		System.out.println("Primljen zahtev za odobrenje "+ request.getZahtev().getIznos());
		response.setOdgovor("Primljen zahtev za odobrenje "+ request.getZahtev().getIznos());

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