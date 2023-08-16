package com.placementportal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

	public Page<Candidate> searchEligibleCandidates(int positionid, int minKeywordLength, String search,
			Pageable pageable) {
		Job job = jobRepository.findById(positionid).orElse(null);
		if (job == null) {
			return Page.empty();
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

		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), eligibleCandidates.size());
		return new PageImpl<>(eligibleCandidates.subList(start, end), pageable, eligibleCandidates.size());
	}

	public List<Candidate> searchCandidates(List<Candidate> candidates, String search) {
		List<Candidate> listcandidate = candidateRepository.findCandidatesByKeyword("%" + search.toLowerCase() + "%");
		return listcandidate;
	}

	public List<Candidate> getCandidatesByIds(List<Long> candidateids) {
		return candidateRepository.findAllById(candidateids);
	}

	// candidatelistfilters
	public Page<Candidate> findEligibleCandidates(int positionId, int minKeywordLength, Pageable pageable) {
		Job job = jobRepository.findById(positionId).orElse(null);
		if (job == null) {
			return Page.empty(); // Return an empty page if the job is not found
		}

		String[] jobKeywords = job.getDescription().split(" ");
		List<Candidate> eligibleCandidates = new ArrayList<>();

		for (String keyword : jobKeywords) {
			if (keyword.length() > minKeywordLength) {
				List<Candidate> candidates = findCandidatesByPrimarySkills(keyword);
				eligibleCandidates.addAll(candidates);
			}
		}

		// Convert the list of candidates to a Page object
		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), eligibleCandidates.size());
		return new PageImpl<>(eligibleCandidates.subList(start, end), pageable, eligibleCandidates.size());
	}

	public Page<Candidate> findEligibleCandidates(int positionid, int minKeywordLength, String noofyearsworkex,
			String workmode, String joborinternship, Pageable pageable) {
		Job job = jobRepository.findById(positionid).orElse(null);
		if (job == null) {
			return Page.empty();
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

		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), eligibleCandidates.size());
		return new PageImpl<>(eligibleCandidates.subList(start, end), pageable, eligibleCandidates.size());

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
