package com.placementportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.placementportal.model.Company;
import com.placementportal.model.Job;
import com.placementportal.model.JobCandidate;
import com.placementportal.repository.JobCandidateRepository;

@Service
public class JobCandidateService {

	@Autowired
	private JobCandidateRepository jobCandidateRepository;

	// ******************* JoblistFilters and CandidateListfilters and
	// mappedcandidatelist Code (Rugwed patharkar , Chinmay wagh)
	// *********************

	// start

	public JobCandidate saveJobCandidate(JobCandidate jobCandidate) {
		return jobCandidateRepository.save(jobCandidate);
	}

	public Page<JobCandidate> getJobCandidatesByCompanyAndJob(Company company, Job job, Pageable pageable) {
		return jobCandidateRepository.findByCompanyAndJob(company, job, pageable);
	}

	@Transactional
	public void deleteByJobCandidateIdIn(List<Long> jobcandidateids) {
		jobCandidateRepository.deleteByJobcandidateidIn(jobcandidateids);
	}
	// end
	// ******************* JoblistFilters and CandidateListfilters and
	// mappedcandidatelist Code (Rugwed patharkar , Chinmay wagh)
	// *********************

}