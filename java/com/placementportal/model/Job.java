package com.placementportal.model;

import java.util.Arrays;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
	private String experience;
	private int noworkexperience;
	private String workmode;
	private String gender;
	private String localcandidate;
	private String icteamhandling;
	private int stipend;
	private int ctcvariable;
	private String anynotableperk;
	private String description;
	private byte[] releventfile;
	private String anyrelevantlink; 
	private String positiontype;
	
	
	 

	


	@Override
	public String toString() {
		return "Job [positionid=" + positionid + ", position=" + position + ", designation=" + designation
				+ ", functions=" + functions + ", department=" + department + ", country=" + country + ", state="
				+ state + ", city=" + city + ", corearea=" + corearea + ", experience=" + experience
				+ ", noworkexperience=" + noworkexperience + ", workmode=" + workmode + ", gender=" + gender
				+ ", localcandidate=" + localcandidate + ", icteamhandling=" + icteamhandling + ", stipend="
				+ stipend + ", ctcvariable=" + ctcvariable + ", anynotableperk=" + anynotableperk
				+ ", description=" + description + ", releventfile=" + Arrays.toString(releventfile)
				+ ", anyrelevantlink=" + anyrelevantlink + ", positiontype=" + positiontype
				+ ", getPositionid()=" + getPositionid() + ", getPosition()=" + getPosition() + ", getDesignation()="
				+ getDesignation() + ", getFunctions()=" + getFunctions() + ", getDepartment()=" + getDepartment()
				+ ", getCountry()=" + getCountry() + ", getState()=" + getState() + ", getCity()=" + getCity()
				+ ", getCorearea()=" + getCorearea() + ", getExperience()=" + getExperience()
				+ ", getNoworkexperience()=" + getNoworkexperience() + ", getWorkmode()=" + getWorkmode()
				+ ", getGender()=" + getGender() + ", getLocalcandidate()=" + getLocalcandidate()
				+ ", getIcteamhandling()=" + getIcteamhandling() + ", getStipend()=" + getStipend()
				+ ", getCtcvariable()=" + getCtcvariable() + ", getAnynotableperk()=" + getAnynotableperk()
				+ ", getDescription()=" + getDescription() + ", getReleventfile()="
				+ Arrays.toString(getReleventfile()) + ", getAnyrelevantlink()=" + getAnyrelevantlink()
				+ ", getPositiontype()=" + getPositiontype() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
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







	public String getExperience() {
		return experience;
	}







	public void setExperience(String experience) {
		this.experience = experience;
	}







	public int getNoworkexperience() {
		return noworkexperience;
	}







	public void setNoworkexperience(int noworkexperience) {
		this.noworkexperience = noworkexperience;
	}







	public String getWorkmode() {
		return workmode;
	}







	public void setWorkmode(String workmode) {
		this.workmode = workmode;
	}







	public String getGender() {
		return gender;
	}







	public void setGender(String gender) {
		this.gender = gender;
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







	public Job() {
		super();
	}



	

	

}