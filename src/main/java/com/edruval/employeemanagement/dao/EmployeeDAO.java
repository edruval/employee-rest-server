package com.edruval.employeemanagement.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edruval.employeemanagement.jpa.EmployeeRepository;
import com.edruval.employeemanagement.model.Employee;

@Component
public class EmployeeDAO implements DAO<Employee> {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeDAO() { }

    @Override
    public Optional<Employee> get(long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employeeList = new ArrayList<Employee>();
        employeeRepository.findAll().forEach(employeeList::add);
        return employeeList;
    }

    @Override
    public long add(Employee employee) {
        return employeeRepository.save(employee).getId();
    }

    @Override
    public void update(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void delete(long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

}