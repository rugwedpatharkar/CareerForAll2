package com.placementportal.model;

import java.math.BigDecimal;
import java.util.Arrays;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
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

	@Column(columnDefinition = "TEXT")
	private String educationalDetailsJson;

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

	public Candidate(Long candidateid, String candidatename, String email, String educationalDetailsJson,
			byte[] cvupload, byte[] otherrelevantupload, String primaryskills, String secondaryskills,
			String experience, String noofyearsworkex, String workmode, String gender, String candidatecity,
			String icormanager, String joborinternship, Institute institutename, String ctcfixed,
			BigDecimal ctcvariable, BigDecimal ctctotal) {
		super();
		this.candidateid = candidateid;
		this.candidatename = candidatename;
		this.email = email;
		this.educationalDetailsJson = educationalDetailsJson;
		this.cvupload = cvupload;
		this.otherrelevantupload = otherrelevantupload;
		this.primaryskills = primaryskills;
		this.secondaryskills = secondaryskills;
		this.experience = experience;
		this.noofyearsworkex = noofyearsworkex;
		this.workmode = workmode;
		this.gender = gender;
		this.candidatecity = candidatecity;
		this.icormanager = icormanager;
		this.joborinternship = joborinternship;
		this.institutename = institutename;
		this.ctcfixed = ctcfixed;
		this.ctcvariable = ctcvariable;
		this.ctctotal = ctctotal;
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

	public String getEducationalDetailsJson() {
		return educationalDetailsJson;
	}

	public void setEducationalDetailsJson(String educationalDetailsJson) {
		this.educationalDetailsJson = educationalDetailsJson;
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

	public void setCtcvariable(BigDecimal ctcvariable) {
		this.ctcvariable = ctcvariable;
	}

	public BigDecimal getCtctotal() {
		return ctctotal;
	}

	public void setCtctotal(BigDecimal ctctotal) {
		this.ctctotal = ctctotal;
	}

	@Override
	public String toString() {
		return "Candidate [candidateid=" + candidateid + ", candidatename=" + candidatename + ", email=" + email
				+ ", educationalDetailsJson=" + educationalDetailsJson + ", cvupload=" + Arrays.toString(cvupload)
				+ ", otherrelevantupload=" + Arrays.toString(otherrelevantupload) + ", primaryskills=" + primaryskills
				+ ", secondaryskills=" + secondaryskills + ", experience=" + experience + ", noofyearsworkex="
				+ noofyearsworkex + ", workmode=" + workmode + ", gender=" + gender + ", candidatecity=" + candidatecity
				+ ", icormanager=" + icormanager + ", joborinternship=" + joborinternship + ", institutename="
				+ institutename + ", ctcfixed=" + ctcfixed + ", ctcvariable=" + ctcvariable + ", ctctotal=" + ctctotal
				+ "]";
	}

}
