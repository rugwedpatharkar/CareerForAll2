package com.placementportal.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "institute")
public class Institute {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long instituteid;
	private String institutename;
	private String brandname;
	private String email;
	private Date startdate;
	private String state;
	private String maincampuscity;
	private String othercampuscity;
	private String foundername;
	private String directororprinciple;
	private String companywebsite;
	private String companylinkedin;
	private String anyotherrelevantlink;
	private byte[] corporatepresentation;
	private byte[] anyotherfileupload;
	private String specialization;
	private String batchsize;
	private Integer noofcampuses;
	private BigDecimal medianctc;
	private BigDecimal averagectc;
	private BigDecimal summermedianctc;
	private String preferredsectors;
	private Date summerplacement;
	private Date finalplacement;
	private Date winterplacement;
	private Date liveprojects;
	
	   @OneToMany(mappedBy = "institutename", cascade = CascadeType.ALL)
	    private List<Candidate> candidates;

	public Institute() {
		super();

	}

	public Long getInstituteid() {
		return instituteid;
	}

	public void setInstituteid(Long instituteid) {
		this.instituteid = instituteid;
	}

	public String getInstitutename() {
		return institutename;
	}

	public void setInstitutename(String institutename) {
		this.institutename = institutename;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMaincampuscity() {
		return maincampuscity;
	}

	public void setMaincampuscity(String maincampuscity) {
		this.maincampuscity = maincampuscity;
	}

	public String getOthercampuscity() {
		return othercampuscity;
	}

	public void setOthercampuscity(String othercampuscity) {
		this.othercampuscity = othercampuscity;
	}

	public String getFoundername() {
		return foundername;
	}

	public void setFoundername(String foundername) {
		this.foundername = foundername;
	}

	public String getDirectororprinciple() {
		return directororprinciple;
	}

	public void setDirectororprinciple(String directororprinciple) {
		this.directororprinciple = directororprinciple;
	}

	public String getCompanywebsite() {
		return companywebsite;
	}

	public void setCompanywebsite(String companywebsite) {
		this.companywebsite = companywebsite;
	}

	public String getCompanylinkedin() {
		return companylinkedin;
	}

	public void setCompanylinkedin(String companylinkedin) {
		this.companylinkedin = companylinkedin;
	}

	public String getAnyotherrelevantlink() {
		return anyotherrelevantlink;
	}

	public void setAnyotherrelevantlink(String anyotherrelevantlink) {
		this.anyotherrelevantlink = anyotherrelevantlink;
	}

	public byte[] getCorporatepresentation() {
		return corporatepresentation;
	}

	public void setCorporatepresentation(byte[] corporatepresentation) {
		this.corporatepresentation = corporatepresentation;
	}

	public byte[] getAnyotherfileupload() {
		return anyotherfileupload;
	}

	public void setAnyotherfileupload(byte[] anyotherfileupload) {
		this.anyotherfileupload = anyotherfileupload;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getBatchsize() {
		return batchsize;
	}

	public void setBatchsize(String batchsize) {
		this.batchsize = batchsize;
	}

	public Integer getNoofcampuses() {
		return noofcampuses;
	}

	public void setNoofcampuses(Integer noofcampuses) {
		this.noofcampuses = noofcampuses;
	}

	public BigDecimal getMedianctc() {
		return medianctc;
	}

	public void setMedianctc(BigDecimal medianctc) {
		this.medianctc = medianctc;
	}

	public BigDecimal getAveragectc() {
		return averagectc;
	}

	public void setAveragectc(BigDecimal averagectc) {
		this.averagectc = averagectc;
	}

	public BigDecimal getSummermedianctc() {
		return summermedianctc;
	}

	public void setSummermedianctc(BigDecimal summermedianctc) {
		this.summermedianctc = summermedianctc;
	}

	public String getPreferredsectors() {
		return preferredsectors;
	}

	public void setPreferredsectors(String preferredsectors) {
		this.preferredsectors = preferredsectors;
	}

	public Date getSummerplacement() {
		return summerplacement;
	}

	public void setSummerplacement(Date summerplacement) {
		this.summerplacement = summerplacement;
	}

	public Date getFinalplacement() {
		return finalplacement;
	}

	public void setFinalplacement(Date finalplacement) {
		this.finalplacement = finalplacement;
	}

	public Date getWinterplacement() {
		return winterplacement;
	}

	public void setWinterplacement(Date winterplacement) {
		this.winterplacement = winterplacement;
	}

	public Date getLiveprojects() {
		return liveprojects;
	}

	public void setLiveprojects(Date liveprojects) {
		this.liveprojects = liveprojects;
	}

	@Override
	public String toString() {
		return "Institute [instituteid=" + instituteid + ", institutename=" + institutename + ", brandname=" + brandname
				+ ", startdate=" + startdate + ", state=" + state + ", maincampuscity=" + maincampuscity
				+ ", othercampuscity=" + othercampuscity + ", foundername=" + foundername + ", directororprinciple="
				+ directororprinciple + ", companywebsite=" + companywebsite + ", companylinkedin=" + companylinkedin
				+ ", anyotherrelevantlink=" + anyotherrelevantlink + ", corporatepresentation="
				+ Arrays.toString(corporatepresentation) + ", anyotherfileupload=" + Arrays.toString(anyotherfileupload)
				+ ", specialization=" + specialization + ", batchsize=" + batchsize + ", noofcampuses=" + noofcampuses
				+ ", medianctc=" + medianctc + ", averagectc=" + averagectc + ", summermedianctc=" + summermedianctc
				+ ", preferredsectors=" + preferredsectors + ", summerplacement=" + summerplacement
				+ ", finalplacement=" + finalplacement + ", winterplacement=" + winterplacement + ", liveprojects="
				+ liveprojects + ", getInstituteid()=" + getInstituteid() + ", getInstitutename()=" + getInstitutename()
				+ ", getBrandname()=" + getBrandname() + ", getStartdate()=" + getStartdate() + ", getState()="
				+ getState() + ", getMaincampuscity()=" + getMaincampuscity() + ", getOthercampuscity()="
				+ getOthercampuscity() + ", getFoundername()=" + getFoundername() + ", getDirectororprinciple()="
				+ getDirectororprinciple() + ", getCompanywebsite()=" + getCompanywebsite() + ", getCompanylinkedin()="
				+ getCompanylinkedin() + ", getAnyotherrelevantlink()=" + getAnyotherrelevantlink()
				+ ", getCorporatepresentation()=" + Arrays.toString(getCorporatepresentation())
				+ ", getAnyotherfileupload()=" + Arrays.toString(getAnyotherfileupload()) + ", getSpecialization()="
				+ getSpecialization() + ", getBatchsize()=" + getBatchsize() + ", getNoofcampuses()="
				+ getNoofcampuses() + ", getMedianctc()=" + getMedianctc() + ", getAveragectc()=" + getAveragectc()
				+ ", getSummermedianctc()=" + getSummermedianctc() + ", getPreferredsectors()=" + getPreferredsectors()
				+ ", getSummerplacement()=" + getSummerplacement() + ", getFinalplacement()=" + getFinalplacement()
				+ ", getWinterplacement()=" + getWinterplacement() + ", getLiveprojects()=" + getLiveprojects()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

}
