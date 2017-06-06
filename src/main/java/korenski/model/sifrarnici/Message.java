package korenski.model.sifrarnici;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Message {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false, unique=true)
	private String code;

	
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Message(Long id, String code) {
		super();
		this.id = id;
		this.code = code;
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
	
	
	
	

}
