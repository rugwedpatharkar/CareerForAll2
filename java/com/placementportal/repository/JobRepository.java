package com.placementportal.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.placementportal.model.Job;

public interface JobRepository extends JpaRepository<Job, Integer> {

	@Query("SELECT j FROM Job j WHERE " + "(:companyid is null or j.company.companyid = :companyid) "
			+ "AND (:postedOn is null or j.postedon >= :postedOn) " + "AND (:country is null or j.country = :country) "
			+ "AND (:state is null or j.state = :state) " + "AND (:city is null or j.city = :city) "
			+ "AND (:workmode is null or j.workmode = :workmode) "
			+ "AND (:noworkexperience = -1 or j.noworkexperience = :noworkexperience) "
			+ "AND (:positiontype is null or j.positiontype = :positiontype)")
	Page<Job> findJobByCompanyidAndIgnoreCase(@Param("companyid") Long companyid, @Param("postedOn") Date postedOn,
			@Param("country") String country, @Param("state") String state, @Param("city") String city,
			@Param("workmode") String workmode, @Param("noworkexperience") String noworkexperience,
			@Param("positiontype") String positiontype, Pageable pageable);

//	@Query("SELECT j FROM Job j WHERE j.company.companyid = :companyid " + "AND j.country = :country "
//			+ "AND j.state = :state " + "AND j.city = :city " + "OR j.noworkexperience = :noworkexperience "
//			+ "OR j.workmode = :workmode " + "OR j.positiontype = :positiontype")
//	Page<Job> findJobByCompanyidAndIgnoreCase(@Param("companyid") Long companyid, @Param("country") String country,
//			@Param("state") String state, @Param("city") String city, @Param("workmode") String workmode,
//			@Param("noworkexperience") String noworkexperience, @Param("positiontype") String positiontype,
//			Pageable pageable);

	@Query("SELECT j FROM Job j WHERE j.company.companyid = :companyid "
			+ "AND LOWER(CONCAT(j.position, j.description, j.designation)) LIKE %:keyword%")
	Page<Job> findJobByCompanyidAndIgnoreCase(@Param("companyid") Long companyid, @Param("keyword") String keyword,
			Pageable pageable);

	@Query("SELECT j FROM Job j WHERE j.company.companyid = :companyid")
	Page<Job> findJobsByCompanyid(@Param("companyid") Long companyid, Pageable pageable);

	List<Job> findByCompanyCompanyid(Long companyid);

}
