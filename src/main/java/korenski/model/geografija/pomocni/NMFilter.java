package korenski.model.geografija.pomocni;

import korenski.model.geografija.Drzava;

public class NMFilter {

	private String oznaka;
	private String naziv;
	private String postanskiBroj;
	private Long drzava;
	
	public NMFilter(String oznaka, String naziv, String postanskiBroj, Long drzava) {
		super();
		this.oznaka = oznaka;
		this.naziv = naziv;
		this.postanskiBroj = postanskiBroj;
		this.drzava = drzava;
	}
	public NMFilter() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getOznaka() {
		return oznaka;
	}
	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getPostanskiBroj() {
		return postanskiBroj;
	}
	public void setPostanskiBroj(String postanskiBroj) {
		this.postanskiBroj = postanskiBroj;
	}
	public Long getDrzava() {
		return drzava;
	}
	public void setDrzava(Long drzava) {
		this.drzava = drzava;
	}
	
	
	
	
}
