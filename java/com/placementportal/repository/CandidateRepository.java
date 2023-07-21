package com.placementportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.placementportal.model.Candidate;



public interface CandidateRepository extends JpaRepository<Candidate,Long> {
	@Query("SELECT c FROM Candidate c WHERE  c.candidate_name = :candidate_name "
			+ "AND  c.candidate_city = :candidate_city "
			+ "OR  c.noofyearsworkex= :noofyearsworkex "
			+ "OR  c.workmode = :workmode "
			+ "OR  c.joborinternship = :joborinternship ")

	
	List<Candidate> findCandidateByIgnoreCase(@Param("candidate_name") String candidate_name,
			 @Param("candidate_city") String candidate_city,
			@Param("noofyearsworkex") String noofyearsworkex,
			@Param("workmode") String workmode,
			@Param("joborinternship") String joborinternship);
	
	
	 @Query("SELECT c FROM Candidate c WHERE LOWER(CONCAT(c.candidate_name, c.primary_skills, c.secondary_skills)) LIKE %:search%")
	    List<Candidate> findCandidatesByKeyword(@Param("search") String search);

	
	  @Query("SELECT c FROM Candidate c WHERE c.primary_skills LIKE %:keyword%")
	    List<Candidate> findCandidatesByPrimarySkills(@Param("keyword") String keyword);
	  

	   

}
