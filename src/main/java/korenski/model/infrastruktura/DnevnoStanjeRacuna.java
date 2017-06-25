package korenski.model.infrastruktura;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@XmlRootElement()
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
	@OneToMany(mappedBy="dnevnoStanjeRacuna", fetch= FetchType.EAGER)
	private Set<AnalitikaIzvoda> analitike;
	
	public DnevnoStanjeRacuna() {
		super();
		analitike=new HashSet<AnalitikaIzvoda>();
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
		analitike=new HashSet<AnalitikaIzvoda>();
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
		analitike=new HashSet<AnalitikaIzvoda>();
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

	@XmlTransient
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	@XmlTransient
	//@JsonIgnoreProperties("")
	public Racun getRacun() {
		return racun;
	}


	public void setRacun(Racun racun) {
		this.racun = racun;
	}

	@JsonIgnoreProperties("dnevnoStanjeRacuna")
	public Set<AnalitikaIzvoda> getAnalitike() {
		if(analitike == null){
			analitike=new HashSet<AnalitikaIzvoda>();
		}
		return analitike;
	}


	public void setAnalitike(HashSet<AnalitikaIzvoda> analitiek) {
		this.analitike = analitiek;
	}
	
	

}
