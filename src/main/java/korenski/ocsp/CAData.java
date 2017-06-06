package korenski.ocsp;

import java.security.PrivateKey;
import java.security.cert.Certificate;

public class CAData {
	private Certificate certificate;
	private PrivateKey privateKey;
	
	
	
	public CAData() {
		super();
		// TODO Auto-generated constructor stub
	}



	public CAData(Certificate certificate, PrivateKey privateKey) {
		super();
		this.certificate = certificate;
		this.privateKey = privateKey;
	}



	public Certificate getCertificate() {
		return certificate;
	}



	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}



	public PrivateKey getPrivateKey() {
		return privateKey;
	}



	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}
	
	
}
