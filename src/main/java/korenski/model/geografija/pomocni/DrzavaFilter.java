package korenski.model.geografija.pomocni;

public class DrzavaFilter {

	private String oznaka;
	private String naziv;
	
	public DrzavaFilter() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DrzavaFilter(String oznaka, String naziv) {
		super();
		this.oznaka = oznaka;
		this.naziv = naziv;
	}
	public String getOznaka() {
		return oznaka;
	}
	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	
	
}
