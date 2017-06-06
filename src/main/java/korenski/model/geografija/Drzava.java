package korenski.model.geografija;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="drzava")
public class Drzava {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	
	@Column(unique = true, nullable = false, length = 3)
	@Pattern(regexp = "[A-Z]{3}", message = "Oznaka drzave se mora sastojati od tacno 3 velika slova!")
	@NotEmpty
	private String oznaka;
	
	@Column(unique = true)
	@Size(max = 80, message = "Naziv drzave ne sme biti duzi od 80!")
	@Pattern(regexp = "([A-Z][a-zA-Z]*)([\\s\\\'-][a-zA-Z]*)*", message = "Naziv drzave podrazumeva veliko pocetno slovo!")
	@NotEmpty
	private String naziv;
	
	@Column(name="naseljenaMesta")
	@OneToMany()
	private Collection<NaseljenoMesto> naseljenaMesta;

	public Drzava() {
		super();
		// TODO Auto-generated constructor stub
		naseljenaMesta = new ArrayList<>();
	}

	
	
	public Drzava(Long id, String oznaka, String naziv, Collection<NaseljenoMesto> naseljenaMesta) {
		super();
		this.id = id;
		this.oznaka = oznaka;
		this.naziv = naziv;
		this.naseljenaMesta = naseljenaMesta;
	}



	public Drzava(String oznaka, String naziv) {
		super();
		this.oznaka = oznaka;
		this.naziv = naziv;
		this.naseljenaMesta = new ArrayList<>();
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

	@JsonIgnoreProperties(value={"drzava"}, allowSetters=true)
	public Collection<NaseljenoMesto> getNaseljenaMesta() {
		return naseljenaMesta;
	}

	public void setNaseljenaMesta(Collection<NaseljenoMesto> naseljenaMesta) {
		this.naseljenaMesta = naseljenaMesta;
	}
	
	
	
}
