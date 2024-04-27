package com.sami.mapper;

import com.sami.dto.EmployeeDTO;
import com.sami.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee save(EmployeeDTO dto) {
        return EmployeeMapping.INSTANCE.save(dto);
    }

    public void update(EmployeeDTO request, Employee employee) {
        EmployeeMapping.INSTANCE.update(request, employee);
    }

    public EmployeeDTO select(Employee employee) {
        return EmployeeMapping.INSTANCE.select(employee);
    }
}
