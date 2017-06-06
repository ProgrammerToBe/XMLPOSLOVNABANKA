package korenski.model.klijenti;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import korenski.model.autorizacija.Subject;
import korenski.model.geografija.NaseljenoMesto;
import korenski.model.infrastruktura.Bank;
import korenski.model.infrastruktura.Racun;

@Entity
@Table(name="klijent")


public class Klijent extends Subject{
	
	
	@Column(nullable = false, length = 13)
	@Pattern(regexp = "[0-9]{13}", message = "Oznaka JMBG-a mora imati 13 cifara.")
	private String jmbg;
	
	@Column(nullable = false)
	@Size(max = 60)
	@Pattern(regexp = "[A-Z][a-z]*")
	private String ime;
	
	@Column(nullable = false)
	@Size(max = 60)
	@Pattern(regexp = "[A-Z][a-z]*")
	private String prezime;
	
	@Column(nullable = false)
	@Size(max = 60)
	@Pattern(regexp = "[0-9a-zA-Z]+")
	private String adresa;
	
	@Column(nullable = false)
	@Size(max = 20)
	//@Pattern(regexp = "\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\2([0-9]{4})")
	@Pattern(regexp = "[0-9]{9,15}")
	private String telefon;
	
	@Column(unique = true, nullable = false)
	@Size(max = 254)
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
	private String email;
	
	//sta da radim sa delom tip klijenta?
	
	@Column(nullable = false)
	private boolean fizickoLice;
	
	@ManyToOne
	private NaseljenoMesto naseljenoMesto;
	
	@ManyToOne
	private Bank bank;
	
	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	@Column(name="racuni")
	@OneToMany()
	private Collection<Racun> racuni;
	
	public boolean isFizickoLice() {
		return fizickoLice;
	}

	public void setFizickoLice(boolean fizickoLice) {
		this.fizickoLice = fizickoLice;
	}

	public Collection<Racun> getRacuni() {
		return racuni;
	}

	public void setRacuni(Collection<Racun> racuni) {
		this.racuni = racuni;
	}

	public Klijent(Long id, String jmbg, String ime, String prezime, String adresa, String telefon, String email,
			NaseljenoMesto naseljenoMesto) {
		super();
		//this.id = id;
		this.jmbg = jmbg;
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
		this.telefon = telefon;
		this.email = email;
		this.naseljenoMesto = naseljenoMesto;
	}

	public Klijent() {
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
	
	public NaseljenoMesto getNaseljenoMesto() {
		return naseljenoMesto;
	}

	public void setNaseljenoMesto(NaseljenoMesto naseljenoMesto) {
		this.naseljenoMesto = naseljenoMesto;
	}
}
