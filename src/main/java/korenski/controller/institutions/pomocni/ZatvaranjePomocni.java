package korenski.controller.institutions.pomocni;

public class ZatvaranjePomocni {

	private Long id;
	private String racun;
	
	public ZatvaranjePomocni(Long id, String racun) {
		super();
		this.id = id;
		this.racun = racun;
	}
	public ZatvaranjePomocni() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRacun() {
		return racun;
	}
	public void setRacun(String racun) {
		this.racun = racun;
	}
	
	
	
	
}
