package com.ems.employeemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class EmployeeRequest {

    @NotNull(message = "Name should not be null.")
    private String name;

    @NotNull(message = "Role should not be null.")
    private String role;
    private int salary;
}
