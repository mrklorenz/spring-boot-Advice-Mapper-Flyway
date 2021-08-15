package com.thoughtworks.springbootemployee.controller;


import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping(path = "/{companyID}")
    public Company findCompanyByID(@PathVariable Integer companyID) {
        return companyService.findCompanyByID(companyID);
    }

    @GetMapping(path = "/{companyID}/employees")
    public List<Employee> getCompanyEmployees(@PathVariable Integer companyID) {
        return companyService.getEmployeeListByCompanyId(companyID);
    }

    @GetMapping(params = {"pageIndex", "pageSize"})
    public List<Company> findCompaniesByPagination(@RequestParam int pageIndex, @RequestParam int pageSize) {
        return companyService.findCompaniesByPagination(pageIndex, pageSize);
    }

    @PostMapping
    public Company addCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }

    @PutMapping(path = "/{companyID}")
    public Company updateCompany(@PathVariable Integer companyID, @RequestBody Company companyDetails) {
        return companyService.updateCompanyByID(companyID, companyDetails);
    }

    @DeleteMapping(path = "/{companyID}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void removeCompany(@PathVariable Integer companyID) {
        companyService.deleteCompanyByID(companyID);
    }

}
