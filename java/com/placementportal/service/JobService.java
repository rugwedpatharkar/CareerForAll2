package com.placementportal.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placementportal.model.Job;
import com.placementportal.model.JobCandidate;
import com.placementportal.repository.JobCandidateRepository;
import com.placementportal.repository.JobRepository;

@Service
public class JobService {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private JobCandidateRepository jobCandidateRepository;

	public void saveJob(Job job) {
		this.jobRepository.save(job);
	}

	// ******************* JoblistFilters and CandidateListfilters and
	// mappedcandidatelist Code (Rugwed patharkar , Chinmay wagh)
	// *********************

	// start
//joblistfilters
	public List<JobCandidate> getJobCandidatesByCompanyidAndPositionid(Long companyid, int positionid) {
		return jobCandidateRepository.findByCompanyCompanyidAndJobPositionid(companyid, positionid);
	}

	public Job getJobByPositionid(int positionid) {
		return jobRepository.findById(positionid).orElse(null);
	}

	public List<Job> getJobsPostedPast24Hours(Long companyid, Job job) {
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		Date past24Hours = calendar.getTime();

		return jobRepository.findJobByCompanyidAndIgnoreCase(companyid, past24Hours, job.getCountry(), job.getState(),
				job.getCity(), job.getWorkmode(), job.getNoworkexperience(), job.getPositiontype());
	}

	public List<Job> getJobsPostedPastWeek(Long companyid, Job job) {
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.WEEK_OF_YEAR, -1);
		Date pastWeek = calendar.getTime();

		return jobRepository.findJobByCompanyidAndIgnoreCase(companyid, pastWeek, job.getCountry(), job.getState(),
				job.getCity(), job.getWorkmode(), job.getNoworkexperience(), job.getPositiontype());
	}

	public List<Job> getJobsPostedPastMonth(Long companyid, Job job) {
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.MONTH, -1);
		Date pastMonth = calendar.getTime();

		return jobRepository.findJobByCompanyidAndIgnoreCase(companyid, pastMonth, job.getCountry(), job.getState(),
				job.getCity(), job.getWorkmode(), job.getNoworkexperience(), job.getPositiontype());
	}

	public List<Job> findJobsByCompanyId(Long companyid) {
		return jobRepository.findJobsByCompanyid(companyid);
	}

	public List<Job> findJobByCompanyIdAndCriteria(Long companyid, Job job) {

		return jobRepository.findJobByCompanyidAndIgnoreCase(companyid, null, job.getCountry(), job.getState(),
				job.getCity(), job.getWorkmode(), job.getNoworkexperience(), job.getPositiontype());

	}

	public List<Job> jobSearchByCompanyId(Long companyid, String keyword) {
		if (keyword != null) {
			return jobRepository.findJobByCompanyidAndIgnoreCase(companyid, keyword);
		}
		return findJobsByCompanyId(companyid);
	}

	public Job getJobbyId(int positionid) {
		return jobRepository.findById(positionid)
				.orElseThrow(() -> new IllegalArgumentException("Job not found with ID: " + positionid));
	}
	// end
	// ******************* JoblistFilters and CandidateListfilters and
	// mappedcandidatelist Code (Rugwed patharkar , Chinmay wagh)
	// *********************

	// Tanmayi job service

	public List<Job> getAllPositions() {
		return jobRepository.findAll();
	}

	public void savePosition(Job job) {
		this.jobRepository.save(job);
	}

	public Job getPositionById(int id) {
		return jobRepository.findById(id).orElse(null);
	}

	public void deletePositionById(int id) {
		this.jobRepository.deleteById(id);
	}

}