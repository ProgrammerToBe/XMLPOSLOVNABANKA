package korenski.controller.autentifikacija.pomocneKlase;

public class PasswordChanging {

	private int id;
	private String username;
	private String password;
	private String newPassword;

	public PasswordChanging(String username, String password, String newPassword) {
		super();
		this.username = username;
		this.password = password;
		this.newPassword = newPassword;
	}

	public PasswordChanging() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public int getId() {
		return id;
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

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setId(int i) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
