package com.placementportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.placementportal.model.Candidate;



public interface CandidateRepository extends JpaRepository<Candidate,Long> {
	@Query("SELECT c FROM Candidate c WHERE  c.candidatename = :candidatename "
			+ "AND  c.candidatecity = :candidatecity "
			+ "OR  c.noofyearsworkex= :noofyearsworkex "
			+ "OR  c.workmode = :workmode "
			+ "OR  c.joborinternship = :joborinternship ")

	
	List<Candidate> findCandidateByIgnoreCase(@Param("candidatename") String candidatename,
			 @Param("candidatecity") String candidatecity,
			@Param("noofyearsworkex") String noofyearsworkex,
			@Param("workmode") String workmode,
			@Param("joborinternship") String joborinternship);
	
	
	 @Query("SELECT c FROM Candidate c WHERE LOWER(CONCAT(c.candidatename, c.primaryskills, c.secondaryskills)) LIKE %:search%")
	    List<Candidate> findCandidatesByKeyword(@Param("search") String search);

	
	  @Query("SELECT c FROM Candidate c WHERE c.primaryskills LIKE %:keyword%")
	    List<Candidate> findCandidatesByPrimarySkills(@Param("keyword") String keyword);
	  

	   

}
