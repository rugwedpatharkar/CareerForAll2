package com.placementportal.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

	// JoblistFilters and CandidateListfilters Code (Rugwed patharkar , Chinmay
	// wagh)
	// start

	public List<Job> findJobList() {

		return jobRepository.findAll();
	}

	public Page<Job> findJobList(Pageable pageable) {
		return jobRepository.findAll(pageable);
	}

	public List<Job> jobsearch(String keyword) {
		if (keyword != null) {
			return jobRepository.findJobByIgnoreCase(keyword);
		}
		return jobRepository.findAll();
	}

	public Page<Candidate> findEligibleCandidates(int positionId, int minKeywordLength, Pageable pageable) {
		Job job = jobRepository.findById(positionId).orElse(null);
		if (job == null) {
			return Page.empty(); // Return an empty page if the job is not found
		}

		String[] jobKeywords = job.getDescription().split(" ");
		List<Candidate> eligibleCandidates = new ArrayList<>();

		for (String keyword : jobKeywords) {
			if (keyword.length() > minKeywordLength) {
				List<Candidate> candidates = candidateService.findCandidatesByPrimarySkills(keyword);
				eligibleCandidates.addAll(candidates);
			}
		}

		// Convert the list of candidates to a Page object
		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), eligibleCandidates.size());
		return new PageImpl<>(eligibleCandidates.subList(start, end), pageable, eligibleCandidates.size());
	}

	public Page<Candidate> findEligibleCandidates(int positionId, int minKeywordLength, String noofyearsworkex,
			String workmode, String joborinternship, Pageable pageable) {
		Job job = jobRepository.findById(positionId).orElse(null);
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

	private List<Candidate> filterCandidates(List<Candidate> candidates, String noofyearsworkex, String workmode,
			String joborinternship) {
		List<Candidate> listcandidate = new ArrayList<>(candidates);
		if (StringUtils.isNotBlank(noofyearsworkex)) {
			listcandidate.removeIf(candidate -> !candidate.getNoofyearsworkex().equals(noofyearsworkex));
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

		return jobRepository.findJobByIgnoreCase(job.getCountry(), job.getState(), job.getCity(), job.getWorkmode(),
				job.getNoworkexperience(), job.getPositiontype());
	}

	public Job getJobById(int positionid) {
		Optional<Job> j = jobRepository.findById(positionid);

		if (j.isPresent()) {
			return j.get();
		}
		return null;
	}

	public List<Job> getJobsPostedPast24Hours() {
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		Date past24Hours = calendar.getTime();

		return jobRepository.findAllByPostedonGreaterThanEqual(past24Hours);
	}

	public List<Job> getJobsPostedPastWeek() {
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.WEEK_OF_YEAR, -1);
		Date pastWeek = calendar.getTime();

		return jobRepository.findAllByPostedonGreaterThanEqual(pastWeek);
	}

	public List<Job> getJobsPostedPastMonth() {
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.MONTH, -1);
		Date pastMonth = calendar.getTime();

		return jobRepository.findAllByPostedonGreaterThanEqual(pastMonth);
	}

	public Job getJobbyId(int positionid) {
		return jobRepository.findById(positionid)
				.orElseThrow(() -> new IllegalArgumentException("Job not found with ID: " + positionid));
	}
	// end
	// JoblistFilters and CandidateListfilters Code (Rugwed patharkar , Chinmay
	// wagh)

}
