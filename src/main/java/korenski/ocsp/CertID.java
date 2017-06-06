package korenski.ocsp;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CertID {
	public enum HashAlgorithm {
		SHA256WithRSA, SHA1withRSA, 
	}

	private  HashAlgorithm hashAlgorithm;
	private String issuerNameHash;
	private String issuerPublicKeyHash;
	private BigInteger seriaNumber;
	
	public CertID(HashAlgorithm hashAlgorithm,String issuerNameHash, String issuerPublicKeyHash, BigInteger seriaNumber) {
		super();
		this.hashAlgorithm=hashAlgorithm;
		this.issuerNameHash = issuerNameHash;
		this.issuerPublicKeyHash = issuerPublicKeyHash;
		this.seriaNumber = seriaNumber;
	}
	public CertID() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getIssuerNameHash() {
		return issuerNameHash;
	}
	public void setIssuerNameHash(String issuerNameHash) {
		this.issuerNameHash = issuerNameHash;
	}
	public String getIssuerPublicKeyHash() {
		return issuerPublicKeyHash;
	}
	public void setIssuerPublicKeyHash(String issuerPublicKeyHash) {
		this.issuerPublicKeyHash = issuerPublicKeyHash;
	}
	public BigInteger getSeriaNumber() {
		return seriaNumber;
	}
	public void setSeriaNumber(BigInteger seriaNumber) {
		this.seriaNumber = seriaNumber;
	}
	public  HashAlgorithm getHashAlgorithm() {
		return hashAlgorithm;
	}
	public  void setHashAlgorithm(HashAlgorithm hashAlgorithm) {
		this.hashAlgorithm = hashAlgorithm;
	}
	
	
}
