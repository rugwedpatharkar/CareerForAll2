package com.placementportal.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placementportal.model.Candidate;
import com.placementportal.model.Job;
import com.placementportal.repository.CandidateRepository;
import com.placementportal.repository.JobRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class JobService {

	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private CandidateRepository candidateRepository;
	@Autowired
	private CandidateService candidateService;


	
	public void saveJob(Job job) {
		this.jobRepository.save(job);
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	//JoblistFilters and CandidateListfilters Code (Rugwed patharkar , Chinmay wagh)
	//start

	public List<Job> findJobList() {
		
		return jobRepository.findAll();
	}

	public List<Job> jobsearch(String keyword) {
		if (keyword != null) {
			return jobRepository.findJobByIgnoreCase(keyword);
		}
		return jobRepository.findAll();
	}
	
	
	public List<Candidate> findEligibleCandidates(int position_id, int minKeywordLength) {
        Job job = jobRepository.findById(position_id).orElse(null);
        if (job == null) {
            return Collections.emptyList();
        }

        String[] jobKeywords = job.getDescription().split(" ");
        List<Candidate> eligibleCandidates = new ArrayList<>();

        for (String keyword : jobKeywords) {
            if (keyword.length() > minKeywordLength) {
                List<Candidate> candidates = candidateService.findCandidatesByPrimarySkills(keyword);
                eligibleCandidates.addAll(candidates);
            }
        }

        return eligibleCandidates;
    }
	
	
	 public List<Candidate> findEligibleCandidates(int positionId, int minKeywordLength, String noofyearsworkex, String workmode, String joborinternship) {
	        Job job = jobRepository.findById(positionId).orElse(null);
	        if (job == null) {
	            return Collections.emptyList();
	        }

	        String[] jobKeywords = job.getDescription().split(" ");
	        List<Candidate> eligibleCandidates = new ArrayList<>();

	        for (String keyword : jobKeywords) {
	            if (keyword.length() > minKeywordLength) {
	                List<Candidate> candidates = candidateRepository.findCandidatesByPrimarySkills(keyword);
	                eligibleCandidates.addAll(candidates);
	            }
	        }

	        eligibleCandidates = filterCandidates(eligibleCandidates, noofyearsworkex, workmode, joborinternship);

	        return eligibleCandidates;
	    }
	 

	    private List<Candidate> filterCandidates(List<Candidate> candidates, String noofyearsworkex, String workmode, String joborinternship) {
	        List<Candidate> listcandidate = new ArrayList<>(candidates);

	        if (StringUtils.isNotBlank(noofyearsworkex)) {
	        	listcandidate.removeIf(candidate -> candidate.getNoofyearsworkex().equalsIgnoreCase(noofyearsworkex));
	        }
	        if (StringUtils.isNotBlank(workmode)) {
	        	listcandidate.removeIf(candidate -> !candidate.getWorkmode().equalsIgnoreCase(workmode));
	        }

	        if (StringUtils.isNotBlank(joborinternship)) {
	        	listcandidate.removeIf(candidate -> !candidate.getJoborinternship().equalsIgnoreCase(joborinternship));
	        }

	        return listcandidate;
	    }
	
	    
	public List<Job> getJobByCriteria(Job job) {
		return jobRepository.findJobByIgnoreCase(job.getPosition(),job.getCountry(), job.getState(), job.getCity(), job.getWork_mode(),
				job.getNoworkexperience(), job.getDescription(), job.getPosition_type());
	}
	
	public Job getJobById(int position_id) {
		Optional<Job> j = jobRepository.findById(position_id);

		if (j.isPresent()) {
			return j.get();
		}
		return null;
	}
	
	
	
			 	//end
				//JoblistFilters and CandidateListfilters Code (Rugwed patharkar , Chinmay wagh)

}
 