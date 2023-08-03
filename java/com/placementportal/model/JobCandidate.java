package com.placementportal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "jobcandidate")
public class JobCandidate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "jobcandidateid")
	private Long jobcandidateid;

	@ManyToOne
	@JoinColumn(name = "positionid")
	private Job job;

	@ManyToOne
	@JoinColumn(name = "candidateid")
	private Candidate candidate;

	@ManyToOne
	@JoinColumn(name = "companyid")
	private Company company;

	@ManyToOne
	@JoinColumn(name = "instituteid")
	private Institute institute;

	public JobCandidate() {
		super();
	}

	public Long getJobcandidateid() {
		return jobcandidateid;
	}

	public void setJobcandidateid(Long jobcandidateid) {
		this.jobcandidateid = jobcandidateid;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	@Override
	public String toString() {
		return "JobCandidate [jobcandidateid=" + jobcandidateid + ", job=" + job + ", candidate=" + candidate
				+ ", company=" + company + ", institute=" + institute + ", getJobcandidateid()=" + getJobcandidateid()
				+ ", getJob()=" + getJob() + ", getCandidate()=" + getCandidate() + ", getCompany()=" + getCompany()
				+ ", getInstitute()=" + getInstitute() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}