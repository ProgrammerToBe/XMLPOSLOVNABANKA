package korenski.controller.institutions.pomocni;

import java.util.Date;

public class ZatvaranjeFilter {
	
	private String racunZatvaranja;
	private String racunPrenosa;
	private Date pocetak;
	private Date kraj;
	
	public ZatvaranjeFilter() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ZatvaranjeFilter(String racunZatvaranja, String racunPrenosa, Date pocetak, Date kraj) {
		super();
		this.racunZatvaranja = racunZatvaranja;
		this.racunPrenosa = racunPrenosa;
		this.pocetak = pocetak;
		this.kraj = kraj;
	}
	public String getRacunZatvaranja() {
		return racunZatvaranja;
	}
	public void setRacunZatvaranja(String racunZatvaranja) {
		this.racunZatvaranja = racunZatvaranja;
	}
	public String getRacunPrenosa() {
		return racunPrenosa;
	}
	public void setRacunPrenosa(String racunPrenosa) {
		this.racunPrenosa = racunPrenosa;
	}
	public Date getPocetak() {
		return pocetak;
	}
	public void setPocetak(Date pocetak) {
		this.pocetak = pocetak;
	}
	public Date getKraj() {
		return kraj;
	}
	public void setKraj(Date kraj) {
		this.kraj = kraj;
	}
	
	
	
}
