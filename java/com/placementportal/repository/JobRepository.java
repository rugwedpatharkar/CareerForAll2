package com.placementportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.placementportal.model.Job;



public interface JobRepository extends JpaRepository<Job, Integer> {
	@Query("SELECT j FROM Job j WHERE  j.country = :country "+ 
"AND  j.state = :state "+ 
"AND  j.city = :city " + 
"OR  j.noworkexperience = :noworkexperience "+ 
"OR  j.workmode = :workmode " +
"OR  j.positiontype = :positiontype ")
 
	List<Job> findJobByIgnoreCase(
			@Param("country") String country,
			@Param("state") String state,
			@Param("city") String city, 
			@Param("workmode") String workmode,
			@Param("noworkexperience") int noworkexperience,
			@Param("positiontype") String positiontype);
	

	
	 @Query("SELECT j FROM Job j WHERE LOWER(CONCAT(j.position, j.description, j.designation)) LIKE %:keyword%")
	 public List<Job> findJobByIgnoreCase(@Param("keyword") String keyword);
}
