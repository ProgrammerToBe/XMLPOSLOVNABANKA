package korenski.model.infrastruktura;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AnalitikaIzvoda {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private Date datumAnalitike;

	@Column(nullable = false)
	private String smer;

	private String duznik;
	private String svrhaPlacanja;
	private String primalac;
	private Date datumNaloga;
	private Date datumValute;
	// duzina mora biti osamnjaest
	private String racunPrvi;
	private String modelPrvi;
	private String pozivNaBrojPrvi;
	private String racunDrugi;
	private String modelDrugi;
	private String pozivNaBrojDrugi;
	@Column(nullable = false)
	private double iznos;
	// Sifra valute bi mozda trebala da bude objekat
	// klase valute
	private String sifraValute;
	private boolean hitno;
	@ManyToOne(optional = false)
	private DnevnoStanjeRacuna dnevnoStanjeRacuna;

	public AnalitikaIzvoda() {
		super();
		// TODO Auto-generated constructor stub
	}

//	public AnalitikaIzvoda(Long id, Date datumAnalitike, String smer, String duznik, String svrhaPlacanja,
//			String primalac, Date datumNaloga, Date datumValute, String racunPrvi, String modelPrvi,
//			String pozivNaBrojPrvi, String racunDrugi, String modelDrugi, String pozivNaBrojDrugi, BigDecimal iznos,
//			String sifraValute, Boolean hitno, DnevnoStanjeRacuna dnevnoStanjeRacuna) {
//		super();
//		this.id = id;
//		this.datumAnalitike = datumAnalitike;
//		this.smer = smer;
//		this.duznik = duznik;
//		this.svrhaPlacanja = svrhaPlacanja;
//		this.primalac = primalac;
//		this.datumNaloga = datumNaloga;
//		this.datumValute = datumValute;
//		this.racunPrvi = racunPrvi;
//		this.modelPrvi = modelPrvi;
//		this.pozivNaBrojPrvi = pozivNaBrojPrvi;
//		this.racunDrugi = racunDrugi;
//		this.modelDrugi = modelDrugi;
//		this.pozivNaBrojDrugi = pozivNaBrojDrugi;
//		this.iznos = iznos;
//		this.sifraValute = sifraValute;
//		this.hitno = hitno;
//		this.dnevnoStanjeRacuna = dnevnoStanjeRacuna;
//	}

	public AnalitikaIzvoda(Date datumAnalitike, String smer, String duznik, String svrhaPlacanja, String primalac,
			Date datumNaloga, Date datumValute, String racunPrvi, String modelPrvi, String pozivNaBrojPrvi,
			String racunDrugi, String modelDrugi, String pozivNaBrojDrugi, double iznos, String sifraValute,
			boolean hitno, DnevnoStanjeRacuna dnevnoStanjeRacuna) {
		super();
		this.datumAnalitike = datumAnalitike;
		this.smer = smer;
		this.duznik = duznik;
		this.svrhaPlacanja = svrhaPlacanja;
		this.primalac = primalac;
		this.datumNaloga = datumNaloga;
		this.datumValute = datumValute;
		this.racunPrvi = racunPrvi;
		this.modelPrvi = modelPrvi;
		this.pozivNaBrojPrvi = pozivNaBrojPrvi;
		this.racunDrugi = racunDrugi;
		this.modelDrugi = modelDrugi;
		this.pozivNaBrojDrugi = pozivNaBrojDrugi;
		this.iznos = iznos;
		this.sifraValute = sifraValute;
		this.hitno = hitno;
		this.dnevnoStanjeRacuna = dnevnoStanjeRacuna;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDatumAnalitike() {
		return datumAnalitike;
	}

	public void setDatumAnalitike(Date datumAnalitike) {
		this.datumAnalitike = datumAnalitike;
	}

	public String getSmer() {
		return smer;
	}

	public void setSmer(String smer) {
		this.smer = smer;
	}

	public String getDuznik() {
		return duznik;
	}

	public void setDuznik(String duznik) {
		this.duznik = duznik;
	}

	public String getSvrhaPlacanja() {
		return svrhaPlacanja;
	}

	public void setSvrhaPlacanja(String svrhaPlacanja) {
		this.svrhaPlacanja = svrhaPlacanja;
	}

	public String getPrimalac() {
		return primalac;
	}

	public void setPrimalac(String primalac) {
		this.primalac = primalac;
	}

	public Date getDatumNaloga() {
		return datumNaloga;
	}

	public void setDatumNaloga(Date datumNaloga) {
		this.datumNaloga = datumNaloga;
	}

	public Date getDatumValute() {
		return datumValute;
	}

	public void setDatumValute(Date datumValute) {
		this.datumValute = datumValute;
	}

	public String getRacunPrvi() {
		return racunPrvi;
	}

	public void setRacunPrvi(String racunPrvi) {
		this.racunPrvi = racunPrvi;
	}

	public String getModelPrvi() {
		return modelPrvi;
	}

	public void setModelPrvi(String modelPrvi) {
		this.modelPrvi = modelPrvi;
	}

	public String getPozivNaBrojPrvi() {
		return pozivNaBrojPrvi;
	}

	public void setPozivNaBrojPrvi(String pozivNaBrojPrvi) {
		this.pozivNaBrojPrvi = pozivNaBrojPrvi;
	}

	public String getRacunDrugi() {
		return racunDrugi;
	}

	public void setRacunDrugi(String racunDrugi) {
		this.racunDrugi = racunDrugi;
	}

	public String getModelDrugi() {
		return modelDrugi;
	}

	public void setModelDrugi(String modelDrugi) {
		this.modelDrugi = modelDrugi;
	}

	public String getPozivNaBrojDrugi() {
		return pozivNaBrojDrugi;
	}

	public void setPozivNaBrojDrugi(String pozivNaBrojDrugi) {
		this.pozivNaBrojDrugi = pozivNaBrojDrugi;
	}

	public double getIznos() {
		return iznos;
	}

	public void setIznos(double iznos) {
		this.iznos = iznos;
	}

	public String getSifraValute() {
		return sifraValute;
	}

	public void setSifraValute(String sifraValute) {
		this.sifraValute = sifraValute;
	}

	public boolean getHitno() {
		return hitno;
	}

	public void setHitno(boolean hitno) {
		this.hitno = hitno;
	}

	public DnevnoStanjeRacuna getDnevnoStanjeRacuna() {
		return dnevnoStanjeRacuna;
	}

	public void setDnevnoStanjeRacuna(DnevnoStanjeRacuna dnevnoStanjeRacuna) {
		this.dnevnoStanjeRacuna = dnevnoStanjeRacuna;
	}

}
