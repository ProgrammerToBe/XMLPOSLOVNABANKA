package korenski.model.geografija;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="naseljeno_mesto")
public class NaseljenoMesto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(unique = true, nullable = false, length=2)
	@Pattern(regexp = "[A-Z]{2}", message = "Oznaka naseljenog se mora sastojati od tacno 2 velika slova!")
	@NotEmpty
	private String oznaka;
	
	@Column(unique = true, nullable = false)
	@Size(max = 80)
	@Pattern(regexp = "([A-Z][a-zA-Z]*)([\\s\\\'-][A-Z][a-zA-Z]*)*", message = "Naziv naseljenog mesta podrazumeva reci sa pocetnim velikim slovom!")
	@NotEmpty
	private String naziv;
	
	@Column(unique = true, nullable = false, length=5)
	@Pattern(regexp = "([0-9]{5})", message = "Postanski broj se sastoji od tacno 5 cifara!")
	@NotEmpty
	private String postanskiBroj;
	
	@NotNull
	@ManyToOne
	private Drzava drzava;

	public NaseljenoMesto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NaseljenoMesto(String oznaka, String naziv, String postanskiBroj, Drzava drzava) {
		super();
		this.oznaka = oznaka;
		this.naziv = naziv;
		this.postanskiBroj = postanskiBroj;
		this.drzava = drzava;
	}

	
	
	public NaseljenoMesto(Long id, String oznaka, String naziv, String postanskiBroj, Drzava drzava) {
		super();
		this.id = id;
		this.oznaka = oznaka;
		this.naziv = naziv;
		this.postanskiBroj = postanskiBroj;
		this.drzava = drzava;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Drzava getDrzava() {
		return drzava;
	}

	public void setDrzava(Drzava drzava) {
		this.drzava = drzava;
	}

	
	
}
