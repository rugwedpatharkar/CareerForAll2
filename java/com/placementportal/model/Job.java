package com.placementportal.model;

import java.util.Arrays;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "position")
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int positionid;
	private String position;
	private String designation;
	private String functions;
	private String department;
	private String country;
	private String state;
	private String city;
	private String corearea;
	private String noworkexperience;
	private String workmode;
	private String localcandidate;
	private String icteamhandling;
	private int stipend;
	private int ctcvariable;
	private String anynotableperk;
	private String description;
	private byte[] releventfile;
	private String anyrelevantlink;
	private String positiontype;

	@Temporal(TemporalType.TIMESTAMP)
	private Date postedon;

	@ManyToOne
	@JoinColumn(name = "companyid")
	private Company company;

	@Override
	public String toString() {
		return "Job [positionid=" + positionid + ", position=" + position + ", designation=" + designation
				+ ", functions=" + functions + ", department=" + department + ", country=" + country + ", state="
				+ state + ", city=" + city + ", corearea=" + corearea + ", noworkexperience=" + noworkexperience
				+ ", workmode=" + workmode + ", localcandidate=" + localcandidate + ", icteamhandling=" + icteamhandling
				+ ", stipend=" + stipend + ", ctcvariable=" + ctcvariable + ", anynotableperk=" + anynotableperk
				+ ", description=" + description + ", releventfile=" + Arrays.toString(releventfile)
				+ ", anyrelevantlink=" + anyrelevantlink + ", positiontype=" + positiontype + ", postedon=" + postedon
				+ ", company=" + company + ", getPositionid()=" + getPositionid() + ", getPosition()=" + getPosition()
				+ ", getDesignation()=" + getDesignation() + ", getFunctions()=" + getFunctions() + ", getDepartment()="
				+ getDepartment() + ", getCountry()=" + getCountry() + ", getState()=" + getState() + ", getCity()="
				+ getCity() + ", getCorearea()=" + getCorearea() + ", getNoworkexperience()=" + getNoworkexperience()
				+ ", getWorkmode()=" + getWorkmode() + ", getLocalcandidate()=" + getLocalcandidate()
				+ ", getIcteamhandling()=" + getIcteamhandling() + ", getStipend()=" + getStipend()
				+ ", getCtcvariable()=" + getCtcvariable() + ", getAnynotableperk()=" + getAnynotableperk()
				+ ", getDescription()=" + getDescription() + ", getReleventfile()=" + Arrays.toString(getReleventfile())
				+ ", getAnyrelevantlink()=" + getAnyrelevantlink() + ", getPositiontype()=" + getPositiontype()
				+ ", getPostedon()=" + getPostedon() + ", getCompany()=" + getCompany() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public int getPositionid() {
		return positionid;
	}

	public void setPositionid(int positionid) {
		this.positionid = positionid;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getFunctions() {
		return functions;
	}

	public void setFunctions(String functions) {
		this.functions = functions;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCorearea() {
		return corearea;
	}

	public void setCorearea(String corearea) {
		this.corearea = corearea;
	}

	public String getNoworkexperience() {
		return noworkexperience;
	}

	public void setNoworkexperience(String noworkexperience) {
		this.noworkexperience = noworkexperience;
	}

	public String getWorkmode() {
		return workmode;
	}

	public void setWorkmode(String workmode) {
		this.workmode = workmode;
	}

	public String getLocalcandidate() {
		return localcandidate;
	}

	public void setLocalcandidate(String localcandidate) {
		this.localcandidate = localcandidate;
	}

	public String getIcteamhandling() {
		return icteamhandling;
	}

	public void setIcteamhandling(String icteamhandling) {
		this.icteamhandling = icteamhandling;
	}

	public int getStipend() {
		return stipend;
	}

	public void setStipend(int stipend) {
		this.stipend = stipend;
	}

	public int getCtcvariable() {
		return ctcvariable;
	}

	public void setCtcvariable(int ctcvariable) {
		this.ctcvariable = ctcvariable;
	}

	public String getAnynotableperk() {
		return anynotableperk;
	}

	public void setAnynotableperk(String anynotableperk) {
		this.anynotableperk = anynotableperk;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getReleventfile() {
		return releventfile;
	}

	public void setReleventfile(byte[] releventfile) {
		this.releventfile = releventfile;
	}

	public String getAnyrelevantlink() {
		return anyrelevantlink;
	}

	public void setAnyrelevantlink(String anyrelevantlink) {
		this.anyrelevantlink = anyrelevantlink;
	}

	public String getPositiontype() {
		return positiontype;
	}

	public void setPositiontype(String positiontype) {
		this.positiontype = positiontype;
	}

	public Date getPostedon() {
		return postedon;
	}

	public void setPostedon(Date postedon) {
		this.postedon = postedon;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Job() {
		super();
	}

}