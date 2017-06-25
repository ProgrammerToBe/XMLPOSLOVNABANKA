package korenski.model.infrastruktura;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import korenski.model.klijenti.Klijent;

@Entity
@Table(name="racun")
@XmlRootElement()
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
	
	@Column(nullable = false)
	private double rezervisano;
	
	@Column(name = "datumDeaktivacije", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date datumDeaktivacije;
	
	@NotNull
	@ManyToOne
	//@JoinColumn(referencedColumnName="klijent_id")
	private Klijent klijent;
	
	@NotNull
	@ManyToOne
	private Bank bank;
	
	@OneToMany(fetch=FetchType.EAGER)
	private List<DnevnoStanjeRacuna> dnevnaStanjaRacuna;
	
	@XmlTransient
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
		this.dnevnaStanjaRacuna=new ArrayList<DnevnoStanjeRacuna>();
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
		this.dnevnaStanjaRacuna=new ArrayList<DnevnoStanjeRacuna>();
	}

	@XmlTransient
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
	@XmlTransient
	@JsonIgnoreProperties({"racuni"})
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
	
	@JsonIgnoreProperties({"racun","bank"})
	public List<DnevnoStanjeRacuna> getDnevnaStanjaRacuna() {
		if(dnevnaStanjaRacuna == null){
			dnevnaStanjaRacuna = new ArrayList<DnevnoStanjeRacuna>();
			return dnevnaStanjaRacuna;
		}
		return dnevnaStanjaRacuna;
	}

	public void setDnevnaStanjaRacuna(List<DnevnoStanjeRacuna> dnevnaStanjaRacuna) {
		this.dnevnaStanjaRacuna = dnevnaStanjaRacuna;
	}

	public double getRezervisano() {
		return rezervisano;
	}

	public void setRezervisano(double rezervisano) {
		this.rezervisano = rezervisano;
	}
	
}
