package korenski.model.autorizacija;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(unique = true, nullable = false)
	@Size(max = 30)
	@NotEmpty
	private String name;
	
	@Column(name="permissions")
	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<Permission> permissions;

	public Role() {
		super();
		permissions = new ArrayList<>();
		
	}
	
	

	public Role(Long id, String name, Collection<Permission> permissions) {
		super();
		this.id = id;
		this.name = name;
		this.permissions = permissions;
	}



	public Role(String name) {
		super();
		this.name = name;
		permissions = new ArrayList<>();
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

	public Collection<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Collection<Permission> permissions) {
		this.permissions = permissions;
	}

	
	
	
}
