package com.edruval.employeemanagement.test;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.edruval.employeemanagement.dao.DAO;
import com.edruval.employeemanagement.dto.EmployeeDTO;
import com.edruval.employeemanagement.model.Employee;
import com.edruval.employeemanagement.service.EmployeeService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeService.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @MockBean
    private DAO<Employee> employeeDAO;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployees() {
        Mockito.when(employeeDAO.getAll()).thenReturn(TestHelper.buildTestEmployeeList());

        List<EmployeeDTO> response = employeeService.getEmployees();
        Assert.assertTrue(response.size() > 0);
    }

    @Test
    public void getEmployee() {
        Mockito.when(employeeDAO.get(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(TestHelper.buildTestEmployee(1)));

        EmployeeDTO response = employeeService.getEmployee(1);
        Assert.assertNotNull(response);
    }

    @Test
    public void getEmployee_EmployeeNotFound() {
        Mockito.when(employeeDAO.get(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

        EmployeeDTO response = employeeService.getEmployee(-1);
        Assert.assertNull(response);
    }

    @Test
    public void addEmployee() {
        Mockito.when(employeeDAO.add(Mockito.any(Employee.class))).thenReturn(1L);

        EmployeeDTO response = employeeService.addEmployee(TestHelper.buildTestEmployeeDTO(0));
        long expected = 1L;
        Assert.assertEquals(expected, response.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addEmployee_Invalid() {
        Mockito.when(employeeDAO.add(Mockito.any(Employee.class))).thenReturn(1L);

        employeeService.addEmployee(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateEmployee_Invalid() {
        Mockito.doNothing().when(employeeDAO).update(Mockito.any(Employee.class));
        Mockito.when(employeeDAO.get(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

        employeeService.updateEmployee(1, TestHelper.buildTestEmployeeDTO(1));
    }

    @Test
    public void updateEmployee() {
        Mockito.doNothing().when(employeeDAO).update(Mockito.any(Employee.class));
        Mockito.when(employeeDAO.get(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(TestHelper.buildTestEmployee(1)));

        employeeService.updateEmployee(1, TestHelper.buildTestEmployeeDTO(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteEmployee_Invalid() {
        Mockito.doNothing().when(employeeDAO).delete(Mockito.anyLong());
        Mockito.when(employeeDAO.get(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

        employeeService.deleteEmployee(1);
    }

    @Test
    public void deleteEmployee() {
        Mockito.doNothing().when(employeeDAO).delete(Mockito.anyLong());
        Mockito.when(employeeDAO.get(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(TestHelper.buildTestEmployee(1)));

        employeeService.deleteEmployee(1);
    }
}
