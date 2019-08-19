package com.edruval.employeemanagement.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.edruval.employeemanagement.dto.EmployeeDTO;
import com.edruval.employeemanagement.model.Employee;

public class ServiceHelper {
    
    public static EmployeeDTO employeeToDto(Employee employee) {
        if (employee == null) {
            return null;
        }
        return EmployeeDTO.builder().id(employee.getId()).firstName(employee.getFirstName())
                .middleInitial(employee.getMiddleInitial()).lastName(employee.getLastName())
                .dateOfBirth(employee.getDateOfBirth()).dateOfEmployment(employee.getDateOfEmployment()).build();
    }

    public static Employee dtoToEmployee(EmployeeDTO employeeDto) {
        if (employeeDto == null) {
            return null;
        }
        return Employee.builder().id(employeeDto.getId()).firstName(employeeDto.getFirstName())
                .middleInitial(employeeDto.getMiddleInitial()).lastName(employeeDto.getLastName())
                .dateOfBirth(employeeDto.getDateOfBirth()).dateOfEmployment(employeeDto.getDateOfEmployment()).build();
    }
    
    public static boolean isEmployeeValid(Employee e) {
        try {
            if (e.getFirstName() != null && !e.getFirstName().isEmpty() && e.getMiddleInitial() != null 
                    && e.getLastName() != null && !e.getLastName().isEmpty() 
                    && e.getDateOfBirth() != null && !e.getDateOfBirth().isEmpty() 
                    && e.getDateOfEmployment() != null && !e.getDateOfEmployment().isEmpty()) {
                LocalDate.parse(e.getDateOfBirth(), DateTimeFormatter.ISO_LOCAL_DATE);
                LocalDate.parse(e.getDateOfEmployment(), DateTimeFormatter.ISO_LOCAL_DATE);
                return true;
            }
        } catch (DateTimeParseException ex) {
            return false;
        }
        return false;
    }
}
