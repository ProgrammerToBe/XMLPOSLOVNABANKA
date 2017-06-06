package korenski.ocsp;

public class OCSPResponse {
	public enum OCSPResponseStatus{
		SUCCESSFUL, malformedRequest , MALFORMEDREQUEST, INTERNALERROR, TRYLATER,SIGREQUIRED, UNAUTHORIZED
	}
	
	private OCSPResponseStatus status;
	
    private	BasicOCSPResponse respnseBytes;
    
 
	public OCSPResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OCSPResponse(OCSPResponseStatus status) {
		super();
		this.status = status;
	}
	public OCSPResponse(OCSPResponseStatus status, BasicOCSPResponse respnseBytes) {
		super();
		this.status = status;
		this.respnseBytes = respnseBytes;
	}
	public OCSPResponseStatus getStatus() {
		return status;
	}
	public void setStatus(OCSPResponseStatus status) {
		this.status = status;
	}
	public BasicOCSPResponse getRespnseBytes() {
		return respnseBytes;
	}
	public void setRespnseBytes(BasicOCSPResponse respnseBytes) {
		this.respnseBytes = respnseBytes;
	}
	
    
	
    
	
}
