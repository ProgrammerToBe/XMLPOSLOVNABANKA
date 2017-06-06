package korenski.ocsp;

import java.util.Date;

public class RevocationInfo {

	private Date revocationTime;

	public RevocationInfo(Date revocationTime) {
		super();
		this.revocationTime = revocationTime;
	}

	public RevocationInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Date getRevocationTime() {
		return revocationTime;
	}

	public void setRevocationTime(Date revocationTime) {
		this.revocationTime = revocationTime;
	}
	
	
}
