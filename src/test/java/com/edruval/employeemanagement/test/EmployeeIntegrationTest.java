package com.edruval.employeemanagement.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResourceAccessException;

import com.edruval.employeemanagement.EmployeeManagementApplication;
import com.edruval.employeemanagement.configuration.ApplicationConfiguration;
import com.edruval.employeemanagement.dto.EmployeeDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmployeeManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeIntegrationTest {

    @Autowired
    private ApplicationConfiguration configuration;
    
    @LocalServerPort
    private int testPort;

    private TestRestTemplate template = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    private String testServerUrl;

    @Before
    public void before() {
        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        headers.setAccept(mediaTypes);
        testServerUrl = String.format("http://%s:%d/api/v1", configuration.getServerUrl(), testPort);
    }

    @Test
    public void retrieveAllEmployees() {
        HttpEntity<String> httpEntity = new HttpEntity<String>("Test_Body", headers);
        ParameterizedTypeReference<List<EmployeeDTO>> typeReference = 
                new ParameterizedTypeReference<List<EmployeeDTO>>() { };

        ResponseEntity<List<EmployeeDTO>> response = template.exchange(testServerUrl + "/employees",
                HttpMethod.GET, httpEntity, typeReference);
        Assert.assertTrue(response.getBody().size() > 0);
    }

    @Test
    public void retrieveEmployeeById() {
        HttpEntity<String> httpEntity = new HttpEntity<String>("Test_Body", headers);

        ResponseEntity<EmployeeDTO> response = template.exchange(testServerUrl + "/employees/3",
                HttpMethod.GET, httpEntity, EmployeeDTO.class);
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void retrieveEmployeeById_Invalid() {
        HttpEntity<String> httpEntity = new HttpEntity<String>("Test_Body", headers);
        
        ResponseEntity<EmployeeDTO> response = template.exchange(testServerUrl + "/employees/-1",
                HttpMethod.GET, httpEntity, EmployeeDTO.class);
        Assert.assertTrue(response.getStatusCode().equals(HttpStatus.NOT_FOUND));
    }

    @Test
    public void createEmployee() {
        EmployeeDTO employeeToCreate = TestHelper.buildTestEmployeeDTO(0);
        HttpEntity<EmployeeDTO> httpEntity = new HttpEntity<EmployeeDTO>(employeeToCreate, headers);

        ResponseEntity<EmployeeDTO> response = template.exchange(testServerUrl + "/employees",
                HttpMethod.POST, httpEntity, EmployeeDTO.class);
        Assert.assertTrue(response.getStatusCode().equals(HttpStatus.OK));
        EmployeeDTO createdEmployee = response.getBody();
        Assert.assertNotNull(createdEmployee);
    }

    @Test
    public void createEmployee_Invalid() {
        EmployeeDTO employeeToCreate = EmployeeDTO.builder().firstName("firstName-only").build();
        HttpEntity<EmployeeDTO> httpEntity = new HttpEntity<EmployeeDTO>(employeeToCreate, headers);

        ResponseEntity<String> response = template.exchange(testServerUrl + "/employees", 
                HttpMethod.POST, httpEntity, String.class);
        Assert.assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void UpdateEmployee() {
        EmployeeDTO employee = TestHelper.buildTestEmployeeDTO(0);
        HttpEntity<EmployeeDTO> httpCreateEntity = new HttpEntity<EmployeeDTO>(employee, headers);
        
        ResponseEntity<EmployeeDTO> createResponse = template.exchange(testServerUrl + "/employees",
                HttpMethod.POST, httpCreateEntity, EmployeeDTO.class);
        Assert.assertTrue(createResponse.getStatusCode().equals(HttpStatus.OK));
        EmployeeDTO createdEmployee = createResponse.getBody();
        Assert.assertNotNull(createdEmployee);

        long createdId = createdEmployee.getId();
        employee.setId(createdId);
        employee.setFirstName("Updated");
        HttpEntity<EmployeeDTO> httpUpdateEntity = new HttpEntity<EmployeeDTO>(employee, headers);
        ResponseEntity<String> updateResponse = template.exchange(testServerUrl + "/employees/" + createdId, 
                HttpMethod.PUT, httpUpdateEntity,String.class);
        Assert.assertTrue(updateResponse.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    public void UpdateEmployee_Invalid() {
        EmployeeDTO employee = TestHelper.buildTestEmployeeDTO(0);
        HttpEntity<EmployeeDTO> httpCreateEntity = new HttpEntity<EmployeeDTO>(employee, headers);
        
        ResponseEntity<EmployeeDTO> createResponse = template.exchange(testServerUrl + "/employees",
                HttpMethod.POST, httpCreateEntity, EmployeeDTO.class);
        Assert.assertTrue(createResponse.getStatusCode().equals(HttpStatus.OK));
        EmployeeDTO createdEmployee = createResponse.getBody();
        Assert.assertNotNull(createdEmployee);

        long createdId = createdEmployee.getId();
        employee.setId(createdId);
        employee.setFirstName(null);
        HttpEntity<EmployeeDTO> httpUpdateEntity = new HttpEntity<EmployeeDTO>(employee, headers);
        ResponseEntity<String> updateResponse = template.exchange(testServerUrl + "/employees/" + createdId, 
                HttpMethod.PUT, httpUpdateEntity,String.class);
        Assert.assertTrue(updateResponse.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }
    
    @Test
    public void UpdateEmployee_NotFound() {
        EmployeeDTO employeeToUpdate = EmployeeDTO.builder().id(0).build();
        HttpEntity<EmployeeDTO> httpUpdateEntity = new HttpEntity<EmployeeDTO>(employeeToUpdate, headers);

        ResponseEntity<String> updateResponse = template.exchange(testServerUrl + "/employees/-1",
                HttpMethod.PUT, httpUpdateEntity, String.class);
        Assert.assertTrue(updateResponse.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void DeleteEmploye() {
        EmployeeDTO employeeToCreate = TestHelper.buildTestEmployeeDTO(0);
        HttpEntity<EmployeeDTO> httpCreateEntity = new HttpEntity<EmployeeDTO>(employeeToCreate, headers);

        ResponseEntity<EmployeeDTO> createResponse = template.exchange(testServerUrl + "/employees",
                HttpMethod.POST, httpCreateEntity, EmployeeDTO.class);
        EmployeeDTO createdEmployee = createResponse.getBody();
        Assert.assertNotNull(createdEmployee);

        HttpEntity<String> httpDeleteEntity = new HttpEntity<String>("Test_Body", headers);
        ResponseEntity<String> deleteResponse = template.withBasicAuth("admin", "Secr3t!")
                .exchange(testServerUrl + "/employees/" + createdEmployee.getId(), 
                HttpMethod.DELETE, httpDeleteEntity, String.class);
        Assert.assertTrue(deleteResponse.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    public void DeleteEmploye_Invalid() {
        HttpEntity<String> httpDeleteEntity = new HttpEntity<String>("Test_Body", headers);
        
        ResponseEntity<String> deleteResponse = template.withBasicAuth("admin", "Secr3t!").exchange(
                testServerUrl + "/employees/-1", HttpMethod.DELETE, httpDeleteEntity, String.class);
        Assert.assertTrue(deleteResponse.getStatusCode().equals(HttpStatus.NOT_FOUND));
    }

    @Test(expected = ResourceAccessException.class)
    public void DeleteEmploye_NoCredentials() {
        HttpEntity<String> httpDeleteEntity = new HttpEntity<String>("Test_Body", headers);

        template.exchange(testServerUrl + "/employees/1", HttpMethod.DELETE, httpDeleteEntity, String.class);
    }
}
