package com.sami.mapper;

import com.sami.dto.EmployeeDTO;
import com.sami.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapping {

    EmployeeMapping INSTANCE = Mappers.getMapper(EmployeeMapping.class);

    Employee save(EmployeeDTO dto);

    void update(EmployeeDTO dto, @MappingTarget Employee employee);

    EmployeeDTO select(Employee employee);
}
