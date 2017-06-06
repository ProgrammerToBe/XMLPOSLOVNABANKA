package korenski.model.dto;

import java.math.BigInteger;
import java.util.Date;

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
@Table(name="certificateID", uniqueConstraints={@UniqueConstraint(columnNames={"serialNumber","bank"})})
public class CertificateInfo {
	public enum Type{
		NationalBank, Bank, Company
	}
	public enum CertStatus { GOOD,REVOKED, UNKNOWN };  
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="serialNumber", unique=true)
	private BigInteger serialNumber;
	
	@Column(nullable=false)
	private CertStatus status;
	
	private Date dateOfRevocation;
	
	@JoinColumn(name="ca")
	@ManyToOne(optional=true, fetch=FetchType.LAZY)
	private CertificateInfo ca;
	@Column(nullable=false)
	private Type type;
	//@Column(unique=true)
	private String alias;
	@JoinColumn(name="bank")
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	private Bank bank;
	public CertificateInfo(){
		
	}

	public CertificateInfo( BigInteger serialNumber, CertStatus status, Date dateOfRevocation, CertificateInfo idOfCA, String alias, Type type) {
		super();
		
		this.serialNumber = serialNumber;
		this.status = status;
		this.dateOfRevocation = dateOfRevocation;
		this.ca = idOfCA;
		this.alias=alias;
		this.type=type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigInteger getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(BigInteger serialNumber) {
		this.serialNumber = serialNumber;
	}

	public CertStatus getStatus() {
		return status;
	}

	public void setStatus(CertStatus status) {
		this.status = status;
	}

	public Date getDateOfRevocation() {
		return dateOfRevocation;
	}

	public void setDateOfRevocation(Date dateOfRevocation2) {
		this.dateOfRevocation = dateOfRevocation2;
	}

	public CertificateInfo getIdOfCA() {
		return ca;
	}

	public void setIdOfCA(CertificateInfo ca) {
		this.ca = ca;
	}

	public CertificateInfo getCa() {
		return ca;
	}

	public void setCa(CertificateInfo ca) {
		this.ca = ca;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
	

}
