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


    public void mapCandidateToJob(int positionid, Long candidateid) {
        Job job = new Job();
        job.setPositionid(positionid);

        Candidate candidate = new Candidate();
        candidate.setCandidateid(candidateid);

        JobCandidate jobCandidate = new JobCandidate();
        jobCandidate.setJob(job);
        jobCandidate.setCandidate(candidate);

        JobcandidateRepository.save(jobCandidate);
    }
 

}