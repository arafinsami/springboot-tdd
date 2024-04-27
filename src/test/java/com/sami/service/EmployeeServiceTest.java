package com.sami.service;

import com.sami.dto.EmployeeDTO;
import com.sami.entity.Employee;
import com.sami.mapper.EmployeeMapper;
import com.sami.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void saveEmployeeTest() {
        EmployeeDTO request = new EmployeeDTO();
        Employee employee = new Employee();
        when(employeeMapper.save(request)).thenReturn(employee);
        Employee savedEmployee = employeeService.save(request);
        assertEquals(employee, savedEmployee);
        verify(employeeMapper, times(1)).save(request);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void updateEmployeeTest() {
        String employeeId = "140c7291-5afc-49c1-a253-cb156737aa81";
        EmployeeDTO request = new EmployeeDTO();
        request.setId(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(new Employee()));
        when(employeeService.update(request)).thenReturn(new Employee());
        Employee updatedEmployee = employeeService.update(request);
        assertNotNull(updatedEmployee, "Updated employee should not be null");
        verify(employeeMapper, times(2)).update(any(EmployeeDTO.class), any(Employee.class));
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void findByIdTest() {
        String employeeId = "140c7291-5afc-49c1-a253-cb156737aa81";
        Employee employee = new Employee();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        Employee foundEmployee = employeeService.findById(employeeId);
        assertEquals(employee, foundEmployee);
    }

    @Test
    void findByNameTest() {
        String employeeName = "Sami";
        Employee employee = new Employee();
        when(employeeRepository.findByName(employeeName)).thenReturn(Optional.of(employee));
        Optional<Employee> foundEmployee = employeeService.findByName(employeeName);
        assertEquals(Optional.of(employee), foundEmployee);
    }

    @Test
    void deleteEmployeeTest() {
        String employeeId = "140c7291-5afc-49c1-a253-cb156737aa81";
        Employee employee = new Employee();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        employeeService.delete(employeeId);
        verify(employeeRepository, times(1)).delete(employee);
    }
}
