package korenski.DTOs;

public class PravnoLiceFilter {
	private String jmbg;
	private String ime;
	private String prezime;
	private String adresa;
	private String telefon;
	private String email;
	private String pib;
	private String fax;
	private String odobrio;
	private Long mesto;
	private Long delatnost;
	
	public PravnoLiceFilter(String jmbg, String ime, String prezime, String adresa, String telefon, String email,
			String pib, String fax, String odobrio, Long mesto, Long delatnost) {
		super();
		this.jmbg = jmbg;
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
		this.telefon = telefon;
		this.email = email;
		this.pib = pib;
		this.fax = fax;
		this.odobrio = odobrio;
		this.mesto = mesto;
		this.delatnost = delatnost;
	}

	public PravnoLiceFilter() {
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

	public String getPib() {
		return pib;
	}

	public void setPib(String pib) {
		this.pib = pib;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getOdobrio() {
		return odobrio;
	}

	public void setOdobrio(String odobrio) {
		this.odobrio = odobrio;
	}

	public Long getMesto() {
		return mesto;
	}

	public void setMesto(Long mesto) {
		this.mesto = mesto;
	}

	public Long getDelatnost() {
		return delatnost;
	}

	public void setDelatnost(Long delatnost) {
		this.delatnost = delatnost;
	}
}
