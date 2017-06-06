package korenski.ocsp;

public class Signature {
	enum HashAlgorithm{
		SHA256WithRSAEncryption, SHA1withRSAencryption
	}

	private HashAlgorithm hashAlgorithm;
	private byte[] signature;
	
	public Signature() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Signature(HashAlgorithm hashAlgorithm, byte[] signature) {
		super();
		this.hashAlgorithm = hashAlgorithm;
		this.signature = signature;
	}
	 
	
	
}
