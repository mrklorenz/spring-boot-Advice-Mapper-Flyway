package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.NoEmployeeWithIDFoundException;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee findEmployeeById(Integer employeeID) {
        return employeeRepository.findById(employeeID)
                .orElseThrow(()->new NoEmployeeWithIDFoundException("No employee found with given ID!"));
    }

    public List<Employee> findEmployeesByGender(String employeeGender) {
        return employeeRepository.findAllByGender(employeeGender);
    }

    public List<Employee> findEmployeesByPagination(int pageIndex, int pageSize) {
        return employeeRepository.findAll(PageRequest.of(pageIndex - 1, pageSize)).getContent();
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee deleteEmployeeByID(Integer employeeID) {
        employeeRepository.deleteById(employeeID);
        return employeeRepository.findById(employeeID)
                .orElseThrow(()->new NoEmployeeWithIDFoundException("Employee Deleted!"));
    }

    public Employee updateEmployeeByID(Integer employeeID, Employee employeeDetails) {
        Employee updateEmployee = employeeRepository.findById(employeeID)
                .map(employee -> updateEmployeeInfo(employee, employeeDetails))
                .orElseThrow(()-> new NoEmployeeWithIDFoundException("No employee found for given id!"));

        return employeeRepository.save(updateEmployee);
    }

    private Employee updateEmployeeInfo(Employee employee, Employee employeeDetails) {

        if (employeeDetails.getName() != null) employee.setName(employeeDetails.getName());
        if (employeeDetails.getAge() != null) employee.setAge(employeeDetails.getAge());
        if (employeeDetails.getGender() != null) employee.setGender(employeeDetails.getGender());
        if (employeeDetails.getSalary() != null) employee.setSalary(employeeDetails.getSalary());
        if (employeeDetails.getCompanyId() != null) employee.setCompanyId(employeeDetails.getCompanyId());

        return employee;
    }


}
