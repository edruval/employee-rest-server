package com.edruval.employeemanagement.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("local")
@Data
public class ApplicationConfiguration {
    private String employeesFile;
    private String serverUrl;
}
