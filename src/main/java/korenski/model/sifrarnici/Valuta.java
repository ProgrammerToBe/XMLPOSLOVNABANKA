package korenski.model.sifrarnici;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
//@Table(name="valuta")
public class Valuta {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique=true, nullable=false)
	private String code;
	
	@Column(unique=true, nullable=false)
	private String name;
	
	public Valuta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Valuta(Long id, String code, String name) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
