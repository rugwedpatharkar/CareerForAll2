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

@Service
public class CandidateService {

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private JobRepository jobRepository;

	public List<Candidate> getAllCandidates() {
		return candidateRepository.findAll();
	}

	public void saveCandidate(Candidate candidate) {
		this.candidateRepository.save(candidate);
	}

	// JoblistFilters and CandidateListfilters Code (Rugwed patharkar , Chinmay
	// wagh)
//start
	public Optional<Candidate> getFileById(Long canidate_id) {
		return candidateRepository.findById(canidate_id);

	}

	public List<Candidate> findCandidatesByPrimarySkills(String keyword) {
		return candidateRepository.findCandidatesByPrimarySkills(keyword);
	}

	public List<Candidate> searchEligibleCandidates(int positionid, int minKeywordLength, String search) {
		Job job = jobRepository.findById(positionid).orElse(null);
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

		eligibleCandidates = searchCandidates(eligibleCandidates, search);

		return eligibleCandidates;
	}

	public List<Candidate> searchCandidates(List<Candidate> candidates, String search) {
		List<Candidate> listcandidate = candidateRepository.findCandidatesByKeyword("%" + search.toLowerCase() + "%");
		return listcandidate;
	}
	 public Candidate getCandidateById(Long candidateid) {
	        return candidateRepository.findById(candidateid)
	                .orElseThrow(() -> new IllegalArgumentException("Candidate not found with ID: " + candidateid));
	    }

	    public List<Candidate> getCandidatesByIds(List<Long> candidateids) {
	        return candidateRepository.findAllById(candidateids);
	    }
	// end
	// JoblistFilters and CandidateListfilters Code (Rugwed patharkar , Chinmay
	// wagh)

}
