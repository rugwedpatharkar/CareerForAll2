package com.placementportal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class MyConfig {

	@Autowired
	private AuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public BCryptPasswordEncoder getPassword() {
		return new BCryptPasswordEncoder();
	}

	// diffrent type provider in spring perform into database

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
		// set userdetailservice object
		dao.setUserDetailsService(getUserDetailsService());
		dao.setPasswordEncoder(getPassword());
		return dao;
	}

	//configure methods
	
	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        
		 http.csrf().disable()
		 .authorizeHttpRequests()
		 .requestMatchers("/adminhome.html").hasRole("ADMIN")
		 .requestMatchers("/userhome.html").hasRole("USER")         
		 .requestMatchers("/profile.html").hasRole("HR") 
		 .requestMatchers("/startup_onboarding.html").hasRole("HR")
		 .requestMatchers("/CompanyList.html").hasRole("HR")
		 .requestMatchers("/editCompany.html").hasRole("HR")
		 .requestMatchers("/jobopening.html").hasRole("HR")
		 .requestMatchers("/joblistfilters.html").hasRole("HR")
		 .requestMatchers("/pohome.html").hasRole("PO") 
		 .requestMatchers("/institute_onboarding.html").hasRole("PO")
		 .requestMatchers("/candidate_registration.html").hasRole("PO")
		 .requestMatchers("/CandidateList.html").hasRole("PO")
		 .requestMatchers("/editCandidate.html").hasRole("PO")
		 .requestMatchers("/**").permitAll()
		 .and()
         .formLogin()
         .loginPage("/login")
         .successHandler(customAuthenticationSuccessHandler) // Redirect all users to a common profile page after login
         .and()
         .logout()
         .logoutUrl("/logout")
         .logoutSuccessUrl("/login")
         .and()
         .exceptionHandling().accessDeniedPage("/access-denied");
		 
		 http.authenticationProvider(daoAuthenticationProvider());
		 
	        return http.build();
	    }

}
