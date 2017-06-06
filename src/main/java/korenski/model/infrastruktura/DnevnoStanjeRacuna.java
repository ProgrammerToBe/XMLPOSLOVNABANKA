package korenski.model.infrastruktura;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class DnevnoStanjeRacuna {
	
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable=false)
	private Date datum;	
	@Column(nullable=false)
	private double prethodnoStanje;
	@Column(nullable=false)
	private double prometNaTeret;
	@Column(nullable=false)
	private double prometUKorist;
	@Column(nullable=false)
	private double novoStanje;
	@ManyToOne(optional=false)
	private Racun racun;
	
	public DnevnoStanjeRacuna() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public DnevnoStanjeRacuna(Long id, Date datum, double prethodnoStanje, double prometNaTeret,
			double prometUKorist, double novoStanje, Racun racun) {
		super();
		this.id = id;
		this.datum = datum;
		this.prethodnoStanje = prethodnoStanje;
		this.prometNaTeret = prometNaTeret;
		this.prometUKorist = prometUKorist;
		this.novoStanje = novoStanje;
		this.racun = racun;
	}

	

	public DnevnoStanjeRacuna(Date datum, double prethodnoStanje, double prometNaTeret,
			double prometUKorist, double novoStanje, Racun racun) {
		super();
		this.datum = datum;
		this.prethodnoStanje = prethodnoStanje;
		this.prometNaTeret = prometNaTeret;
		this.prometUKorist = prometUKorist;
		this.novoStanje = novoStanje;
		this.racun = racun;
	}


	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public double getPrethodnoStanje() {
		return prethodnoStanje;
	}

	public void setPrethodnoStanje(double prethodnoStanje) {
		this.prethodnoStanje = prethodnoStanje;
	}

	public double getPrometNaTeret() {
		return prometNaTeret;
	}

	public void setPrometNaTeret(double prometNaTeret) {
		this.prometNaTeret = prometNaTeret;
	}

	public double getPrometUKorist() {
		return prometUKorist;
	}

	public void setPrometUKorist(double prometUKorist) {
		this.prometUKorist = prometUKorist;
	}

	public double getNovoStanje() {
		return novoStanje;
	}

	public void setNovoStanje(double novoStanje) {
		this.novoStanje = novoStanje;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Racun getRacun() {
		return racun;
	}


	public void setRacun(Racun racun) {
		this.racun = racun;
	}
	
	

}
