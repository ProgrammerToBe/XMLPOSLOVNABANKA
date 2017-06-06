package korenski.controller.autentifikacija.pomocneKlase;

public class LoginObject {
	
	private int id;
	private String username;
	private String password;
	private String url;
	
	public LoginObject(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		id = 1;
	}
	
	public LoginObject() {
		super();
		id = 1;
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
