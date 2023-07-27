package com.placementportal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.placementportal.model.User;
import com.placementportal.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		// fetching user from database

		User user = userRepository.findByEmail(email);
		if (user != null) {
			return new CustomUserDetails(user);
		}
		throw new UsernameNotFoundException("User not available");

	}

}
