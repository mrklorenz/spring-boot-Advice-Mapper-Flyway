package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>,
        PagingAndSortingRepository<Employee, Integer> {

    Employee findAllById(Integer employeeID);

    List<Employee> findAllByGender(String employeeGender);
}
