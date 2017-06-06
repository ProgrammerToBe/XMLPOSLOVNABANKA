package korenski.DTOs;

import java.math.BigInteger;
import java.util.Date;

public class CertificateDTO {
	public String alias;
	public String keyAlias;
	public String keyPassword;
	public String keyPasswordConf;
	public boolean selfSigned;
	public String issuerAlias;
	public String cn;
	public String surname;
	public String givenName;
	public String organization;
	public String organizationUnit;
	public String country;
	public String email;
	public String uid;
	public boolean ca;
	public Date validFrom;
	public Date validTo; 
	public String publicKey;
	public BigInteger serialNumber;
	public CertificateDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getKeyAlias() {
		return keyAlias;
	}

	public void setKeyAlias(String keyAlias) {
		this.keyAlias = keyAlias;
	}

	public String getKeyPassword() {
		return keyPassword;
	}

	public void setKeyPassword(String keyPassword) {
		this.keyPassword = keyPassword;
	}

	public String getKeyPasswordConf() {
		return keyPasswordConf;
	}

	public void setKeyPasswordConf(String keyPasswordConf) {
		this.keyPasswordConf = keyPasswordConf;
	}

	public boolean isSelfSigned() {
		return selfSigned;
	}

	public void setSelfSigned(boolean selfSigned) {
		this.selfSigned = selfSigned;
	}

	public String getIssuerAlias() {
		return issuerAlias;
	}

	public void setIssuerAlias(String issuerAlias) {
		this.issuerAlias = issuerAlias;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOrganizationUnit() {
		return organizationUnit;
	}

	public void setOrganizationUnit(String organizationUnit) {
		this.organizationUnit = organizationUnit;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public boolean isCa() {
		return ca;
	}

	public void setCa(boolean ca) {
		this.ca = ca;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	
}
