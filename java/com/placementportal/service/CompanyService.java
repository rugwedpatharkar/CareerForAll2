package com.placementportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placementportal.model.Company;
import com.placementportal.repository.CompanyRepository;

@Service
public class CompanyService {
	@Autowired
	private CompanyRepository companyRepository;
	
	public CompanyService(CompanyRepository companyRepository) {
		
		this.companyRepository = companyRepository;
	}

	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	public void saveCompany(Company company) {
		this.companyRepository.save(company);
	}

	public Company getCompanyById(Long id) {
		return companyRepository.findById(id).orElse(null);
	}

	public void deleteCompanyById(Long id) {
		this.companyRepository.deleteById(id);

	}

	// ******************* JoblistFilters and CandidateListfilters and
	// mappedcandidatelist Code (Rugwed patharkar , Chinmay wagh)
	// *********************
	// start
	public Company findCompanyById(Long companyid) {
		return companyRepository.findById(companyid).orElse(null);
	}

	public Company getCompanyByCompanyid(Long companyid) {
		return companyRepository.findById(companyid)
				.orElseThrow(() -> new IllegalArgumentException("Company not found with ID: " + companyid));
	}

	// end

	// ******************* JoblistFilters and CandidateListfilters and
	// mappedcandidatelist Code (Rugwed patharkar , Chinmay wagh)
	// *********************

}
