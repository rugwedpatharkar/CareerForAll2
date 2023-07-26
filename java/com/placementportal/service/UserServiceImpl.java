package com.placementportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placementportal.model.User;
import com.placementportal.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository urepo;

	@Override
	public User findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return urepo.findByEmail(email);
	}

	@Override
	public boolean checkEmail(String email) {

		return urepo.existsByEmail(email);
	}

//	public User login(String email, String password) {
//		User user=urepo.findByEmailAndPassword(email,password);
//		return user;
//
//	}

}
