package com.sami.service;

import com.sami.dto.EmployeeDTO;
import com.sami.entity.Employee;
import com.sami.exception.ResourceNotFoundException;
import com.sami.mapper.EmployeeMapper;
import com.sami.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    @Transactional
    public Employee save(EmployeeDTO request) {
        Employee employee = employeeMapper.save(request);
        employeeRepository.save(employee);
        return employee;
    }

    @Transactional
    public Employee update(EmployeeDTO request) {
        Employee employee = employeeRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Employee not found with id: " + request.getId())
        );
        employeeMapper.update(request, employee);
        return employeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    public Employee findById(String id) {
        return employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee not found with id: " + id)
        );
    }

    @Transactional(readOnly = true)
    public Optional<Employee> findByName(String name) {
        return employeeRepository.findByName(name);
    }

    @Transactional
    public void delete(String id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("employee not found with id: " + id)
        );
        employeeRepository.delete(employee);
    }
}
