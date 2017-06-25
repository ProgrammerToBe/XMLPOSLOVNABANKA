package korenski.service.infrastruktura;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import korenski.model.infrastruktura.AnalitikaIzvoda;
import korenski.model.infrastruktura.Bank;
import korenski.model.infrastruktura.MedjubankarskiPrenos;
import korenski.model.infrastruktura.StavkaPrenosa;
import korenski.soap.clearing.Clearing;
import korenski.soap.clearing.Clearing.StavkePrenosa;
import korenski.soap.clearing.Clearing.StavkePrenosa.StavkaPrenosa.PodaciOPlacanju;
import korenski.soap.clearing.ClearingRequest;
import korenski.soap.clearing.TBanka;
import korenski.soap.clearing.TFinansijskiPodaci;
import korenski.soap.rtgs.RTGS;
import korenski.soap.rtgs.RtgsRequest;

public class PrenosHelper {

	public PrenosHelper() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ClearingRequest repackToClearing(MedjubankarskiPrenos mbprenos){
		
		ClearingRequest cr = new ClearingRequest();
		
		Clearing clearing = new Clearing();
		
		Bank bankaDuznika = mbprenos.getBankaPrva();
		Bank bankaPoverioca = mbprenos.getBankaDruga();
		
		
		TBanka tBankaDuznika = new TBanka();
		
		tBankaDuznika.setNaziv(bankaDuznika.getName());
		tBankaDuznika.setObracunskiRacun(bankaDuznika.getLiquidationAcount());
		tBankaDuznika.setSwiftKod(bankaDuznika.getSwiftCode());
		
		TBanka tBankaPoverioca = new TBanka();
		
		tBankaPoverioca.setNaziv(bankaPoverioca.getName());
		tBankaPoverioca.setObracunskiRacun(bankaPoverioca.getLiquidationAcount());
		tBankaPoverioca.setSwiftKod(bankaPoverioca.getSwiftCode());
		
		
		clearing.setBankaDuznik(tBankaDuznika);
		clearing.setBankaPoverilac(tBankaPoverioca);
//		clearing.setDatum(mbprenos.getDatum());
//		clearing.setDatumValute(value);
		
		clearing.setIznos(new BigDecimal(mbprenos.getIznos()));
		clearing.setSifraPrometa(mbprenos.getPoruka().getCode());
		
		Set<StavkaPrenosa> stavkeIzMB = mbprenos.getStavkePrenosa();
		
		StavkePrenosa stavke = new StavkePrenosa();
		
		AnalitikaIzvoda ai;
		korenski.soap.clearing.Clearing.StavkePrenosa.StavkaPrenosa stavkaClearing;
		PodaciOPlacanju pop;
		
		TFinansijskiPodaci finDuznik;
		TFinansijskiPodaci finPoverilac;
		
		for (StavkaPrenosa sp : stavkeIzMB){
			ai = sp.getAnalitikaIzvoda();
			
			stavkaClearing = new korenski.soap.clearing.Clearing.StavkePrenosa.StavkaPrenosa();
			
			stavkaClearing.setDuznikNalogodavac(ai.getDuznik());
			stavkaClearing.setPrimalacPoverilac(ai.getPrimalac());
			
			pop = new PodaciOPlacanju();
			
			//pop.setDatumNaloga(value);
			
			finDuznik = new TFinansijskiPodaci();
			
			finDuznik.setBrojRacuna(ai.getRacunPrvi());
			finDuznik.setModel(ai.getModelPrvi());
			finDuznik.setPozivNaBroj(ai.getPozivNaBrojPrvi());
			
			finPoverilac = new TFinansijskiPodaci();
			
			finPoverilac.setBrojRacuna(ai.getRacunDrugi());
			finPoverilac.setModel(ai.getModelDrugi());
			finPoverilac.setPozivNaBroj(ai.getPozivNaBrojDrugi());
			
			pop.setFinansijskiPodaciDuznik(finDuznik);
			pop.setFinansijskiPodaciPoverilac(finPoverilac);
			
			pop.setIznos(new BigDecimal(ai.getIznos()));
			pop.setOznakaValute(ai.getSifraValute());
			
			stavkaClearing.setPodaciOPlacanju(pop);
			stavkaClearing.setSvrhaPlacanja(ai.getSvrhaPlacanja());
			
			stavke.getStavkaPrenosa().add(stavkaClearing);
		}
		
		clearing.setStavkePrenosa(stavke);
		
		
		cr.setZahtev(clearing);
		
		return cr;
	}
	

	
	public RtgsRequest repackToRtgs(MedjubankarskiPrenos mbprenos){
		
		RtgsRequest rr = new RtgsRequest();
	
		RTGS rtgs = new RTGS();
		
		rtgs.setDatumValute(new Date());
		
		Bank bankaDuznika = mbprenos.getBankaPrva();
		Bank bankaPoverioca = mbprenos.getBankaDruga();
		
		
		korenski.soap.rtgs.TBanka tBankaDuznika = new korenski.soap.rtgs.TBanka();
		
		tBankaDuznika.setNaziv(bankaDuznika.getName());
		tBankaDuznika.setObracunskiRacun(bankaDuznika.getLiquidationAcount());
		tBankaDuznika.setSwiftKod(bankaDuznika.getSwiftCode());
		
		korenski.soap.rtgs.TBanka tBankaPoverioca = new korenski.soap.rtgs.TBanka();
		
		tBankaPoverioca.setNaziv(bankaPoverioca.getName());
		tBankaPoverioca.setObracunskiRacun(bankaPoverioca.getLiquidationAcount());
		tBankaPoverioca.setSwiftKod(bankaPoverioca.getSwiftCode());
		
		
		
		rtgs.setBankaDuznik(tBankaDuznika);
		rtgs.setBankaPoverilac(tBankaPoverioca);
		rtgs.setDatumValute(mbprenos.getDatum());
		
		rtgs.setIznos(new BigDecimal(mbprenos.getIznos()));
		rtgs.setSifraPrometa(mbprenos.getPoruka().getCode());
		
		korenski.soap.rtgs.RTGS.StavkaPrenosa stavka = new korenski.soap.rtgs.RTGS.StavkaPrenosa();
		
		Set<StavkaPrenosa> stavkeIzMB = mbprenos.getStavkePrenosa();
		
		korenski.soap.rtgs.RTGS.StavkaPrenosa.PodaciOPlacanju pop = new korenski.soap.rtgs.RTGS.StavkaPrenosa.PodaciOPlacanju();
		
		korenski.soap.rtgs.TFinansijskiPodaci finDuznik;
		korenski.soap.rtgs.TFinansijskiPodaci finPoverilac;
		
		AnalitikaIzvoda ai;
		
		
		for (StavkaPrenosa sp : stavkeIzMB){
			ai = sp.getAnalitikaIzvoda();
			
			stavka.setDuznikNalogodavac(ai.getDuznik());
			stavka.setPrimalacPoverilac(ai.getPrimalac());
			
			
			pop.setDatumNaloga(sp.getAnalitikaIzvoda().getDatumNaloga());
			
			
			finDuznik = new korenski.soap.rtgs.TFinansijskiPodaci();
			
			finDuznik.setBrojRacuna(ai.getRacunPrvi());
			finDuznik.setModel(ai.getModelPrvi());
			finDuznik.setPozivNaBroj(ai.getPozivNaBrojPrvi());
			
			finPoverilac = new korenski.soap.rtgs.TFinansijskiPodaci();
			
			finPoverilac.setBrojRacuna(ai.getRacunDrugi());
			finPoverilac.setModel(ai.getModelDrugi());
			finPoverilac.setPozivNaBroj(ai.getPozivNaBrojDrugi());
			
			pop.setFinansijskiPodaciDuznik(finDuznik);
			pop.setFinansijskiPodaciPoverilac(finPoverilac);
			
			
			pop.setIznos(new BigDecimal(ai.getIznos()));
			pop.setOznakaValute(ai.getSifraValute());
			
			stavka.setPodaciOPlacanju(pop);
			stavka.setSvrhaPlacanja(ai.getSvrhaPlacanja());
			
			
			rtgs.setStavkaPrenosa(stavka);
		}
		
		
		
		
		rr.setZahtev(rtgs);
		
		return rr;
		
	}

}
