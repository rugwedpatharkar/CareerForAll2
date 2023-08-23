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

//	public void saveCandidate(Candidate candidate) {
//		this.candidateRepository.save(candidate);
//	}

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

	public List<Candidate> searchEligibleCandidates(int positionid, int minKeywordLength, String search) {
		Job job = jobRepository.findById(positionid).orElse(null);

		String[] jobKeywords = job.getDescription().split(" ");
		List<Candidate> eligibleCandidates = new ArrayList<>();

		for (String keyword : jobKeywords) {
			if (keyword.length() > minKeywordLength) {
				List<Candidate> candidates = candidateRepository.findCandidatesByPrimarySkills(keyword);
				eligibleCandidates.addAll(candidates);
			}
		}

		return eligibleCandidates = searchCandidates(eligibleCandidates, search);

	}

	public List<Candidate> searchCandidates(List<Candidate> candidates, String search) {
		List<Candidate> listcandidate = candidateRepository.findCandidatesByKeyword("%" + search.toLowerCase() + "%");
		return listcandidate;
	}

	public List<Candidate> getCandidatesByIds(List<Long> candidateids) {
		return candidateRepository.findAllById(candidateids);
	}

	// candidatelistfilters
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

	public List<Candidate> findEligibleCandidates(int positionid, int minKeywordLength, String noofyearsworkex,
			String workmode, String joborinternship) {
		Job job = jobRepository.findById(positionid).orElse(null);

		String[] jobKeywords = job.getDescription().split(" ");
		List<Candidate> eligibleCandidates = new ArrayList<>();

		for (String keyword : jobKeywords) {
			if (keyword.length() > minKeywordLength) {
				List<Candidate> candidates = candidateRepository.findCandidatesByPrimarySkills(keyword);
				eligibleCandidates.addAll(candidates);
			}
		}

		return eligibleCandidates = filterCandidates(eligibleCandidates, noofyearsworkex, workmode, joborinternship);

	}

//	private List<Candidate> filterCandidates(List<Candidate> candidates, String noofyearsworkex, String workmode,
//			String joborinternship) {
//		List<Candidate> listcandidate = new ArrayList<>(candidates);
//		if (StringUtils.isNotBlank(noofyearsworkex)) {
//			listcandidate.removeIf(candidate -> !candidate.getNoofyearsworkex().equals(noofyearsworkex));
//		}
//
//		if (StringUtils.isNotBlank(workmode)) {
//			listcandidate.removeIf(candidate -> !candidate.getWorkmode().equalsIgnoreCase(workmode));
//		}
//
//		if (StringUtils.isNotBlank(joborinternship)) {
//			listcandidate.removeIf(candidate -> !candidate.getJoborinternship().equalsIgnoreCase(joborinternship));
//		}
//
//		return candidates;
//	}

	private List<Candidate> filterCandidates(List<Candidate> candidates, String noofyearsworkex, String workmode,
			String joborinternship) {
		return candidateRepository.findCandidatesByFilters(noofyearsworkex, workmode, joborinternship);
	}

	public Candidate getCandidateByCandidateid(Long candidateid) {
		return candidateRepository.findById(candidateid).orElse(null);
	}

	// end

	// ******************* JoblistFilters and CandidateListfilters and
	// mappedcandidatelist Code (Rugwed patharkar , Chinmay wagh)
	// *********************

}
