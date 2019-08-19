package com.edruval.employeemanagement.test;

import java.util.ArrayList;
import java.util.List;

import com.edruval.employeemanagement.dto.EmployeeDTO;
import com.edruval.employeemanagement.model.Employee;

public class TestHelper {

    private TestHelper() {
    }

    public static List<Employee> buildTestEmployeeList() {
        List<Employee> employeeList = new ArrayList<>();

        employeeList.add(buildTestEmployee(1));
        employeeList.add(buildTestEmployee(2));
        employeeList.add(buildTestEmployee(3));
        employeeList.add(buildTestEmployee(4));

        return employeeList;
    }

    public static Employee buildTestEmployee(long id) {

        return Employee.builder().id(id).firstName("firstName" + id).middleInitial("M").lastName("lastName" + id)
                .dateOfBirth("1990-01-01").dateOfEmployment("2000-01-01").build();
    }

    public static EmployeeDTO buildTestEmployeeDTO(long id) {
        return EmployeeDTO.builder().id(id).firstName("firstDTO" + id).middleInitial("D").lastName("lastDTO" + id)
                .dateOfBirth("1990-01-01").dateOfEmployment("2000-01-01").build();
    }
}
