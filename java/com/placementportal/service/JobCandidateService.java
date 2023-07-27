package com.placementportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placementportal.model.JobCandidate;
import com.placementportal.repository.JobCandidateRepository;

@Service
public class JobCandidateService {

	@Autowired
	private JobCandidateRepository jobCandidateRepository;

	public JobCandidate saveJobCandidate(JobCandidate jobCandidate) {
		return jobCandidateRepository.save(jobCandidate);
	}

}