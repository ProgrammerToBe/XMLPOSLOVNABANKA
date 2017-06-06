package korenski.ocsp;

import java.util.ArrayList;

public class TBSRequest {
	public enum Version{
		V1, V2;
	}
	
	private Version version;
	private String requestorName;
	private ArrayList<Request> requestList;
	public TBSRequest(Version version, String requestorName) {
		super();
		this.version = version;
		this.requestorName = requestorName;
		requestList=new ArrayList<Request>();
	}   
	
	public TBSRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Version getVersion() {
		return version;
	}
	public void setVersion(Version version) {
		this.version = version;
	}
	public String getRequestorName() {
		return requestorName;
	}
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}

	public ArrayList<Request> getRequestList() {
		return requestList;
	}

	public void setRequestList(ArrayList<Request> requestList) {
		this.requestList = requestList;
	}
	
	public void add(Request request){
		requestList.add(request);
	}
	
	

}
