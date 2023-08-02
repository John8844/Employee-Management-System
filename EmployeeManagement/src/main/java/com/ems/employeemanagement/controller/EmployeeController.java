package com.ems.employeemanagement.controller;

import com.ems.employeemanagement.model.Employee;
import com.ems.employeemanagement.service.EmployeeService;
import com.ems.employeemanagement.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * REST API to create Employee
     * @param employee
     * @return success message
     */

    @PostMapping
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee){
        String traceId = Util.getTrackingId();
        logger.info("{}: Post Request to create Employee, Employee: {}",traceId,employee.getName());
        Employee savedEmployee =  employeeService.saveEmployee(employee,traceId);
        return new ResponseEntity<Employee>(savedEmployee, HttpStatus.CREATED);
    }

    /**
     * REST API to retrieve all Employee
     * @return list of employees
     */

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployee(){
        String traceId = Util.getTrackingId();
        logger.info("{}: Get Request to get all Employees.",traceId);
        List<Employee> employees=employeeService.getAllEmployee(traceId);
        return new ResponseEntity<>(employees,HttpStatus.OK);
    }

    /**
     * REST API to retrieve particular employee by using Id
     * @param empId
     * @return employee object
     */

    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") int empId){
        String traceId = Util.getTrackingId();
        logger.info("{}: Get Request to get Employee By Id",traceId);
        Employee employee=employeeService.getEmployeeBtId(empId,traceId);
        return new ResponseEntity<>(employee,HttpStatus.OK);
    }

    /**
     * REST API to update employee details
     * @param id
     * @param employee
     * @return success message
     */

    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") int id,@RequestBody Employee employee){
        String traceId = Util.getTrackingId();
        logger.info("{}: Put Request to update Employee By Id",traceId);
        Employee updatedEmployee=employeeService.updateEmployee(employee,id,traceId);
        return new ResponseEntity<Employee>(updatedEmployee,HttpStatus.OK);
    }

    /**
     * REST API to delete employee
     * @param id
     * @return success message
     */

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") int id){
        String traceId = Util.getTrackingId();
        logger.info("{}: Delete Request to delete Employee By Id",traceId);
        employeeService.deleteEmployee(id,traceId);
        return new ResponseEntity<String>("Employee deleted successfully...",HttpStatus.OK);
    }
}
