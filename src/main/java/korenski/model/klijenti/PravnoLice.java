package korenski.model.klijenti;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import korenski.model.geografija.Drzava;
import korenski.model.geografija.NaseljenoMesto;
import korenski.model.sifrarnici.Activity;

@Entity
@Table(name="pravno_lice")
public class PravnoLice extends Klijent {
	
	@Column(nullable = false, length = 10)
	@Pattern(regexp = "[0-9]{10}")
	private String pib;
	
	@Column(nullable = false)
	@Size(max = 20)
	@Pattern(regexp = "[0-9a-zA-Z]+")
	private String fax;
	
	@Column(nullable = false)
	@Size(max = 60)
	@Pattern(regexp = "[A-Z][a-z]*")
	private String odobrio;
	
	@ManyToOne
	private Activity activity;
	
	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public PravnoLice(Long id, String jmbg, String ime, String prezime, String adresa, String telefon, String email,
			NaseljenoMesto naseljenoMesto, String pib, String fax, String odobrio) {
		super(id, jmbg, ime, prezime, adresa, telefon, email, naseljenoMesto);
		this.pib = pib;
		this.fax = fax;
		this.odobrio = odobrio;
	}

	public PravnoLice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PravnoLice(Long id, String jmbg, String ime, String prezime, String adresa, String telefon, String email,
			NaseljenoMesto naseljenoMesto) {
		super(id, jmbg, ime, prezime, adresa, telefon, email, naseljenoMesto);
		// TODO Auto-generated constructor stub
	}

	public String getPib() {
		return pib;
	}

	public void setPib(String pib) {
		this.pib = pib;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getOdobrio() {
		return odobrio;
	}

	public void setOdobrio(String odobrio) {
		this.odobrio = odobrio;
	}
	
}
