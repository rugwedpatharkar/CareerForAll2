package com.placementportal.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placementportal.model.Institute;
import com.placementportal.model.JobCandidate;
import com.placementportal.repository.InstituteRepository;

@Service
public class InstituteService {

	@Autowired
	private InstituteRepository instituteRepository;

	public List<Institute> getAllInstitute() {

		return instituteRepository.findAll();
	}

	public void saveInstitute(Institute institute) {

		instituteRepository.save(institute);
	}
	
	
	// *******************  JoblistFilters and CandidateListfilters and mappedcandidatelist Code (Rugwed patharkar , Chinmay wagh) *********************
//start
	public List<Institute> getInstitutesByJobCandidates(List<JobCandidate> jobCandidates) {
		List<Long> instituteIds = jobCandidates.stream().map(JobCandidate::getInstitute).map(Institute::getInstituteid)
				.collect(Collectors.toList());
		return instituteRepository.findAllById(instituteIds);
	}

	public Institute getInstituteById(Long instituteid) {
		return instituteRepository.findById(instituteid).orElse(null);
	}

	public List<Institute> getInstitutesByIds(List<Long> instituteids) {
		return instituteRepository.findAllById(instituteids);
	}
	//end
	// *******************  JoblistFilters and CandidateListfilters and mappedcandidatelist Code (Rugwed patharkar , Chinmay wagh) *********************

}
