package com.sami.controller;

import com.sami.dto.EmployeeDTO;
import com.sami.entity.Employee;
import com.sami.mapper.EmployeeMapper;
import com.sami.service.EmployeeService;
import com.sami.validator.EmployeeValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import static com.sami.exception.ApiError.fieldError;
import static com.sami.utils.ResponseBuilder.error;
import static com.sami.utils.ResponseBuilder.success;
import static com.sami.utils.StringUtils.toJson;
import static org.springframework.http.ResponseEntity.badRequest;

@RestController
@RequiredArgsConstructor
@Tag(name = "Employee API")
@RequestMapping(path = "employee")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    private final EmployeeValidator employeeValidator;

    private final EmployeeMapper employeeMapper;

    @PostMapping
    @Operation(summary = "save employee")
    public ResponseEntity<JSONObject> save(@Valid @RequestBody EmployeeDTO request, BindingResult bindingResult) {
        ValidationUtils.invokeValidator(employeeValidator, request, bindingResult);

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }
        Employee employee = employeeService.save(request);
        LOGGER.info("employee save response: {} ", toJson(employee));
        EmployeeDTO dto = employeeMapper.select(employee);
        return new ResponseEntity<>(success(dto).getJson(), HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "update employee")
    public ResponseEntity<JSONObject> update(@Valid @RequestBody EmployeeDTO request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }
        Employee employee = employeeService.update(request);
        LOGGER.info("employee update response: {} ", toJson(employee));
        EmployeeDTO dto = employeeMapper.select(employee);
        return new ResponseEntity<>(success(dto).getJson(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get employee by id")
    public ResponseEntity<JSONObject> findById(@PathVariable("id") String id) {
        Employee employee = employeeService.findById(id);
        LOGGER.info("get employee by id: {}, response: {} ", id, toJson(employee));
        EmployeeDTO dto = employeeMapper.select(employee);
        return new ResponseEntity<>(success(dto).getJson(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete employee by id")
    public ResponseEntity<JSONObject> remove(@PathVariable(value = "id") String id) {
        employeeService.delete(id);
        LOGGER.info("delete employee by id response: {} ", toJson(id));
        return new ResponseEntity<>(success("deleted employee with id: " + id).getJson(), HttpStatus.OK);
    }

}
