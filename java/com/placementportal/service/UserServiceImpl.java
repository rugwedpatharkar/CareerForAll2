package com.placementportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.placementportal.model.User;
import com.placementportal.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository urepo;
	
	public List<User> getAllCompanyList() {
		return urepo.findAll();
	}
	public List<User> getAllInstituteList() {
		return urepo.findAll();
	}
	public UserServiceImpl(UserRepository urepo) {
		this.urepo = urepo;
	}

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
