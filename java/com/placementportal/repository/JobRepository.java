package com.placementportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.placementportal.model.Job;



public interface JobRepository extends JpaRepository<Job, Integer> {
	@Query("SELECT j FROM Job j WHERE  j.position = :position " + 
"OR  j.country = :country "+ 
"AND  j.state = :state "+ 
"AND  j.city = :city " + 
"OR  j.noworkexperience = :noworkexperience "+ 
"OR  j.work_mode = :work_mode " +
"OR  j.description = :description " +
"OR  j.position_type = :position_type ")
 
	List<Job> findJobByIgnoreCase(@Param("position") String position, 
			@Param("country") String country,
			@Param("state") String state,
			@Param("city") String city, 
			@Param("work_mode") String work_mode,
			@Param("noworkexperience") int noworkexperience,
			@Param("description") String description,
			@Param("position_type") String position_type);
	
	@Query("SELECT j FROM Job j WHERE (j.position) LIKE %?1%")
	public List<Job> findJobByIgnoreCase(String keyword);
}
