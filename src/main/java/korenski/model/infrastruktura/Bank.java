package korenski.model.infrastruktura;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import korenski.model.klijenti.Klijent;

@Entity
public class Bank {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	@Size(max = 50)
	@Pattern(regexp = "([A-Z][a-zA-Z]*)([\\s\\\'-][a-zA-Z]*)*", message = "Naziv banke podrazumeva veliko pocetno slovo!")
	@NotEmpty
	private String name;
	
	@Column(nullable=false, unique=true)
	@Pattern(regexp="[0-9]{3}")
	@NotEmpty
	private String code;
	
	@Column(nullable=false, unique=true, length = 8)
	@Pattern(regexp="[0-9]{8}")
	@NotEmpty
	private String swiftCode;
	
	@Column(nullable=false)
	@Size(max = 18)
	@NotEmpty
	private String liquidationAcount;
	
	@Column(name="racuni")
	@OneToMany()
	private Collection<Racun> racuni;
	
	@Column(name="klijenti")
	@OneToMany()
	private Collection<Klijent> klijenti;
	
	public Collection<Racun> getRacuni() {
		return racuni;
	}

	public void setRacuni(Collection<Racun> racuni) {
		this.racuni = racuni;
	}

	public Collection<Klijent> getKlijenti() {
		return klijenti;
	}

	public void setKlijenti(Collection<Klijent> klijenti) {
		this.klijenti = klijenti;
	}

	public Bank(Long id, String name, String code, String swiftCode, String liquidationAcount) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.swiftCode = swiftCode;
		this.liquidationAcount = liquidationAcount;
	}

	public Bank() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public String getLiquidationAcount() {
		return liquidationAcount;
	}

	public void setLiquidationAcount(String liquidationAcount) {
		this.liquidationAcount = liquidationAcount;
	}

	public String getSwiftCode() {
		return swiftCode;
	}

	
	
}
