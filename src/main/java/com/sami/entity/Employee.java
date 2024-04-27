package com.sami.entity;

import com.sami.enums.EmployeeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@ToString
@DynamicUpdate
public class Employee {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String id;

    @Size(max = 50)
    private String name;

    private int age;

    @Column(length = 12)
    @Enumerated(EnumType.STRING)
    private EmployeeType employeeType;
}
