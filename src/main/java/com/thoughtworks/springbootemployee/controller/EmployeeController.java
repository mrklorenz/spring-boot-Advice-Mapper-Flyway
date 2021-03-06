package com.thoughtworks.springbootemployee.controller;


import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.EmployeeResponse;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeMapper employeeMapper;

    @GetMapping
    public List<EmployeeResponse> getAllEmployeeInfo() {
        List<Employee> employees = employeeService.getAllEmployees();
        return mapListToEmployeeResponse(employees);
    }

    @GetMapping(path = "/{employeeID}")
    public EmployeeResponse findEmployeeByID(@PathVariable Integer employeeID) {
        return employeeMapper.toResponse(employeeService.findEmployeeById(employeeID));
    }

    @GetMapping(params = "gender")
    public List<EmployeeResponse> findEmployeesByGender(@RequestParam String gender) {
        List<Employee> employees = employeeService.findEmployeesByGender(gender);
        return mapListToEmployeeResponse(employees);
    }

    @GetMapping(params = {"pageIndex", "pageSize"})
    public List<EmployeeResponse> findEmployeesByPagination(@RequestParam int pageIndex, @RequestParam int pageSize) {
        List<Employee> employees = employeeService.findEmployeesByPagination(pageIndex, pageSize);
        return mapListToEmployeeResponse(employees);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EmployeeResponse addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeService.addEmployee(employeeMapper.toEntity(employeeRequest));
        return employeeMapper.toResponse(employee);
    }

    @PutMapping(path = "/{employeeId}")
    public EmployeeResponse updateEmployee(@PathVariable Integer employeeId, @RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeService.updateEmployeeByID(employeeId, employeeMapper.toEntity(employeeRequest));
        return employeeMapper.toResponse(employee);
    }

    @DeleteMapping(path = "/{employeeID}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public EmployeeResponse removeEmployee(@PathVariable Integer employeeID) {
        return employeeMapper.toResponse(employeeService.deleteEmployeeByID(employeeID));
    }

    public List<EmployeeResponse> mapListToEmployeeResponse(List<Employee> employees) {
        return employees.stream()
                .map(employee -> employeeMapper.toResponse(employee))
                .collect(Collectors.toList());
    }

}