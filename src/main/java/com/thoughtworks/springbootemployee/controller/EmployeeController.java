package com.thoughtworks.springbootemployee.controller;


import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployeeInfo() {
        return employeeService.getAllEmployees();
    }

    @GetMapping(path = "/{employeeID}")
    public Employee findEmployeeByID(@PathVariable Integer employeeID) {
        return employeeService.findEmployeeById(employeeID);
    }

    @GetMapping(params = "gender")
    public List<Employee> findEmployeesByGender(@RequestParam String gender) {
        return employeeService.findEmployeesByGender(gender);
    }

    @GetMapping(params = {"pageIndex", "pageSize"})
    public List<Employee> findEmployeesByPagination(@RequestParam int pageIndex, @RequestParam int pageSize) {
        return employeeService.findEmployeesByPagination(pageIndex, pageSize);
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @PutMapping(path = "/{employeeID}")
    public Employee updateEmployee(@PathVariable Integer employeeID, @RequestBody Employee employeeDetails) {
        return employeeService.updateEmployeeByID(employeeID, employeeDetails);
    }

    @DeleteMapping(path = "/{employeeID}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void removeEmployee(@PathVariable Integer employeeID) {
        employeeService.deleteEmployeeByID(employeeID);
    }

}