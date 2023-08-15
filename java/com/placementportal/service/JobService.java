package com.placementportal.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.placementportal.model.Candidate;
import com.placementportal.model.Job;
import com.placementportal.model.JobCandidate;
import com.placementportal.repository.CandidateRepository;
import com.placementportal.repository.JobCandidateRepository;
import com.placementportal.repository.JobRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class JobService {

	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private CandidateRepository candidateRepository;
	@Autowired
	private JobCandidateRepository jobCandidateRepository;
	@Autowired
	private CandidateService candidateService;

	public void saveJob(Job job) {
		this.jobRepository.save(job);
	}

	// ******************* JoblistFilters and CandidateListfilters and
	// mappedcandidatelist Code (Rugwed patharkar , Chinmay wagh)
	// *********************

	// start
//joblistfilters
	public List<JobCandidate> getJobCandidatesByCompanyidAndPositionid(Long companyid, int positionid) {
		return jobCandidateRepository.findByCompanyCompanyidAndJobPositionid(companyid, positionid);
	}

	public Job getJobByPositionid(int positionid) {
		return jobRepository.findById(positionid).orElse(null);
	}

	public Page<Job> getJobsPostedPast24Hours(Long companyid, Job job, Pageable pageable) {
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		Date past24Hours = calendar.getTime();

		return jobRepository.findJobByCompanyidAndIgnoreCase(companyid, past24Hours, job.getCountry(), job.getState(), job.getCity(),
				job.getWorkmode(), job.getNoworkexperience(), job.getPositiontype(), pageable);
	}

	public Page<Job> getJobsPostedPastWeek(Long companyid, Job job, Pageable pageable) {
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.WEEK_OF_YEAR, -1);
		Date pastWeek = calendar.getTime();

		return jobRepository.findJobByCompanyidAndIgnoreCase(companyid, pastWeek, job.getCountry(), job.getState(), job.getCity(),
				job.getWorkmode(), job.getNoworkexperience(), job.getPositiontype(), pageable);
	}

	public Page<Job> getJobsPostedPastMonth(Long companyid, Job job, Pageable pageable) {
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.MONTH, -1);
		Date pastMonth = calendar.getTime();

		return jobRepository.findJobByCompanyidAndIgnoreCase(companyid, pastMonth, job.getCountry(), job.getState(), job.getCity(),
				job.getWorkmode(), job.getNoworkexperience(), job.getPositiontype(), pageable);
	}

	public Page<Job> findJobsByCompanyId(Long companyid, Pageable pageable) {
		return jobRepository.findJobsByCompanyid(companyid, pageable);
	}

	public Page<Job> findJobByCompanyIdAndCriteria(Long companyid, Job job, Pageable pageable) {
		
		return jobRepository.findJobByCompanyidAndIgnoreCase(companyid, null, job.getCountry(), job.getState(), job.getCity(),
				job.getWorkmode(), job.getNoworkexperience(), job.getPositiontype(), pageable);
		

	}

	public Page<Job> jobSearchByCompanyId(Long companyid, String keyword, Pageable pageable) {
		if (keyword != null) {
			return jobRepository.findJobByCompanyidAndIgnoreCase(companyid, keyword, pageable);
		}
		return findJobsByCompanyId(companyid, pageable);
	}
//candidatelistfilters
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

        return candidates;
    }
	
	
	public Job getJobbyId(int positionid) {
		return jobRepository.findById(positionid)
				.orElseThrow(() -> new IllegalArgumentException("Job not found with ID: " + positionid));
	}
	// end
	// ******************* JoblistFilters and CandidateListfilters and
	// mappedcandidatelist Code (Rugwed patharkar , Chinmay wagh)
	// *********************

	//Tanmayi  job service
	
		 public List<Job> getAllPositions() {
				return jobRepository.findAll();
			}
		 
		 public void savePosition(Job job) {
			 this.jobRepository.save(job);
		 }
		 
		 
		 public Job getPositionById(int id) {
			 return jobRepository.findById(id).orElse(null);
		 }
		 
		 public void deletePositionById(int id) {
				this.jobRepository.deleteById(id);
		 }
		 
}