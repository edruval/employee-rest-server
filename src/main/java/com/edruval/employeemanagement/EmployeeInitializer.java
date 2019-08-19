package com.edruval.employeemanagement;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.edruval.employeemanagement.configuration.ApplicationConfiguration;
import com.edruval.employeemanagement.jpa.EmployeeRepository;
import com.edruval.employeemanagement.model.Employee;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@Component
public class EmployeeInitializer implements CommandLineRunner {

    @Autowired
    private ApplicationConfiguration configuration;
    
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) throws Exception {
        String employeesFile = configuration.getEmployeesFile();
        List<Employee> employees = loadObjectList(Employee.class, employeesFile);
        employees.forEach(employeeRepository::save);
    }

    public <T> List<T> loadObjectList(Class<T> type, String fileName) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            File file = new ClassPathResource(fileName).getFile();
            MappingIterator<T> readValues = mapper.readerFor(type).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
