package korenski.controller.institutions.pomocni;

import java.util.Date;

public class StanjaFilter {
	
	private String racun;
	private Date pocetak;
	private Date kraj;
	public StanjaFilter(String racun, Date pocetak, Date kraj) {
		super();
		this.racun = racun;
		this.pocetak = pocetak;
		this.kraj = kraj;
	}
	public StanjaFilter() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getRacun() {
		return racun;
	}
	public void setRacun(String racun) {
		this.racun = racun;
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
