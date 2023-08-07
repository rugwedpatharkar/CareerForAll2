package com.placementportal.service;

import com.placementportal.model.User;

public interface UserService {

	User findUserByEmail(String email);
	
	//User loadUserByEmail(String email);

	public boolean checkEmail(String email);

}
