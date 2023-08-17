package com.placementportal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String fullname;
	private String email;
	private String password;
	private String role;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "companyid")
	private Company company_name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "instituteid")
	private Institute institutename;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
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

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Company getCompanyname() {
		return company_name;
	}

	public void setCompanyname(Company company_name) {
		this.company_name = company_name;
	}

	public Institute getInstitutename() {
		return institutename;
	}

	public void setInstitutename(Institute institutename) {
		this.institutename = institutename;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fullname=" + fullname + ", email=" + email + ", password=" + password + ", role="
				+ role + ", company_name=" + company_name + ", institutename=" + institutename + "]";
	}

	public User(int id, String fullname, String email, String password, String role, Company company_name,
			Institute institutename) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.email = email;
		this.password = password;
		this.role = role;
		this.company_name = company_name;
		this.institutename = institutename;
	}

	public User() {
		super();
		
	}
	
	

}
