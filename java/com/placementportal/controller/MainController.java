package com.placementportal.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import io.micrometer.common.util.StringUtils;
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

	@GetMapping("/login")
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

	@GetMapping("/startup_onboarding")
	@PreAuthorize("hasRole('HR')")
	public String startup_onboarding(Model model) {
		Company company = new Company();
		model.addAttribute("company", company);
		return ("startup_onboarding");
	}
	
	// Save company
	@PostMapping("/saveCompany")
    public String saveCompany(@ModelAttribute("company") Company company, RedirectAttributes redirectAttributes) {
        companyService.saveCompany(company);
        redirectAttributes.addFlashAttribute("message", "Company added successfully!");
        return "redirect:/CompanyList";
    }
	
	// List of companies
	@GetMapping("/CompanyList")
	public String showCompany(Model model) {
		model.addAttribute("listCompanies", companyService.getAllCompanies());
		return "CompanyList";
	}

	// Update company
	@GetMapping("/editCompany/{id}")
	public String editCompanyForm(@PathVariable("id") Long id, Model model) {
	    Company company = companyService.getCompanyById(id);
	    model.addAttribute("company", company);
	    return "editCompany";
	}

	@PostMapping("/updateCompany")
	public String updateCompany(@ModelAttribute("company") Company company, RedirectAttributes redirectAttributes) {
		companyService.saveCompany(company);
		redirectAttributes.addFlashAttribute("umessage", "Company updated successfully!");
		return "redirect:/CompanyList";
	}
	
	// Delete company
	@GetMapping("/deleteCompany/{id}")
	public String deleteCompany(@PathVariable(value = "id") Long id, RedirectAttributes redirectAttributes) {
		// call delete company method
		this.companyService.deleteCompanyById(id);
		redirectAttributes.addFlashAttribute("dmessage", "Company deleted successfully!");
		return "redirect:/CompanyList";
	}

	// ---------------------end of startup onboarding------------------------

	// ---------------------Institute onboarding-----------------------
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

	@GetMapping("/InstituteList")
	public String showInstitute(Model model) {
		model.addAttribute("listInstitutes", instituteService.getAllInstitute());
		return "InstituteList";
	}

	@GetMapping("/editInstitute/{id}")
	public String editInstituteForm(@PathVariable("id") Long id, Model model) {
		Institute institute = instituteService.getInstituteById(id);
		model.addAttribute("institute", institute);
		return "editInstitute";
	}

	@PostMapping("/updateInstitute")
	public String updateInstitute(@ModelAttribute("institute") Institute institute) {
		instituteService.saveInstitute(institute);
		return "redirect:/InstituteList";
	}

	@GetMapping("/deleteInstitute/{id}")
	public String deleteInstitute(@PathVariable(value = "id") Long id) {
		this.instituteService.deleteInstituteById(id);
		return "redirect:/InstituteList";
	}

	// ---------------------end of Institute onboarding------------------------

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
	public String saveCandidate(@ModelAttribute("candidate") Candidate candidate,
			@RequestParam("educationalDetailsJson") String educationalDetailsJson) {
		candidateService.saveCandidate(candidate, educationalDetailsJson);
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
	public String updateCandidate(@ModelAttribute("candidate") Candidate candidate,
			@RequestParam("educationalDetailsJson") String educationalDetailsJson) {
		candidateService.saveCandidate(candidate, educationalDetailsJson);
		return "redirect:/CandidateList";
	}

	@GetMapping("/deleteCandidate/{id}")
	public String deleteCandidate(@PathVariable(value = "id") Long id) {
		this.candidateService.deleteCandidateById(id);
		return "redirect:/CandidateList";
	}

	// ************************ User Login And Registration
	// **************************

//	@GetMapping("/login")
//	public String loginHome(Model model) {
//		return "login";
//	}

	@GetMapping("/userhome")
	public String userHome() {
		return "userhome";
	}

	@GetMapping("/useraccountsetting")
	public String useraccount() {
		return "useraccountsetting";
	}

	@GetMapping("/adminhome")
	public String adminhome() {
		return "adminhome";
	}

	@GetMapping("/adminaccountsetting")
	public String adminaccount() {
		return "adminaccountsetting";
	}

	@GetMapping("/profile")
	public String hrhome(Model model, Principal principal) {
		return "profile";
	}

	@GetMapping("/hraccountsetting")
	public String hraccount() {
		return "hraccountsetting";
	}

	@GetMapping("/pohome")
	public String pohome() {
		return "pohome";
	}

	@GetMapping("/poaccountsetting")
	public String poaccount() {
		return "poaccountsetting";
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

		return "redirect:/login?success";
	}

	// *************** ADMIN Controller **************************


	// Admin-Company Controllers
	
	@GetMapping("/adminreg")
	public String adminUser(Model model) {
		model.addAttribute("user",  new User());
		return "adminuser";
	}
	
	@PostMapping("/adminreg")
	public String adminReg(@ModelAttribute User user, HttpSession session) {
		
		userRepository.save(user);
		
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

		return "redirect:/adminhome?success";
	}
	
		@PostMapping("/adminsaveCompany")
	    public String adminsaveCompany(@ModelAttribute("company") Company company, RedirectAttributes redirectAttributes) {
	        companyService.saveCompany(company);
	        redirectAttributes.addFlashAttribute("message", "Company added successfully!");
	        return "redirect:/CompanyList";
	    }
		
	@GetMapping("/admincompany")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminstartup_onboarding(Model model) {
		Company company = new Company();
		model.addAttribute("company", company);
		return ("admincompany");
	}

	// List of companies
	@GetMapping("/adminCompanyList")
	public String adminshowCompany(Model model) {
		model.addAttribute("listCompanies", companyService.getAllCompanies());
		return "adminCompanyList";
	}

	// Update company
	@GetMapping("/admineditCompany/{id}")
	public String admineditCompanyForm(@PathVariable("id") Long id, Model model) {
		Company company = companyService.getCompanyById(id);
		model.addAttribute("company", company);
		return "admineditCompany";
	}

	@PostMapping("/adminupdateCompany")
	public String adminupdateCompany(@ModelAttribute("company") Company company,
			RedirectAttributes redirectAttributes) {
		companyService.saveCompany(company);
		redirectAttributes.addFlashAttribute("umessage", "Company updated successfully!");
		return "redirect:/adminCompanyList";
	}

	@GetMapping("/admindeleteCompany/{id}")
	public String admindeleteCompany(@PathVariable(value = "id") Long id, RedirectAttributes redirectAttributes) {
		// call delete company method
		this.companyService.deleteCompanyById(id);
		redirectAttributes.addFlashAttribute("dmessage", "Company deleted successfully!");
		return "redirect:/adminCompanyList";
	}

	// ***************** End Admin Controller

	// ************************ End of User Login And Registration
	// **************************

	// ******************* JoblistFilters and CandidateListfilters and
	// mappedcandidatelist Code (Rugwed patharkar , Chinmay wagh)
	// *********************
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
				jobPage = jobService.getJobsPostedPast24Hours(companyid, job, pageable);
				break;
			case "pastweek":
				jobPage = jobService.getJobsPostedPastWeek(companyid, job, pageable);
				break;
			case "pastmonth":
				jobPage = jobService.getJobsPostedPastMonth(companyid, job, pageable);
				break;
			default:
				jobPage = jobService.findJobByCompanyIdAndCriteria(companyid, job, pageable);
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
//	@GetMapping("/cvdownload/{candidateid}")
//	public ResponseEntity<Resource> downloadFile1(@PathVariable long candidateid) throws IOException {
//		Optional<Candidate> fileEntityOptional = candidateService.getFileById(candidateid);
//		if (fileEntityOptional.isPresent()) {
//			Candidate candidate = fileEntityOptional.get();
//			ByteArrayResource resource = new ByteArrayResource(candidate.getCvupload());
//
//			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION)
//					.contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(candidate.getCvupload().length)
//					.body(resource);
//		}
//		return ResponseEntity.notFound().build();
//	}
	@GetMapping("/cvdownload/{candidateid}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long candidateid) throws IOException {
		Optional<Candidate> candidateOptional = candidateService.getFileById(candidateid);
		if (candidateOptional.isPresent()) {
			Candidate candidate = candidateOptional.get();
			byte[] cvupload = candidate.getCvupload();

			ByteArrayResource resource = new ByteArrayResource(cvupload);

			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=" + candidate.getCandidatename() + ".pdf");

			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
					.contentLength(cvupload.length).body(resource);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// shows eligible candidate lists
	@GetMapping("/eligiblecandidates/{positionid}/{minKeywordLength}")
	public String showEligibleCandidates(@PathVariable int positionid, @PathVariable int minKeywordLength,
			@RequestParam(defaultValue = "0") int page, Model model) {
		int pageSize = 5; // Number of candidates to display per page1
		Job position = jobService.getJobbyId(positionid);
		model.addAttribute("position", position);

		Pageable pageable = PageRequest.of(page, pageSize);
		Page<Candidate> eligibleCandidates = candidateService.findEligibleCandidates(positionid, minKeywordLength,
				pageable);

		model.addAttribute("listcandidate", eligibleCandidates);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", eligibleCandidates.getTotalPages());

		return "candidatelistfilters";
	}

	// filters for candidates on candidate list filters
//	@GetMapping("/candidatelistfilters/{positionid}/{minKeywordLength}")
//	public String applyFiltersOnCandidates(@PathVariable int positionid, @PathVariable int minKeywordLength,
//			@RequestParam(required = false) String noofyearsworkex, @RequestParam(required = false) String workmode,
//			@RequestParam(required = false) String joborinternship, @RequestParam(required = false) String search,
//			Model model, @RequestParam(defaultValue = "0") int page) {
//		int pageSize = 10;
//		Job position = jobService.getJobbyId(positionid);
//		model.addAttribute("position", position);
//		Pageable pageable = PageRequest.of(page, pageSize);
//		Page<Candidate> eligibleCandidates;
//		if (!noofyearsworkex.isEmpty() || !workmode.isEmpty() || !joborinternship.isEmpty() || !search.isEmpty()) {
//			eligibleCandidates = candidateService.findEligibleCandidates(positionid, minKeywordLength, noofyearsworkex,
//					workmode, joborinternship, pageable);
//			eligibleCandidates = candidateService.searchEligibleCandidates(positionid, minKeywordLength, search,
//					pageable);
//		} else {
//			eligibleCandidates = candidateService.searchEligibleCandidates(positionid, minKeywordLength, search,
//					pageable);
//		}
//		model.addAttribute("listcandidate", eligibleCandidates);
//
//		return "candidatelistfilters";
//	}
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

		if (StringUtils.isNotBlank(noofyearsworkex) || StringUtils.isNotBlank(workmode)
				|| StringUtils.isNotBlank(joborinternship)) {
			eligibleCandidates = candidateService.findEligibleCandidates(positionid, minKeywordLength, noofyearsworkex,
					workmode, joborinternship, pageable);
		} else if (StringUtils.isNotBlank(search)) {
			eligibleCandidates = candidateService.searchEligibleCandidates(positionid, minKeywordLength, search,
					pageable);
		} else {
			eligibleCandidates = candidateService.searchEligibleCandidates(positionid, minKeywordLength, search,
					pageable);
		}

		model.addAttribute("listcandidate", eligibleCandidates);

		return "candidatelistfilters";
	}

	// show list of mapped candidates to job on mappedcandidatelist
	@GetMapping("/mappedcandidatelist/{companyid}/{positionid}")
	public String getJobCandidatesByCompanyidAndPositionid(@PathVariable Long companyid, @PathVariable int positionid,
			@RequestParam(defaultValue = "0") int page, Model model) {
		// Common logic to fetch job candidates
		fetchJobCandidates(companyid, positionid, page, model);
		return "mappedcandidateslist";
	}

	// delete mapped candidates to jobs
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
			return;
		}

		Page<JobCandidate> jobCandidatesPage = jobCandidateService.getJobCandidatesByCompanyAndJob(company, job,
				pageable);

		model.addAttribute("jobCandidates", jobCandidatesPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", jobCandidatesPage.getTotalPages());
	}

	// controller to show profile of shortlisted candidate
	@GetMapping("/candidate/{candidateid}/profile")
	public String getCandidateProfile(@PathVariable Long candidateid, Model model) {
		Candidate candidate = candidateService.getCandidateByCandidateid(candidateid);
		model.addAttribute("candidate", candidate);
		return "shortlistedcandidateprofile"; // Thymeleaf template name
	}
	// end

	// ******************* JoblistFilters and CandidateListfilters and
	// mappedcandidatelist Code (Rugwed patharkar , Chinmay wagh)
	// *********************

	// Positionlist Code (Tanmayi)
	// start

	///
	@PostMapping("/savePosition")
	public String savePosition(@ModelAttribute("job") Job job, RedirectAttributes redirectAttributes) {
		jobService.savePosition(job);
		redirectAttributes.addFlashAttribute("message", "Company added successfully!");
		return ("redirect:/positionlist");
	}

	public String savePosition(@ModelAttribute("job") Job job) {
		jobService.savePosition(job);
		return ("redirect:/positionlist");
	}

	// @GetMapping("/jobopening")
	@PreAuthorize("hasRole('HR')")
	public String jobopening(Model model) {
		Job job = new Job();
		model.addAttribute("job", job);
		return ("jobopening");
	}

	// List of positions
	@GetMapping("/positionlist")
	public String showPosition(Model model) {
		model.addAttribute("listJob", jobService.getAllPositions());
		return "positionlist";
	}

	// Update position
	@GetMapping("/editPosition/{id}")
	public String editPositionForm(@PathVariable("id") int id, Model model) {
		Job job = jobService.getPositionById(id);
		model.addAttribute("job", job);
		return "editPosition";
	}

	@PostMapping("/updatePosition")
	public String updatePosition(@ModelAttribute("job") Job job, RedirectAttributes redirectAttributes) {
		jobService.savePosition(job);
		redirectAttributes.addFlashAttribute("umessage", "Position updated successfully!");
		return "redirect:/positionlist";
	}

	@GetMapping("/deletePosition/{id}")

	public String deletePosition(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
		// call delete Position method

		this.jobService.deletePositionById(id);
		redirectAttributes.addFlashAttribute("dmessage", "Position deleted successfully!");
		return "redirect:/positionlist";
	}

}
