package com.sami.dto;

import com.sami.enums.EmployeeType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EmployeeDTO {

    private String id;

    @Size(min = 10, max = 50)
    private String name;

    private int age;

    @NotNull
    private EmployeeType employeeType;
}
