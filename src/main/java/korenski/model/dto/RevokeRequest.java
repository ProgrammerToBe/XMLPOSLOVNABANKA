package korenski.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import korenski.model.infrastruktura.Bank;

@Entity
@Table(name="revokeRequest", uniqueConstraints={@UniqueConstraint(columnNames={"bank","alias"})})
public class RevokeRequest {
	
	@Id
	@GeneratedValue
	private Long id;
	@Column( nullable=false)
	//private BigInteger serialNumer;
	
	private String alias;
	
	private String comment;
	//@OneToMany
	@JoinColumn(name="bank")
	@ManyToOne(optional=true, fetch=FetchType.LAZY)
	private Bank bank;
//	public RevokeRequest(Long id, BigInteger serialNumer, String comment) {
//		super();
//		this.id = id;
//		//this.serialNumer = serialNumer;
//		this.comment = comment;
//	}
	public RevokeRequest(Long id, String alias, String comment, Bank bank) {
		super();
		this.id = id;
		this.alias = alias;
		this.comment = comment;
	}
	public RevokeRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
	
	
}
