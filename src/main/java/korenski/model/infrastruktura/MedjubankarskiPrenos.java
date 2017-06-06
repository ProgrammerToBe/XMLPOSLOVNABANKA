package korenski.model.infrastruktura;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import korenski.model.sifrarnici.Message;

@Entity
public class MedjubankarskiPrenos {
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne(optional=false)
	private Bank bankaPrva;
	@ManyToOne(optional=false)
	private Bank bankaDruga;
	@ManyToOne(optional=false)
	private Message poruka;
	@Column(nullable=false)
	private Date datum;
	@Column(nullable=false)
	private BigDecimal iznos;
	
	public MedjubankarskiPrenos() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MedjubankarskiPrenos(Long id, Bank bankaPrva, Bank bankaDruga, Message poruka, Date datum,
			BigDecimal iznos) {
		super();
		this.id = id;
		this.bankaPrva = bankaPrva;
		this.bankaDruga = bankaDruga;
		this.poruka = poruka;
		this.datum = datum;
		this.iznos = iznos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Bank getBankaPrva() {
		return bankaPrva;
	}

	public void setBankaPrva(Bank bankaPrva) {
		this.bankaPrva = bankaPrva;
	}

	public Bank getBankaDruga() {
		return bankaDruga;
	}

	public void setBankaDruga(Bank bankaDruga) {
		this.bankaDruga = bankaDruga;
	}

	public Message getPoruka() {
		return poruka;
	}

	public void setPoruka(Message poruka) {
		this.poruka = poruka;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public BigDecimal getIznos() {
		return iznos;
	}

	public void setIznos(BigDecimal iznos) {
		this.iznos = iznos;
	}
	
	

}
