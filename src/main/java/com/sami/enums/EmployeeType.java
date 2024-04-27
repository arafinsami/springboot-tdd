package com.sami.enums;

import lombok.Getter;

@Getter
public enum EmployeeType {
    PERMANENT("Permanent"),
    TEMPORARY("Temporary");

    private final String label;

    EmployeeType(String label) {
        this.label = label;
    }
}
