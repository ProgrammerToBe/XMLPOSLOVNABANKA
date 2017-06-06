package korenski.ocsp;

public class Request {
	private CertID reqCert;

	
	public Request() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Request(CertID reqCert) {
		super();
		this.reqCert = reqCert;
	}

	public CertID getReqCert() {
		return reqCert;
	}

	public void setReqCert(CertID reqCert) {
		this.reqCert = reqCert;
	}
	
	
}
