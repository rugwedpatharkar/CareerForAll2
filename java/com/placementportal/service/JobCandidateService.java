package com.placementportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placementportal.model.Candidate;
import com.placementportal.model.Job;
import com.placementportal.model.JobCandidate;
import com.placementportal.repository.JobCandidateRepository;

@Service
public class JobCandidateService {
  
  @Autowired
  private JobCandidateRepository JobcandidateRepository;


    public void mapCandidateToJob(int position_id, Long candidate_id) {
        Job job = new Job();
        job.setPosition_id(position_id);

        Candidate candidate = new Candidate();
        candidate.setCandidate_id(candidate_id);

        JobCandidate jobCandidate = new JobCandidate();
        jobCandidate.setJob(job);
        jobCandidate.setCandidate(candidate);

        JobcandidateRepository.save(jobCandidate);
    }
 

}