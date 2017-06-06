package korenski.model.infrastruktura;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ZatvaranjeRacuna {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = true)
	private Long id;
	
	@Column(nullable = true)
	private String racunPrebacenihSredstava;
	
	@Column(nullable = false)
	private Date datumDeaktivacije;
	
	@OneToOne
	Racun racun;

	
	
	
	public ZatvaranjeRacuna(String racunPrebacenihSredstava, Date datumDeaktivacije, Racun racun) {
		super();
		this.racunPrebacenihSredstava = racunPrebacenihSredstava;
		this.datumDeaktivacije = datumDeaktivacije;
		this.racun = racun;
	}

	public ZatvaranjeRacuna(String racunPrebacenihSredstava, Date datumDeaktivacije) {
		super();
		this.racunPrebacenihSredstava = racunPrebacenihSredstava;
		this.datumDeaktivacije = datumDeaktivacije;
	}

	public ZatvaranjeRacuna() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRacunPrebacenihSredstava() {
		return racunPrebacenihSredstava;
	}

	public void setRacunPrebacenihSredstava(String racunPrebacenihSredstava) {
		this.racunPrebacenihSredstava = racunPrebacenihSredstava;
	}

	public Date getDatumDeaktivacije() {
		return datumDeaktivacije;
	}

	public void setDatumDeaktivacije(Date datumDeaktivacije) {
		this.datumDeaktivacije = datumDeaktivacije;
	}

	public Racun getRacun() {
		return racun;
	}

	public void setRacun(Racun racun) {
		this.racun = racun;
	}
	
	
	
}
