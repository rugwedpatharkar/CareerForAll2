package com.placementportal.model;

import java.math.BigDecimal;
import java.util.Arrays;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Candidate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long candidateid;

	private String candidatename;

	private String email;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] cvupload;
	  
	private byte[] otherrelevantupload;

	private String primaryskills;

	private String secondaryskills;

	private String experience;

	private String noofyearsworkex;

	private String workmode;

	private String gender;

	private String candidatecity;

	private String icormanager;

	private String joborinternship;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "instituteid")
	private Institute institutename;

	private String ctcfixed;

	private BigDecimal ctcvariable;

	private BigDecimal ctctotal;

	public Candidate() {
		super();
	}

	public Long getCandidateid() {
		return candidateid;
	}

	public void setCandidateid(Long candidateid) {
		this.candidateid = candidateid;
	}

	public String getCandidatename() {
		return candidatename;
	}

	public void setCandidatename(String candidatename) {
		this.candidatename = candidatename;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getCvupload() {
		return cvupload;
	}

	public void setCvupload(byte[] cvupload) {
		this.cvupload = cvupload;
	}

	public byte[] getOtherrelevantupload() {
		return otherrelevantupload;
	}

	public void setOtherrelevantupload(byte[] otherrelevantupload) {
		this.otherrelevantupload = otherrelevantupload;
	}

	public String getPrimaryskills() {
		return primaryskills;
	}

	public void setPrimaryskills(String primaryskills) {
		this.primaryskills = primaryskills;
	}

	public String getSecondaryskills() {
		return secondaryskills;
	}

	public void setSecondaryskills(String secondaryskills) {
		this.secondaryskills = secondaryskills;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getNoofyearsworkex() {
		return noofyearsworkex;
	}

	public void setNoofyearsworkex(String noofyearsworkex) {
		this.noofyearsworkex = noofyearsworkex;
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

	public String getCandidatecity() {
		return candidatecity;
	}

	public void setCandidatecity(String candidatecity) {
		this.candidatecity = candidatecity;
	}

	public String getIcormanager() {
		return icormanager;
	}

	public void setIcormanager(String icormanager) {
		this.icormanager = icormanager;
	}

	public String getJoborinternship() {
		return joborinternship;
	}

	public void setJoborinternship(String joborinternship) {
		this.joborinternship = joborinternship;
	}

	public Institute getInstitutename() {
		return institutename;
	}

	public void setInstitutename(Institute institutename) {
		this.institutename = institutename;
	}

	public String getCtcfixed() {
		return ctcfixed;
	}

	public void setCtcfixed(String ctcfixed) {
		this.ctcfixed = ctcfixed;
	}

	public BigDecimal getCtcvariable() {
		return ctcvariable;
	}

	@Override
	public String toString() {
		return "Candidate [candidateid=" + candidateid + ", candidatename=" + candidatename + ", email=" + email
				+ ", cvupload=" + Arrays.toString(cvupload) + ", otherrelevantupload="
				+ Arrays.toString(otherrelevantupload) + ", primaryskills=" + primaryskills + ", secondaryskills="
				+ secondaryskills + ", experience=" + experience + ", noofyearsworkex=" + noofyearsworkex
				+ ", workmode=" + workmode + ", gender=" + gender + ", candidatecity=" + candidatecity
				+ ", icormanager=" + icormanager + ", joborinternship=" + joborinternship + ", institutename="
				+ institutename + ", ctcfixed=" + ctcfixed + ", ctcvariable=" + ctcvariable + ", ctctotal=" + ctctotal
				+ ", getCandidateid()=" + getCandidateid() + ", getCandidatename()=" + getCandidatename()
				+ ", getEmail()=" + getEmail() + ", getCvupload()=" + Arrays.toString(getCvupload())
				+ ", getOtherrelevantupload()=" + Arrays.toString(getOtherrelevantupload()) + ", getPrimaryskills()="
				+ getPrimaryskills() + ", getSecondaryskills()=" + getSecondaryskills() + ", getExperience()="
				+ getExperience() + ", getNoofyearsworkex()=" + getNoofyearsworkex() + ", getWorkmode()="
				+ getWorkmode() + ", getGender()=" + getGender() + ", getCandidatecity()=" + getCandidatecity()
				+ ", getIcormanager()=" + getIcormanager() + ", getJoborinternship()=" + getJoborinternship()
				+ ", getInstitutename()=" + getInstitutename() + ", getCtcfixed()=" + getCtcfixed()
				+ ", getCtcvariable()=" + getCtcvariable() + ", getCtctotal()=" + getCtctotal() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public void setCtcvariable(BigDecimal ctcvariable) {
		this.ctcvariable = ctcvariable;
	}

	public BigDecimal getCtctotal() {
		return ctctotal;
	}

	public void setCtctotal(BigDecimal ctctotal) {
		this.ctctotal = ctctotal;
	}

}
