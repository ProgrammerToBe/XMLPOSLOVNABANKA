package korenski.ocsp;

public class OCSPRequest {
	private TBSRequest tbsRequest;


	private Signature signature;
	
	public OCSPRequest(TBSRequest tbsRequest) {
		super();
		this.tbsRequest = tbsRequest;
	}

	public OCSPRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TBSRequest getTbsRequest() {
		return tbsRequest;
	}

	public void setTbsRequest(TBSRequest tbsRequest) {
		this.tbsRequest = tbsRequest;
	}

	public Signature getSignature() {
		return signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}
	
	
	

}
