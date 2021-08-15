package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company findCompanyByID(Integer companyID) {
        return companyRepository.findAllById(companyID);
    }

    public List<Employee> getEmployeeListByCompanyId(Integer companyID) {
        Company company = companyRepository.findById(companyID).orElse(null);
        return company.getEmployees();
    }

    public List<Company> findCompaniesByPagination(int pageIndex, int pageSize) {
        return companyRepository.findAll(PageRequest.of(pageIndex - 1, pageSize)).getContent();
    }

    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompanyByID(Integer companyID, Company newCompany) {
        Company updateCompany = companyRepository.findById(companyID)
                .map(company -> updateCompanyInfo(company, newCompany))
                .get();

        return companyRepository.save(updateCompany);
    }

    public void deleteCompanyByID(int companyID) {
        companyRepository.deleteById(companyID);
    }

    private Company updateCompanyInfo(Company company, Company newCompany) {
        if (newCompany.getName() != null) company.setName(newCompany.getName());
        return company;
    }
}
