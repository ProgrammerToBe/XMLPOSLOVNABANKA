package korenski.ocsp;

import java.util.ArrayList;
import java.util.Date;

import korenski.ocsp.TBSRequest.Version;

public class ResponseData {
//	public enum Version{
//		V1,V2
//	}
	private Version version;
	private Date priducedAt;
	private ArrayList<SingleResponse> responses;
	
	public ResponseData(Version version, Date priducedAt) {
		super();
		this.version = version;
		this.priducedAt = priducedAt;
		this.responses=new ArrayList<SingleResponse>();
	}

	public ResponseData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public Date getPriducedAt() {
		return priducedAt;
	}

	public void setPriducedAt(Date priducedAt) {
		this.priducedAt = priducedAt;
	}

	public ArrayList<SingleResponse> getResponses() {
		return responses;
	}

	public void setResponses(ArrayList<SingleResponse> responses) {
		this.responses = responses;
	}
	
	public void add(SingleResponse singleResponse){
		responses.add(singleResponse);
	}
}
