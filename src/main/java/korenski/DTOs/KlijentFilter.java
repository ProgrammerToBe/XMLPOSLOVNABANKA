package korenski.DTOs;

public class KlijentFilter {
	private String jmbg;
	private String ime;
	private String prezime;
	private String adresa;
	private String telefon;
	private String email;
	private Long mesto;
	
	public KlijentFilter(String jmbg, String ime, String prezime, String adresa, String telefon, String email,
			Long mesto) {
		super();
		this.jmbg = jmbg;
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
		this.telefon = telefon;
		this.email = email;
		this.mesto = mesto;
	}

	public KlijentFilter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getMesto() {
		return mesto;
	}

	public void setMesto(Long mesto) {
		this.mesto = mesto;
	}
}
