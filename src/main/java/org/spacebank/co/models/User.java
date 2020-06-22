package org.spacebank.co.models;

import org.hibernate.annotations.NaturalId;
import org.spacebank.co.models.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),
		@UniqueConstraint(columnNames = { "email" }) })
public class User extends DateAudit {
	private static final long serialVersionUID = -4291071888343686356L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(min = 4, max = 40)
	private String firstName;
	
	@NotBlank
	@Size(min = 4, max = 40)
	private String lastName;
	
	@Size(min = 4, max = 40)
	private String middleName;
	
	@NotBlank
	@Size(min = 4, max = 16)
	private String cellNumber;
	
	@NotBlank
	@Size(min = 4, max = 200)
	private String address;

	@NotBlank
	@Size(max = 15)
	private String username;

	@NaturalId
	@NotBlank
	@Size(max = 100)
	@Email
	private String email;

	@NotBlank
	@Size(max = 100)
	private String password;
	
	private boolean enabled;
	

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User() {
	}

	public User(@NotBlank @Size(min = 4, max = 40) String firstName,
			@NotBlank @Size(min = 4, max = 40) String lastName, @Size(min = 4, max = 40) String middleName,
			@NotBlank @Size(min = 4, max = 40) String cellNumber, @NotBlank @Size(min = 4, max = 200) String address,
			@NotBlank @Size(max = 15) String username, @NotBlank @Size(max = 40) @Email String email,
			@NotBlank @Size(max = 100) String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.cellNumber = cellNumber;
		this.address = address;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getMiddleName() {
		return middleName;
	}


	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}


	public String getCellNumber() {
		return cellNumber;
	}


	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
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

	public String getPassword() {
		return password;
	}
	

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}