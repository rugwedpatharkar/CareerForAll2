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
		 .authorizeHttpRequests((requests) -> requests
		 .requestMatchers("/admin/adminhome.html").hasRole("ADMIN")
		 .requestMatchers("/user/userhome.html").hasRole("USER")
		 .requestMatchers("/placementofficer/pohome.html").hasRole("PO")
		 .requestMatchers("/profile.html").hasRole("HR")
		 .requestMatchers("/jobopening").hasRole("HR")
		 .requestMatchers("/**").permitAll()
		 .anyRequest().authenticated())
		 .formLogin((form) -> form
				 .loginPage("/login")
				 .loginProcessingUrl("/login")
				 .successHandler(customAuthenticationSuccessHandler)
				 .permitAll()
		)
		 .logout((logout) -> logout.permitAll())
         .exceptionHandling().accessDeniedPage("/access-denied");
		 
		 
		 
		 http.authenticationProvider(daoAuthenticationProvider());
		 
	        return http.build();
	    }

}
