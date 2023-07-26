package com.placementportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class MyConfig{

	@Bean
	public UserDetailsService getUserDetailsService()
	{
		return new CustomUserDetailsService();
	}
	
	@Bean
	public BCryptPasswordEncoder getPassword()
	{
		 return new BCryptPasswordEncoder();
	}
	
	// diffrent type provider in spring perform into database
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider()
	{
		DaoAuthenticationProvider dao=new DaoAuthenticationProvider();
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
		 .requestMatchers("/admin/**").hasRole("ADMIN")
		 .requestMatchers("/user/userhome.html").hasRole("USER")
		 .requestMatchers("/placementofficer/**").hasRole("PO")
		 .requestMatchers("/hr/**").hasRole("HR")
		 .requestMatchers("/**").permitAll()
		 .anyRequest().authenticated()
		 )
		 .formLogin((form) -> form
				 .loginPage("/login")
				 .loginProcessingUrl("/login")
				 .defaultSuccessUrl("/userhome")
				 .permitAll()
		)
		 .logout((logout) -> logout.permitAll())
         .exceptionHandling().accessDeniedPage("/access-denied");
		 
		 http.authenticationProvider(daoAuthenticationProvider());
		 
	        return http.build();
	    }

}
