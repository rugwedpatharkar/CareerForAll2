package com.placementportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placementportal.model.JobCandidate;

public interface JobCandidateRepository extends JpaRepository<JobCandidate, Long> {

}