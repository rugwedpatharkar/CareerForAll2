package com.placementportal.controller;
 
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.placementportal.model.Candidate;
import com.placementportal.model.Company;
import com.placementportal.model.Institute;
import com.placementportal.model.Job;
import com.placementportal.model.User;
import com.placementportal.repository.UserRepository;
import com.placementportal.service.CandidateService;
import com.placementportal.service.CompanyService;
import com.placementportal.service.InstituteService;
import com.placementportal.service.JobCandidateService;
import com.placementportal.service.JobService;
import com.placementportal.service.UserServiceImpl;

import jakarta.servlet.http.HttpSession;
  
@Controller
public class MainController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	private BCryptPasswordEncoder bp;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private InstituteService institueService; 	

	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private JobCandidateService jobCandidateService;
	  
	@GetMapping("/")
	public String startupjobDesc(Model model) {
		Job job = new Job();
		model.addAttribute("job", job);
		return "index";
	}
	
	@PostMapping("/saveJob")
	public String saveJob(@ModelAttribute("job") Job job) {
		jobService.saveJob(job);
		return ("redirect:/jobopening");
	}
	
	
	
	@GetMapping("/index")
	public ModelAndView homepage() {
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}
	 
	@GetMapping("/jobopening")
	public ModelAndView jobopening() {
		ModelAndView mav = new ModelAndView("jobopening");
		return mav;
	}
			
	//Company Controllers
	@PostMapping("/saveCompany")
	public String saveCompany(@ModelAttribute("company") Company company) {
		companyService.saveCompany(company);
		return("redirect:/");
	}
	
	@GetMapping("/CompanyList")
	public String showCompany(Model model) {
		model.addAttribute("listCompanies",companyService.getAllCompanies());
		return "CompanyList";
	}
	
	@GetMapping("/startup_onboarding")
	public String startup_onboarding(Model model) {
		Company company = new Company();
		model.addAttribute("company", company);
		return("startup_onboarding"); 
	}
	
	//Institute Controller
	@GetMapping("/institute_onboarding")
	public String institute_onboarding(Model model) {
		Institute institute = new Institute();
		model.addAttribute("institute", institute);
		return("institute_onboarding"); 
	}

	@PostMapping("/saveInstitute")
	public String saveInstitute(@ModelAttribute("institute") Institute institute) {
		institueService.saveInstitute(institute);
	    return "redirect:/";
	}
	
	//Candidate COntroller	
	@GetMapping("/candidate_registration")
	public String candidateregistration(Model model) {
		Candidate candidate = new Candidate();
		model.addAttribute("candidate", candidate);
		return("candidate_registration"); 
	}
	
	@PostMapping("/saveCandidate")
	public String saveCandidate(@ModelAttribute("candidate") Candidate candidate) {
	    candidateService.saveCandidate(candidate);
	    return "redirect:/candidate_registration";
	}
	
	// Login Controller
	
//	@GetMapping("/login")
//	public ModelAndView login() {
//		ModelAndView mav = new ModelAndView("login");
//		return mav;
//	}
	
	@GetMapping("/login")
	public String loginHome()
	{
		return "login";
	}
	
	// Registration Controller
	
//	@GetMapping("/register")
//	public ModelAndView register() {
//		ModelAndView mav = new ModelAndView("register");
//		return mav;
//	}
	
	@GetMapping("/register")
	public String home()
	{
		return "register";
	}
	
	
	@PostMapping("/register")
	public String create(@ModelAttribute User user, HttpSession session)
	{
		
		boolean u=userServiceImpl.checkEmail(user.getEmail());
		if(u)
		{
			System.out.println("Email id already exist");
		}else {
			System.out.println(user);
			//password encryption 
			user.setPassword(bp.encode(user.getPassword()));
			//user.setRole(user.getRole());
			
			session.setAttribute("msg", "Registration  successfully!");
			userRepository.save(user);
		}
		
		return "redirect:/register?success";
	}
	
	
	
	
	//JoblistFilters and CandidateListfilters Code (Rugwed patharkar , Chinmay wagh)
	//start
	//filters for jobs on joblistfilters
		@GetMapping("/jobfilter")
		public String getJobByCriteria(@ModelAttribute("job") Job job, Model model) {
			List<Job> listJob = jobService.getJobByCriteria(job);
			model.addAttribute("listJob", listJob);
			System.out.println(listJob);
			return "joblistfilters";
		}
		
		
	//Job Search on joblistfilters
		@GetMapping("/joblistfilters")
		public String joblistfilters(Model model,@Param("keyword") String keyword) {
			List<Job> listJob = jobService.jobsearch(keyword);
			model.addAttribute("listJob", listJob);
			model.addAttribute("keyword", keyword);
			return "joblistfilters";
		}
		//show eligible candidates on candidateslistfilters
		@GetMapping("/joblistfilters/{position_id}/{minKeywordLength}")
		public String getJobDetails(@PathVariable int position_id,@PathVariable int minKeywordLength, Model model) {
			Job position = jobService.getJobById(position_id);
			model.addAttribute("position", position);
			 List<Candidate> eligibleCandidates = jobService.findEligibleCandidates(position_id, minKeywordLength);
		        model.addAttribute("listcandidate", eligibleCandidates);
			return "candidatelistfilters";
		}
	//CV download of candidates on candidatelistfilters
		@GetMapping("/cvdownload/{candidate_id}")
		public ResponseEntity<Resource> downloadFile1(@PathVariable long candidate_id) throws IOException {
	        Optional<Candidate> fileEntityOptional = candidateService.getFileById(candidate_id);
	        if (fileEntityOptional.isPresent()) {
	        	Candidate candidate = fileEntityOptional.get();
	            ByteArrayResource resource = new ByteArrayResource(candidate.getCv_upload());

	            return ResponseEntity.ok()
	                    .header(HttpHeaders.CONTENT_DISPOSITION)
	                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
	                    .contentLength(candidate.getCv_upload().length)
	                    .body(resource);
	        }
	        return ResponseEntity.notFound().build();
	    }

	    @GetMapping(value = "/cvdownload/{candidate_id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	    @ResponseBody
	    public byte[] downloadFileAsAttachment(@PathVariable Long candidate_id) throws IOException {
	        Optional<Candidate> fileEntityOptional = candidateService.getFileById(candidate_id);
	        if (fileEntityOptional.isPresent()) {
	            Candidate candidate = fileEntityOptional.get();
	            return candidate.getCv_upload();
	        }
	        return null;
	    }
	    
	    
	    //seaech candidates in list of eligible candidates
	    @GetMapping("/searchcandidates/{position_id}/{minKeywordLength}")
	    public String searchCandidates(@PathVariable int position_id, @PathVariable int minKeywordLength,
	            @RequestParam(required = false) String search,
	                                Model model) {
	        Job position = jobService.getJobById(position_id);
	        model.addAttribute("position", position);

	        List<Candidate> eligibleCandidates = candidateService.searchEligibleCandidates(position_id, minKeywordLength, search);
	        model.addAttribute("listcandidate", eligibleCandidates);

	        return "candidatelistfilters";
	    }
	    
	    
	//filters for candidates on candidatelistfilters
		@GetMapping("/candidatelistfilters/{position_id}/{minKeywordLength}")
	    public String getJobDetails1(@PathVariable int position_id, @PathVariable int minKeywordLength,
	                                @RequestParam(required = false) String noofyearsworkex,
	                                @RequestParam(required = false) String workmode,
	                                @RequestParam(required = false) String joborinternship,
	                                Model model) {
	        Job position = jobService.getJobById(position_id);
	        model.addAttribute("position", position);

	        List<Candidate> eligibleCandidates = jobService.findEligibleCandidates(position_id, minKeywordLength, noofyearsworkex, workmode, joborinternship);
	        model.addAttribute("listcandidate", eligibleCandidates);

	        return "candidatelistfilters";
	    }
		
		//candidates mapping to jobs
		@GetMapping("/candidatemapping/{position_id}/{minKeywordLength}")
		public String mapCandidateToJob(@ModelAttribute("candidate") Candidate candidate,
				@PathVariable int minKeywordLength ,
				Model model,
				@RequestParam("position_id") int position_id,
				@RequestParam("candidate_id") long candidate_id) {
			jobCandidateService.mapCandidateToJob(position_id, candidate_id);
			Job position = jobService.getJobById(position_id);
			model.addAttribute("position", position);
			 List<Candidate> eligibleCandidates = jobService.findEligibleCandidates(position_id, minKeywordLength);
		        model.addAttribute("listcandidate", eligibleCandidates);
			return "candidatelistfilters";
		}
		//end
		//JoblistFilters and CandidateListfilters Code (Rugwed patharkar , Chinmay wagh)

		
	
}

  