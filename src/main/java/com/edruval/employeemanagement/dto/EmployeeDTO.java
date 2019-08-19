package com.edruval.employeemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private long id;
    private String firstName;
    private String middleInitial;
    private String lastName;
    private String dateOfBirth;
    private String dateOfEmployment;
}
