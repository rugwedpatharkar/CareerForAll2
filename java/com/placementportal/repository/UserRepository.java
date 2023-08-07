package com.placementportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.placementportal.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public boolean existsByEmail(String email);

	public User findByEmail(String email);

	// User findByName(String name);

//	@Query("select email from users where users.email=:email")
//	public User getUserByEmail(@Param("email") String email);

	// for login
	// User findByEmailAndPassword(String email, String password);

}
