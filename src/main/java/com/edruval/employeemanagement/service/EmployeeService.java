package com.edruval.employeemanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edruval.employeemanagement.dao.DAO;
import com.edruval.employeemanagement.dto.EmployeeDTO;
import com.edruval.employeemanagement.model.Employee;

@Service
public class EmployeeService {

    @Autowired
    private DAO<Employee> employeeDAO;

    public List<EmployeeDTO> getEmployees() {
        List<Employee> employees = employeeDAO.getAll();
        employees.removeIf(e -> e.getStatus().equals(Employee.EmployeeStatus.INACTIVE));
        return employees.stream().map(e -> ServiceHelper.employeeToDto(e)).collect(Collectors.toList());
    }

    public EmployeeDTO getEmployee(long employeeId) {
        Optional<Employee> employeeFound = employeeDAO.get(employeeId)
                .filter(e -> e.getStatus().equals(Employee.EmployeeStatus.ACTIVE));
  
        return ServiceHelper.employeeToDto(employeeFound.orElse(null));
    }

    public EmployeeDTO addEmployee(EmployeeDTO employeeDto) {
        Employee employee = ServiceHelper.dtoToEmployee(employeeDto);

        if (employee == null || !ServiceHelper.isEmployeeValid(employee) || employee.getId() != 0) {
            throw new IllegalArgumentException();
        }
        long generatedId = employeeDAO.add(employee);
        employeeDto.setId(generatedId);
        return employeeDto;
    }

    public void updateEmployee(long employeeId, EmployeeDTO employeeDto) {
        Employee employee = ServiceHelper.dtoToEmployee(employeeDto);
        boolean employeeFound = employeeDAO.get(employeeId).isPresent();
        if (!employeeFound || employee == null || !ServiceHelper.isEmployeeValid(employee)) {
            throw new IllegalArgumentException();
        }
        employee.setId(employeeId);
        employeeDAO.update(employee);
    }

    public void deleteEmployee(long employeeId) {
        Optional<Employee> employeeFound = employeeDAO.get(employeeId);
        if (!employeeFound.isPresent()) {
            throw new IllegalArgumentException();
        }
        Employee employeeToDelete = employeeFound.get();
        employeeToDelete.setStatus(Employee.EmployeeStatus.INACTIVE);
        employeeDAO.update(employeeToDelete);
    }
}