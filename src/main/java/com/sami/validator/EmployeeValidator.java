package com.sami.validator;

import com.sami.dto.EmployeeDTO;
import com.sami.entity.Employee;
import com.sami.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

import static com.sami.utils.AppConstants.ALREADY_EXIST;

@Component
@RequiredArgsConstructor
public class EmployeeValidator implements Validator {

    private final EmployeeService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return EmployeeDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmployeeDTO request = (EmployeeDTO) target;
        Optional<Employee> employee = accountService.findByName(request.getName());
        if (employee.isPresent()) {
            errors.rejectValue("name", null, ALREADY_EXIST);
        }
    }
}
