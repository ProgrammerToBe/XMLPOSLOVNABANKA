package korenski.model.autorizacija;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import korenski.model.infrastruktura.Bank;

@Entity
@Table(name="user")
public class User {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(unique = true, nullable = false)
	@Size(min = 6, max = 30)
	@NotEmpty
	@Pattern(regexp = "[\\w]{6,30}", message = "Korisnicko ime ima najmanje 6 a najvise 30 karaktera!")
	private String username;
	
	@Column(unique = true, nullable = false)
	@Email
	@NotEmpty
	private String email;
	
	
	@Column(nullable = false)
	//@Size(min=8, max = 25)
	//@Pattern(regexp = "[\\w]{8,25}")
	//@NotEmpty
	private String password;
	//private byte[] password;

	//@Column(nullable = false, length=64)
	@Column(nullable = true, length=64)
	private byte[] salt;
	
	@Column(name = "creationTime", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private java.util.Date creationTime;
	
	@Column(name = "changedFirstPassword")
	private boolean changedFirstPassword = false;
	
	@NotNull
	@ManyToOne
	@Valid
	private Role role;
	
	@NotNull
	@ManyToOne
	@Valid
	private Bank bank;

	
	@NotNull
	@OneToOne
	@Valid
	private Subject subject;
	
	public User() {
		super();

		this.changedFirstPassword = false;
	}

	
	
	public User(Long id, String username) {
		super();
		this.id = id;
		this.username = username;
	}



//	public User(Long id, String username, String email, byte[] password, Role role,
//			Bank bank, Date creationTime, Subject subject) {
//		super();
//		this.id = id;
//		this.username = username;
//		this.email = email;
//		this.password = password;
//		this.role = role;
//		this.bank = bank;
//		this.subject = subject;
//		this.creationTime = creationTime;
//		this.changedFirstPassword = false;
//	}
//
//	public User(String username, String email, byte[] password, Role role, Bank bank, Date creationTime, Subject subject) {
//		super();
//		this.username = username;
//		this.email = email;
//		this.password = password;
//		this.subject = subject;
//		this.role = role;
//		this.bank = bank;
//		this.creationTime = creationTime;
//		this.changedFirstPassword = false;
//	}

	
	public User(String username, String email, String password, Date creationTime,
			Role role, Bank bank, Subject subject) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.creationTime = creationTime;
		this.role = role;
		this.bank = bank;
		this.subject = subject;
		this.changedFirstPassword = false;
	}
	
	
	public Long getId() {
		return id;
	}

		
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	public byte[] getPassword() {
//		return password;
//	}
//
//	public void setPassword(byte[] password) {
//		this.password = password;
//	}

	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}


	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	public java.util.Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}

	public boolean isChangedFirstPassword() {
		return changedFirstPassword;
	}

	public void setChangedFirstPassword(boolean changedFirstPassword) {
		this.changedFirstPassword = changedFirstPassword;
	}

	
}
