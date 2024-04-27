package com.sami.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sami.dto.EmployeeDTO;
import com.sami.entity.Employee;
import com.sami.mapper.EmployeeMapper;
import com.sami.service.EmployeeService;
import com.sami.validator.EmployeeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private EmployeeValidator employeeValidator;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(employeeController)
                .setValidator(employeeValidator)
                .build();
    }

    @Test
    public void testSave_Success() throws Exception {
        when(employeeValidator.supports(EmployeeDTO.class)).thenReturn(true);
        BindingResult bindingResult = mock(BindingResult.class);
        lenient().when(bindingResult.hasErrors()).thenReturn(false);
        when(employeeService.save(any(EmployeeDTO.class))).thenReturn(new Employee());
        when(employeeMapper.select(any(Employee.class))).thenReturn(new EmployeeDTO());
        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new EmployeeDTO())))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").exists())
                .andReturn();
        verify(employeeService, times(1)).save(new EmployeeDTO());
    }

    @Test
    public void testUpdate_Success() throws Exception {
        BindingResult bindingResult = mock(BindingResult.class);
        lenient().when(bindingResult.hasErrors()).thenReturn(false);
        when(employeeService.update(any(EmployeeDTO.class))).thenReturn(new Employee());
        when(employeeMapper.select(any(Employee.class))).thenReturn(new EmployeeDTO());
        mockMvc.perform(MockMvcRequestBuilders.put("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new EmployeeDTO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").exists())
                .andReturn();
        verify(employeeService, times(1)).update(new EmployeeDTO());
    }

    @Test
    public void testFindById() throws Exception {
        String employeeId = "140c7291-5afc-49c1-a253-cb156737aa81";
        when(employeeService.findById(employeeId)).thenReturn(new Employee());
        when(employeeMapper.select(new Employee())).thenReturn(new EmployeeDTO());
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        mockMvc.perform(get("/employee/{id}", employeeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testRemove_Success() throws Exception {
        String employeeId = "140c7291-5afc-49c1-a253-cb156737aa81";
        mockMvc.perform(delete("/employee/{id}", employeeId)
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").value("deleted employee with id: " + employeeId))
                .andReturn();
        verify(employeeService, times(1)).delete(employeeId);
    }
}
