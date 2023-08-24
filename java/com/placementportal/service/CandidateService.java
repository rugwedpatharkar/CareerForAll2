package com.placementportal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placementportal.model.Candidate;
import com.placementportal.model.Job;
import com.placementportal.model.JobCandidate;
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

	public void saveCandidate(Candidate candidate, String educationalDetailsJson) {
		candidate.setEducationalDetailsJson(educationalDetailsJson);
		this.candidateRepository.save(candidate);
	}

	public Candidate getCandidateById(Long id) {
		return candidateRepository.findById(id).orElse(null);
	}

	public void deleteCandidateById(Long id) {
		this.candidateRepository.deleteById(id);
	}

	// ******************* JoblistFilters and CandidateListfilters and
	// mappedcandidatelist Code (Rugwed patharkar , Chinmay wagh)
	// *********************
//start
	public Optional<Candidate> getFileById(Long canidate_id) {
		return candidateRepository.findById(canidate_id);

	}

	public List<Candidate> getCandidatesByJobCandidates(List<JobCandidate> jobCandidates) {
		List<Long> candidateIds = jobCandidates.stream().map(JobCandidate::getCandidate).map(Candidate::getCandidateid)
				.collect(Collectors.toList());
		return candidateRepository.findAllById(candidateIds);
	}

	public List<Candidate> findCandidatesByPrimarySkills(String keyword) {
		return candidateRepository.findCandidatesByPrimarySkills(keyword);
	}

	public List<Candidate> getCandidatesByIds(List<Long> candidateids) {
		return candidateRepository.findAllById(candidateids);
	}

	public List<Candidate> findEligibleCandidates(int positionId, int minKeywordLength) {
		Job job = jobRepository.findById(positionId).orElse(null);

		String[] jobKeywords = job.getDescription().split(" ");
		List<Candidate> eligibleCandidates = new ArrayList<>();

		for (String keyword : jobKeywords) {
			if (keyword.length() > minKeywordLength) {
				List<Candidate> candidates = findCandidatesByPrimarySkills(keyword);
				eligibleCandidates.addAll(candidates);
			}
		}
		return eligibleCandidates;

	}

	public List<Candidate> applyFiltersAndSearch(List<Candidate> candidates, String noofyearsworkex, String workmode,
			String joborinternship, String search) {
		List<Candidate> filteredCandidates = new ArrayList<>(candidates);

		// Apply filters on filteredCandidates using custom queries
		List<Candidate> filteredByFilters = candidateRepository.findEligibleCandidatesByFilters(noofyearsworkex,
				workmode, joborinternship);

		filteredCandidates.retainAll(filteredByFilters); // Keep only candidates that satisfy filters

		// Apply search on the remaining filtered candidates
		if (search != null && !search.isEmpty()) {
			List<Candidate> searchedCandidates = candidateRepository.findCandidatesByKeyword(search.toLowerCase());
			filteredCandidates.retainAll(searchedCandidates); // Keep only candidates that match search
		}

		return filteredCandidates;
	}

	public Candidate getCandidateByCandidateid(Long candidateid) {
		return candidateRepository.findById(candidateid).orElse(null);
	}

	// end

	// ******************* JoblistFilters and CandidateListfilters and
	// mappedcandidatelist Code (Rugwed patharkar , Chinmay wagh)
	// *********************

}
