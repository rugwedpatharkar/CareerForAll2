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

	@Override
	public String toString() {
		return "JobCandidate [jobcandidateid=" + jobcandidateid + ", job=" + job + ", candidate=" + candidate
				+ ", getJobcandidateid()=" + getJobcandidateid() + ", getJob()=" + getJob() + ", getCandidate()="
				+ getCandidate() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
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

	public JobCandidate() {
		super();
	}

}