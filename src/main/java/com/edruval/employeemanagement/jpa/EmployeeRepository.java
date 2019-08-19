package com.edruval.employeemanagement.jpa;

import org.springframework.data.repository.CrudRepository;

import com.edruval.employeemanagement.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
