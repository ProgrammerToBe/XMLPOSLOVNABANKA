package korenski.DTOs;

import java.util.Date;

import korenski.model.infrastruktura.Bank;
import korenski.model.sifrarnici.Message;

public class MedjubankarskiPrenosDTO {
	private Date datum1;
	private Date datum2;
	private Bank banka;
	private double iznos1;
	private double iznos2;
	private Message poruka;
	public MedjubankarskiPrenosDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Date getDatum1() {
		return datum1;
	}
	public void setDatum1(Date datum1) {
		this.datum1 = datum1;
	}
	public Date getDatum2() {
		return datum2;
	}
	public void setDatum2(Date datum2) {
		this.datum2 = datum2;
	}
	public Bank getBanka() {
		return banka;
	}
	public void setBanka(Bank banka) {
		this.banka = banka;
	}
	public double getIznos1() {
		return iznos1;
	}
	public void setIznos1(double iznos1) {
		this.iznos1 = iznos1;
	}
	public double getIznos2() {
		return iznos2;
	}
	public void setIznos2(double iznos2) {
		this.iznos2 = iznos2;
	}
	public Message getPoruka() {
		return poruka;
	}
	public void setPoruka(Message poruka) {
		this.poruka = poruka;
	}
	
	

}
