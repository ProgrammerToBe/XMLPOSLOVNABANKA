package korenski.controller.institutions.pomocni;

import java.util.Date;

public class AnalitikaFilter {
	
	private String racunDuznika;
	private String modelDuznika;
	private String pozivNaBrojDuznika;
	
	private String racunPoverioca;
	private String modelPoverioca;
	private String pozivNaBrojPoverioca;
	
	private Date pocetakAnalitika;
	private Date krajAnalitika;
	
	private Date pocetakNalog;
	private Date krajNalog;
	
	private Date pocetakValuta;
	private Date krajValuta;
	
	private Boolean hitno;

	public AnalitikaFilter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AnalitikaFilter(String racunDuznika, String modelDuznika, String pozivNaBrojDuznika, String racunPoverioca,
			String modelPoverioca, String pozivNaBrojPoverioca, Date pocetakAnalitika, Date krajAnalitika,
			Date pocetakNalog, Date krajNalog, Date pocetakValuta, Date krajValuta, Boolean hitno) {
		super();
		this.racunDuznika = racunDuznika;
		this.modelDuznika = modelDuznika;
		this.pozivNaBrojDuznika = pozivNaBrojDuznika;
		this.racunPoverioca = racunPoverioca;
		this.modelPoverioca = modelPoverioca;
		this.pozivNaBrojPoverioca = pozivNaBrojPoverioca;
		this.pocetakAnalitika = pocetakAnalitika;
		this.krajAnalitika = krajAnalitika;
		this.pocetakNalog = pocetakNalog;
		this.krajNalog = krajNalog;
		this.pocetakValuta = pocetakValuta;
		this.krajValuta = krajValuta;
		this.hitno = hitno;
	}

	public String getRacunDuznika() {
		return racunDuznika;
	}

	public void setRacunDuznika(String racunDuznika) {
		this.racunDuznika = racunDuznika;
	}

	public String getModelDuznika() {
		return modelDuznika;
	}

	public void setModelDuznika(String modelDuznika) {
		this.modelDuznika = modelDuznika;
	}

	public String getPozivNaBrojDuznika() {
		return pozivNaBrojDuznika;
	}

	public void setPozivNaBrojDuznika(String pozivNaBrojDuznika) {
		this.pozivNaBrojDuznika = pozivNaBrojDuznika;
	}

	public String getRacunPoverioca() {
		return racunPoverioca;
	}

	public void setRacunPoverioca(String racunPoverioca) {
		this.racunPoverioca = racunPoverioca;
	}

	public String getModelPoverioca() {
		return modelPoverioca;
	}

	public void setModelPoverioca(String modelPoverioca) {
		this.modelPoverioca = modelPoverioca;
	}

	public String getPozivNaBrojPoverioca() {
		return pozivNaBrojPoverioca;
	}

	public void setPozivNaBrojPoverioca(String pozivNaBrojPoverioca) {
		this.pozivNaBrojPoverioca = pozivNaBrojPoverioca;
	}

	public Date getPocetakAnalitika() {
		return pocetakAnalitika;
	}

	public void setPocetakAnalitika(Date pocetakAnalitika) {
		this.pocetakAnalitika = pocetakAnalitika;
	}

	public Date getKrajAnalitika() {
		return krajAnalitika;
	}

	public void setKrajAnalitika(Date krajAnalitika) {
		this.krajAnalitika = krajAnalitika;
	}

	public Date getPocetakNalog() {
		return pocetakNalog;
	}

	public void setPocetakNalog(Date pocetakNalog) {
		this.pocetakNalog = pocetakNalog;
	}

	public Date getKrajNalog() {
		return krajNalog;
	}

	public void setKrajNalog(Date krajNalog) {
		this.krajNalog = krajNalog;
	}

	public Date getPocetakValuta() {
		return pocetakValuta;
	}

	public void setPocetakValuta(Date pocetakValuta) {
		this.pocetakValuta = pocetakValuta;
	}

	public Date getKrajValuta() {
		return krajValuta;
	}

	public void setKrajValuta(Date krajValuta) {
		this.krajValuta = krajValuta;
	}

	public Boolean isHitno() {
		return hitno;
	}

	public void setHitno(Boolean hitno) {
		this.hitno = hitno;
	}
	
	

}
