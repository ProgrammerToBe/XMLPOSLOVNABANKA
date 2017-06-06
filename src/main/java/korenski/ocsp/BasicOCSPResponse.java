package korenski.ocsp;
/**
 * 
 * @author Biljana
 *
 */
public class BasicOCSPResponse {
	public enum AlgorithmIdentifier {
		SHA256WithRSAEncryption, SHA1withRSAencryption
	}

	private AlgorithmIdentifier algorithmIdentifier;
	private ResponseData responseData;
	private byte[] signature;
	
	public BasicOCSPResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BasicOCSPResponse(AlgorithmIdentifier algorithmIdentifier, ResponseData responseData, byte[] signature) {
		super();
		this.algorithmIdentifier = algorithmIdentifier;
		this.responseData = responseData;
		this.signature = signature;
	}

	public AlgorithmIdentifier getalgorithmIdentifier() {
		return algorithmIdentifier;
	}

	public void setalgorithmIdentifier(AlgorithmIdentifier algorithmIdentifier) {
		this.algorithmIdentifier = algorithmIdentifier;
	}

	public ResponseData getResponseData() {
		return responseData;
	}

	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}

	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}
	
	
	
}
