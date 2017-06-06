package korenski.ocsp;

import java.util.Date;

import korenski.model.dto.CertificateInfo.CertStatus;

public class SingleResponse {

//	public enum CertStatus{
//		GOOD,REVOKED,UNKNOWN
//	}
	
	private CertStatus certStatus;
	private CertID certID;
	private Date thisUpdate;
	private Date nextUpdate;
	private RevocationInfo revocationInfo;
	
	public SingleResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SingleResponse(CertStatus certStatus, CertID certID, Date thisUpdate, Date nextUpdate, RevocationInfo revocationInfo) {
		super();
		this.certStatus = certStatus;
		this.certID = certID;
		this.thisUpdate = thisUpdate;
		this.nextUpdate = nextUpdate;
		this.revocationInfo=revocationInfo;
	}

	public CertStatus getCertStatus() {
		return certStatus;
	}

	public void setCertStatus(CertStatus certStatus) {
		this.certStatus = certStatus;
	}

	public CertID getCertID() {
		return certID;
	}

	public void setCertID(CertID certID) {
		this.certID = certID;
	}

	public Date getThisUpdate() {
		return thisUpdate;
	}

	public void setThisUpdate(Date thisUpdate) {
		this.thisUpdate = thisUpdate;
	}

	public Date getNextUpdate() {
		return nextUpdate;
	}

	public void setNextUpdate(Date nextUpdate) {
		this.nextUpdate = nextUpdate;
	}

	public RevocationInfo getRevocationInfo() {
		return revocationInfo;
	}

	public void setRevocationInfo(RevocationInfo revocationInfo) {
		this.revocationInfo = revocationInfo;
	}
	
	 
	
}
