package com.placementportal.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
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
import com.placementportal.model.JobCandidate;
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

	// ---------------------startup onboarding------------------------
	// Company Controllers
	@PostMapping("/saveCompany")
	public String saveCompany(@ModelAttribute("company") Company company) {
		companyService.saveCompany(company);
		return ("redirect:/CompanyList");
	}


	@GetMapping("/startup_onboarding")
	public String startup_onboarding(Model model) {
		Company company = new Company();
		model.addAttribute("company", company);
		return ("startup_onboarding");
	}

	
	@GetMapping("/CompanyList")
	public String showCompany(Model model) {
		model.addAttribute("listCompanies", companyService.getAllCompanies());
		return "CompanyList";
	}

	@GetMapping("/editCompany/{id}")
	public String editCompanyForm(@PathVariable("id") Long id, Model model) {
		Company company = companyService.getCompanyById(id);
		model.addAttribute("company", company);
		return "editCompany";
	}

	@PostMapping("/updateCompany")
	public String updateCompany(@ModelAttribute("company") Company company) {
		companyService.saveCompany(company);
		return "redirect:/CompanyList";
	}

	@GetMapping("/deleteCompany/{id}")
	public String deleteCompany(@PathVariable(value = "id") Long id) {
		// call delete company method
		this.companyService.deleteCompanyById(id);
		return "redirect:/CompanyList";
	}

//	@Autowired
//	private CompanyService companyService;

	// List of companies
//	@GetMapping("/CompanyList")
//	public String showCompany(Model model) {
//		model.addAttribute("listCompanies",companyService.getAllCompanies());
//		return "CompanyList";
//	}
//	@GetMapping("/")
//	public String startupOnboarding(Model model) {
//		Company company = new Company();
//		model.addAttribute("company", company);
//		return("startup_onboarding");
//	}
//	@PostMapping("/saveCompany")
//	public String saveCompany(@ModelAttribute("company") Company company) {
//		companyService.saveCompany(company);
//		return("redirect:/");
//	}
	// ---------------------end of startup onboarding------------------------
	// Institute Controller
	@GetMapping("/institute_onboarding")
	public String institute_onboarding(Model model) {
		Institute institute = new Institute();
		model.addAttribute("institute", institute);
		return ("institute_onboarding");
	}

	@PostMapping("/saveInstitute")
	public String saveInstitute(@ModelAttribute("institute") Institute institute) {
		institueService.saveInstitute(institute);
		return "redirect:/";
	}

	// Candidate COntroller
	@GetMapping("/candidate_registration")
	public String candidateregistration(Model model) {
		Candidate candidate = new Candidate();
		model.addAttribute("candidate", candidate);
		return ("candidate_registration");
	}

	@PostMapping("/saveCandidate")
	public String saveCandidate(@ModelAttribute("candidate") Candidate candidate) {
		candidateService.saveCandidate(candidate);
		return "redirect:/candidate_registration";
	}
	
	@GetMapping("/CandidateList")
	public String showCandidate(Model model) {
		model.addAttribute("listCandidates", candidateService.getAllCandidates());
		return "CandidateList";
	}

	@GetMapping("/editCandiadte/{id}")
	public String editCandidateForm(@PathVariable("id") Long id, Model model) {
		Candidate candidate = candidateService.getCandidateById(id);
		model.addAttribute("candidate",candidate);
		return "editCandidate";
	}

	@PostMapping("/updateCandidate")
	public String updateCandiadte(@ModelAttribute("candidate") Candidate candidate) {
		candidateService.saveCandidate(candidate);
		return "redirect:/CandidateList";
	}

	@GetMapping("/deleteCandidate/{id}")
	public String deleteCandidate(@PathVariable(value = "id") Long id) {
		this.candidateService.deleteCandidateById(id);
		return "redirect:/CandidateList";
	}

	
	
	

	// ************************ User Login And Registration  **************************

	@GetMapping("/login")
		public String loginHome(Model model)
	{
				return "login";
	}

	@GetMapping("/userhome")
	public String userHome()
	{
		return "userhome"; 
	}
	@GetMapping("/adminhome")
	public String adminhome()
	{
		return "adminhome"; 
	}
	@GetMapping("/profile")
	public String hrhome()
	{
		return "profile"; 
	}
	@GetMapping("/pohome")
	public String pohome()
	{
		return "pohome"; 
	}
	
	
	// Registration Controller (Prasad)
	@GetMapping("/register")
	public String home() {
		return "register";
	}

	@PostMapping("/register")
	public String create(@ModelAttribute User user, HttpSession session) {

		boolean u = userServiceImpl.checkEmail(user.getEmail());
		if (u) {
			System.out.println("Email id already exist");
		} else {
			System.out.println(user);
			// password encryption
			user.setPassword(bp.encode(user.getPassword()));
			// user.setRole(user.getRole());

			session.setAttribute("msg", "Registration  successfully!");
			userRepository.save(user);
		}

		return "redirect:/register?success";
	}
	
	// ************************ End of User Login And Registration  **************************

	// JoblistFilters and CandidateListfilters Code (Rugwed patharkar , Chinmay
	// wagh)
	// start
	@GetMapping("/joblist")
	public String getAllJobs(Model model) {
		model.addAttribute("listJob", jobService.findJobList());
		return "joblistfilters";
	}

	// filters for jobs on joblistfilters
	@GetMapping("/joblistfilters")
	public String getJobByCriteria(@RequestParam(value = "timeRange", required = false) String timeRange,
			@RequestParam(value = "keyword", required = false) String keyword, @ModelAttribute("job") Job job,
			Model model) {
		List<Job> listJob;
		if (timeRange != null && !timeRange.isEmpty()) {
			switch (timeRange) {
			case "past24hours":
				listJob = jobService.getJobsPostedPast24Hours();
				break;
			case "pastweek":
				listJob = jobService.getJobsPostedPastWeek();
				break;
			case "pastmonth":
				listJob = jobService.getJobsPostedPastMonth();
				break;
			default:
				listJob = jobService.findJobList();
			}
		} else if (keyword != null && !keyword.isEmpty()) {
			listJob = jobService.jobsearch(keyword);
		}

		else {
			listJob = jobService.getJobByCriteria(job);
		}
		model.addAttribute("listJob", listJob);
		System.out.println(listJob);
		return "joblistfilters";
	}

	// CV download of candidates on candidatelistfilters
	@GetMapping("/cvdownload/{candidateid}")
	public ResponseEntity<Resource> downloadFile1(@PathVariable long candidateid) throws IOException {
		Optional<Candidate> fileEntityOptional = candidateService.getFileById(candidateid);
		if (fileEntityOptional.isPresent()) {
			Candidate candidate = fileEntityOptional.get();
			ByteArrayResource resource = new ByteArrayResource(candidate.getCvupload());

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION)
					.contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(candidate.getCvupload().length)
					.body(resource);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/cvdownload/{candidateid}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public byte[] downloadFileAsAttachment(@PathVariable Long candidateid) throws IOException {
		Optional<Candidate> fileEntityOptional = candidateService.getFileById(candidateid);
		if (fileEntityOptional.isPresent()) {
			Candidate candidate = fileEntityOptional.get();
			return candidate.getCvupload();
		}
		return null;
	}

	// filters for candidates on candidatelistfilters
	@GetMapping("/candidatelistfilters/{positionid}/{minKeywordLength}")
	public String applyFiltersOnCandidates(@PathVariable int positionid, @PathVariable int minKeywordLength,
			@RequestParam(required = false) String noofyearsworkex, @RequestParam(required = false) String workmode,
			@RequestParam(required = false) String joborinternship, @RequestParam(required = false) String search,
			Model model) {
		Job position = jobService.getJobById(positionid);
		model.addAttribute("position", position);

		List<Candidate> eligibleCandidates;
		if (noofyearsworkex != null && !noofyearsworkex.isEmpty() || workmode != null && !workmode.isEmpty()
				|| joborinternship != null && !joborinternship.isEmpty()) {
			eligibleCandidates = jobService.findEligibleCandidates(positionid, minKeywordLength, noofyearsworkex,
					workmode, joborinternship);
		} else {
			eligibleCandidates = candidateService.searchEligibleCandidates(positionid, minKeywordLength, search);
		}
		model.addAttribute("listcandidate", eligibleCandidates);

		return "candidatelistfilters";
	}
 
	@GetMapping("/eligiblecandidates/{positionid}/{minKeywordLength}")
	public String showEligibleCandidates(@PathVariable int positionid, @PathVariable int minKeywordLength,
			Model model) {
		Job position = jobService.getJobById(positionid);
		model.addAttribute("position", position);
		List<Candidate> eligibleCandidates = jobService.findEligibleCandidates(positionid, minKeywordLength);
		model.addAttribute("listcandidate", eligibleCandidates);
		return "candidatelistfilters";
	}

	// candidates mapping to jobs
	 
	@PostMapping("/mapcandidatetojob/{positionid}/{minKeywordLength}")
	    public String mapCandidatestoJob( @PathVariable int minKeywordLength,
				Model model,
				@RequestParam("positionid") int positionid,
	            @RequestParam("candidateids") List<Long> candidateids) {
	        Job job = jobService.getJobbyId(positionid);
	        List<Candidate> selectedCandidates = candidateService.getCandidatesByIds(candidateids);

	        for (Candidate candidate : selectedCandidates) {
	            JobCandidate jobCandidate = new JobCandidate();
	            jobCandidate.setJob(job);
	            jobCandidate.setCandidate(candidate);
	            jobCandidateService.saveJobCandidate(jobCandidate);
	            Job position = jobService.getJobById(positionid);
	    		model.addAttribute("position", position);
	    		List<Candidate> eligibleCandidates = jobService.findEligibleCandidates(positionid, minKeywordLength);
	    		model.addAttribute("listcandidate", eligibleCandidates);
	        }
 
	        return "candidatelistfilters";
	    }
	// end
	// JoblistFilters and CandidateListfilters Code (Rugwed patharkar , Chinmay
	// wagh)

}
