package com.thoughtworks.springbootemployee.model;

import com.thoughtworks.springbootemployee.entity.Employee;

import java.util.List;

public class CompanyRequest {
    private Integer id;
    private String name;
    private List<Employee> employees;

    public CompanyRequest()
    {

    }
    public CompanyRequest(Integer id, String name, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public CompanyRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
