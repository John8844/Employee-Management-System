package com.ems.employeemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class EmployeeRequest {

    @NotNull(message = "Name should not be null.")
    private String name;

    @NotNull(message = "Role should not be null.")
    private String role;

    @Min(value = 5000,message = "salary must between 5000 t0 50000")
    @Max(value = 50000,message = "salary must between 5000 t0 50000")
    private int salary;
}
