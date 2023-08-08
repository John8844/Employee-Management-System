package com.ems.employeemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class EmployeeRequest {

    @NotNull(message = "Name should not be null.")
    private String name;

    @NotNull(message = "Role should not be null.")
    private String role;
    private int salary;
}
