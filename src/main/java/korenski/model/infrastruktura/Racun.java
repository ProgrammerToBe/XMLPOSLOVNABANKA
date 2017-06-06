package korenski.model.infrastruktura;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import korenski.model.klijenti.Klijent;

@Entity
@Table(name="racun")
public class Racun {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = true)
	private Long id;
	
	@Column(nullable = false)
	private String brojRacuna;
	
	@Column(nullable = false)
	private boolean status;
	
	@Column(nullable = false)
	private Date datumOtvaranja;
	
	@Column(nullable = false)
	private double stanje;
	
	@Column(nullable = true)
	private Date datumDeaktivacije;
	
	@NotNull
	@ManyToOne
	private Klijent klijent;
	
	@NotNull
	@ManyToOne
	private Bank bank;
	
	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Racun() {
		super();
		// TODO Auto-generated constructor stub
		this.stanje = 0;
	}

	public Racun(Long id, String brojRacuna, boolean status, Date datumOtvaranja, Date datumDeaktivacije,
			Klijent klijent) {
		super();
		this.id = id;
		this.brojRacuna = brojRacuna;
		this.status = status;
		this.datumOtvaranja = datumOtvaranja;
		this.datumDeaktivacije = datumDeaktivacije;
		this.klijent = klijent;
		this.stanje = 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrojRacuna() {
		return brojRacuna;
	}

	public void setBrojRacuna(String brojRacuna) {
		this.brojRacuna = brojRacuna;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getDatumOtvaranja() {
		return datumOtvaranja;
	}

	public void setDatumOtvaranja(Date datumOtvaranja) {
		this.datumOtvaranja = datumOtvaranja;
	}

	public Date getDatumDeaktivacije() {
		return datumDeaktivacije;
	}

	public void setDatumDeaktivacije(Date datumDeaktivacije) {
		this.datumDeaktivacije = datumDeaktivacije;
	}

	public Klijent getKlijent() {
		return klijent;
	}

	public void setKlijent(Klijent klijent) {
		this.klijent = klijent;
	}

	public double getStanje() {
		return stanje;
	}

	public void setStanje(double stanje) {
		this.stanje = stanje;
	}
	
	
	
}
