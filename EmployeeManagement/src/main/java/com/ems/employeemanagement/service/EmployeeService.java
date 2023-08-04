package com.ems.employeemanagement.service;

import com.ems.employeemanagement.dto.EmployeeRequest;
import com.ems.employeemanagement.exception.ValidationException;
import com.ems.employeemanagement.model.Employee;

import java.util.List;

public interface EmployeeService {

    /**
     * Function to create employee
     * @param employeeRequest
     * @param traceId
     * @return
     */
    Employee saveEmployee(EmployeeRequest employeeRequest, String traceId) throws ValidationException;

    /**
     * Function to list all employees
     * @param traceId
     * @return
     */
    List<Employee> getAllEmployee(String traceId) throws ValidationException;

    /**
     * Function to get particular employee by Id
     * @param id
     * @param traceId
     * @return
     */
    Employee getEmployeeBtId(int id,String traceId) throws ValidationException;

    /**
     * Function to update employee details
     * @param employee
     * @param id
     * @param traceId
     * @return
     */
    Employee updateEmployee(Employee employee,int id,String traceId) throws ValidationException;

    /**
     * Function to delete employee
     * @param id
     * @param traceId
     */
    void deleteEmployee(int id,String traceId) throws ValidationException;
}
