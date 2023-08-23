package com.placementportal.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.placementportal.model.Company;
import com.placementportal.model.Job;
import com.placementportal.model.JobCandidate;

public interface JobCandidateRepository extends JpaRepository<JobCandidate, Long> {

	List<JobCandidate> findByCompanyCompanyidAndJobPositionid(Long companyid, int positionid);

	List<JobCandidate> findByCompanyAndJob(Company company, Job job);

	@Modifying
	@Query("DELETE FROM JobCandidate j WHERE j.jobcandidateid IN :jobcandidateids")
	void deleteByJobcandidateidIn(@Param("jobcandidateids") List<Long> jobcandidateids);

}