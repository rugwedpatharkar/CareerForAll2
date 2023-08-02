package com.placementportal.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	private InstituteService instituteService;

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
	@PreAuthorize("hasRole('HR')")
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
	@PreAuthorize("hasRole('HR')")
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
		instituteService.saveInstitute(institute);
		return "redirect:/institute_onboarding";
	}

	// Candidate COntroller

	@GetMapping("/candidate_registration")
	public String candidateregistration(Model model) {
		List<Institute> institutes = instituteService.getAllInstitute();
		model.addAttribute("institutes", institutes);
		Candidate candidate = new Candidate();
		model.addAttribute("candidate", candidate);
		return ("candidate_registration");
	}

	@PostMapping("/saveCandidate")
	public String saveCandidate(@ModelAttribute("candidate") Candidate candidate) {
		candidateService.saveCandidate(candidate);
		return "redirect:/CandidateList";
	}

	@GetMapping("/CandidateList")
	public String showCandidate(Model model) {
		model.addAttribute("listCandidates", candidateService.getAllCandidates());
		return "CandidateList";
	}

	@GetMapping("/editCandidate/{id}")
	public String editCandidateForm(@PathVariable("id") Long id, Model model) {
		List<Institute> institutes = instituteService.getAllInstitute();
		model.addAttribute("institutes", institutes);
		Candidate candidate = candidateService.getCandidateById(id);
		model.addAttribute("candidate", candidate);
		return "editCandidate";
	}
	@PostMapping("/updateCandidate")
	public String updateCandidate(@ModelAttribute("candidate") Candidate candidate) {
		candidateService.saveCandidate(candidate);
		return "redirect:/CandidateList";
	}

	@GetMapping("/deleteCandidate/{id}")
	public String deleteCandidate(@PathVariable(value = "id") Long id) {
		this.candidateService.deleteCandidateById(id);
		return "redirect:/CandidateList";
	}

	// ************************ User Login And Registration
	// **************************

	@GetMapping("/login")
	public String loginHome(Model model) {
		return "login";
	}

	@GetMapping("/userhome")
	public String userHome() {
		return "userhome";
	}

	@GetMapping("/adminhome")
	public String adminhome() {
		return "adminhome";
	}

	@GetMapping("/profile")
	public String hrhome(Model model, Principal principal)
	{
//		String name=principal.getName();
//		User user=userRepository.findByName(name);
//		model.addAttribute("username",user.getFullname());
		return "profile";
	}

	@GetMapping("/pohome")
	public String pohome() {
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

	// ************************ End of User Login And Registration
	// **************************

	// ************************ End of User Login And Registration **************************


	// *******************  JoblistFilters and CandidateListfilters and mappedcandidatelist Code (Rugwed patharkar , Chinmay wagh) *********************
	// start
	// Show job list on joblistfilters
	@GetMapping("/joblist/{companyid}")
	public String getAllJobs(@PathVariable Long companyid, @RequestParam(defaultValue = "0") int page, Model model) {
		int pageSize = 10;
		Pageable pageable = PageRequest.of(page, pageSize);

		Page<Job> jobPage = jobService.findJobsByCompanyId(companyid, pageable);
		model.addAttribute("listJob", jobPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", jobPage.getTotalPages());

		return "joblistfilters.html";
	}

	// filter jobs on joblistfilters
	@GetMapping("/joblistfilters/{companyid}")
	public String getJobByCriteria(@PathVariable Long companyid,
			@RequestParam(value = "timeRange", required = false) String timeRange,
			@RequestParam(value = "keyword", required = false) String keyword, @ModelAttribute("job") Job job,
			@RequestParam(defaultValue = "0") int page, Model model) {

		Page<Job> jobPage;
		int pageSize = 10;
		Pageable pageable = PageRequest.of(page, pageSize);

		if (timeRange != null && !timeRange.isEmpty()) {
			switch (timeRange) {
			case "past24hours":
				jobPage = jobService.getJobsPostedPast24Hours(companyid, pageable);
				break;
			case "pastweek":
				jobPage = jobService.getJobsPostedPastWeek(companyid, pageable);
				break;
			case "pastmonth":
				jobPage = jobService.getJobsPostedPastMonth(companyid, pageable);
				break;
			default:
				jobPage = jobService.findJobsByCompanyId(companyid, pageable);
			}
		} else if (keyword != null && !keyword.isEmpty()) {
			jobPage = jobService.jobSearchByCompanyId(companyid, keyword, pageable);
		} else {
			jobPage = jobService.findJobByCompanyIdAndCriteria(companyid, job, pageable);
		}

		model.addAttribute("listJob", jobPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", jobPage.getTotalPages());

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

	// shows eligible candidate lists
	@GetMapping("/eligiblecandidates/{positionid}/{minKeywordLength}")
	public String showEligibleCandidates(@PathVariable int positionid, @PathVariable int minKeywordLength,
			@RequestParam(defaultValue = "0") int page, Model model) {
		int pageSize = 10; // Number of candidates to display per page1
		Job position = jobService.getJobbyId(positionid);
		model.addAttribute("position", position);

		Pageable pageable = PageRequest.of(page, pageSize);
		Page<Candidate> eligibleCandidates = jobService.findEligibleCandidates(positionid, minKeywordLength, pageable);

		model.addAttribute("listcandidate", eligibleCandidates);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", eligibleCandidates.getTotalPages());

		return "candidatelistfilters";
	}

	// filters for candidates on candidatelistfilters
	@GetMapping("/candidatelistfilters/{positionid}/{minKeywordLength}")
	public String applyFiltersOnCandidates(@PathVariable int positionid, @PathVariable int minKeywordLength,
			@RequestParam(required = false) String noofyearsworkex, @RequestParam(required = false) String workmode,
			@RequestParam(required = false) String joborinternship, @RequestParam(required = false) String search,
			Model model, @RequestParam(defaultValue = "0") int page) {
		int pageSize = 10;
		Job position = jobService.getJobbyId(positionid);
		model.addAttribute("position", position);
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<Candidate> eligibleCandidates;
		if (noofyearsworkex != null && !noofyearsworkex.isEmpty() || workmode != null && !workmode.isEmpty()
				|| joborinternship != null && !joborinternship.isEmpty()) {
			eligibleCandidates = jobService.findEligibleCandidates(positionid, minKeywordLength, noofyearsworkex,
					workmode, joborinternship, pageable);
		} else {
			eligibleCandidates = candidateService.searchEligibleCandidates(positionid, minKeywordLength, search,
					pageable);
		}
		model.addAttribute("listcandidate", eligibleCandidates);

		return "candidatelistfilters";
	}

	//map candidates to job on candidatelistfilters
	@PostMapping("/mapcandidatetojob/{positionid}/{companyid}/{minKeywordLength}")
	public String mapCandidatesToJob(@PathVariable int minKeywordLength, Model model,
			@RequestParam("positionid") int positionid, @RequestParam("companyid") Long companyid,
			@RequestParam("candidateids") List<Long> candidateids, @RequestParam(defaultValue = "0") int page) {

		int pageSize = 10;
		Job job = jobService.getJobbyId(positionid);
		Company company = companyService.getCompanyByCompanyid(companyid);
		Pageable pageable = PageRequest.of(page, pageSize);

		List<Candidate> selectedCandidates = candidateService.getCandidatesByIds(candidateids);
		List<Institute> selectedInstitutes = new ArrayList<>();

		// Fetch the corresponding institutes based on the selected candidates
		for (Candidate candidate : selectedCandidates) {
			Institute institute = candidate.getInstitutename();
			selectedInstitutes.add(institute);
		}

		for (int i = 0; i < selectedCandidates.size(); i++) {
			Candidate candidate = selectedCandidates.get(i);
			Institute institute = selectedInstitutes.get(i);

			JobCandidate jobCandidate = new JobCandidate();
			jobCandidate.setJob(job);
			jobCandidate.setCompany(company);
			jobCandidate.setCandidate(candidate);
			jobCandidate.setInstitute(institute);
			jobCandidateService.saveJobCandidate(jobCandidate);
		}

		Job position = jobService.getJobbyId(positionid);
		model.addAttribute("position", position);
		Page<Candidate> eligibleCandidates = jobService.findEligibleCandidates(positionid, minKeywordLength, pageable);
		model.addAttribute("listcandidate", eligibleCandidates);

		return "candidatelistfilters";
	}

	//show list of mapped candidates to job on mappedcandidatelist
	@GetMapping("/mappedcandidatelist/{companyid}/{positionid}")
	public String getJobCandidatesByCompanyidAndPositionid(@PathVariable Long companyid, @PathVariable int positionid,
			@RequestParam(defaultValue = "0") int page, Model model) {
		// Common logic to fetch job candidates
		fetchJobCandidates(companyid, positionid, page, model);
		return "mappedcandidateslist";
	}
	
	
	//delete mapped candidates to jobs
	@PostMapping("/deletecandidates")
	public String deleteSelectedCandidates(@RequestParam("jobcandidateids") List<Long> jobcandidateids,
			@RequestParam("companyid") Long companyid, @RequestParam("positionid") int positionid,
			@RequestParam(defaultValue = "0") int page, Model model) {
		jobCandidateService.deleteByJobCandidateIdIn(jobcandidateids);

		// Reuse the common logic to fetch job candidates after deletion
		fetchJobCandidates(companyid, positionid, page, model);
		return "mappedcandidateslist";
	}

	// Common method to fetch jobcandidates
	private void fetchJobCandidates(Long companyid, int positionid, int page, Model model) {
		int pageSize = 10;
		Pageable pageable = PageRequest.of(page, pageSize);
		Job position = jobService.getJobbyId(positionid);
		model.addAttribute("position", position);
		Company company = companyService.getCompanyByCompanyid(companyid);
		Job job = jobService.getJobByPositionid(positionid);

		if (company == null || job == null) {
			// Handle error if needed
			// You can return a different view to show an error message or handle it as per
			// your application logic.
			return;
		}

		Page<JobCandidate> jobCandidatesPage = jobCandidateService.getJobCandidatesByCompanyAndJob(company, job,
				pageable);

		model.addAttribute("jobCandidates", jobCandidatesPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", jobCandidatesPage.getTotalPages());
	}

	// end

	// *******************  JoblistFilters and CandidateListfilters and mappedcandidatelist Code (Rugwed patharkar , Chinmay wagh) *********************
}
