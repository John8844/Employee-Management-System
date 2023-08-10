package com.ems.employeemanagement.controller;

import com.ems.employeemanagement.advice.AccessDeniedException;
import com.ems.employeemanagement.dto.EmployeeRequest;
import com.ems.employeemanagement.exception.ValidationException;
import com.ems.employeemanagement.model.Employee;
import com.ems.employeemanagement.model.response.ResponseMessage;
import com.ems.employeemanagement.model.response.ResponseStatus;
import com.ems.employeemanagement.service.EmployeeService;
import com.ems.employeemanagement.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
     * @param employeeRequest
     * @return success message
     */

    @PostMapping
    public ResponseEntity<?> saveEmployee(@RequestBody @Valid EmployeeRequest employeeRequest) {
        String traceId = Util.getTrackingId();
        logger.info("{}: Post Request to create Employee, Employee: {}",traceId,employeeRequest.getName());
        try {
            employeeService.saveEmployee(employeeRequest, traceId);
            return new ResponseEntity<>(new ResponseMessage(201,ResponseStatus.Successful,"Employee Created Successfully..."),HttpStatus.CREATED);
        }catch (ValidationException e) {
            return new ResponseEntity<>(new ResponseMessage(403, ResponseStatus.Failure,e.getExceptionMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(new ResponseMessage(500, ResponseStatus.Failure,"Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * REST API to retrieve all Employee
     * @return list of employees
     */

    @GetMapping
    public ResponseEntity<?> getAllEmployee(){
        try {
            String traceId = Util.getTrackingId();
            logger.info("{}: Get Request to get all Employees.",traceId);
            List<Employee> employees=employeeService.getAllEmployee(traceId);
            return new ResponseEntity<>(employees,HttpStatus.OK);
        }catch (ValidationException e){
            String errorMessage = e.getExceptionMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }

    /**
     * REST API to retrieve particular employee by using Id
     * @param empId
     * @return employee object
     */

    @GetMapping("{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") int empId){
        try{
            String traceId = Util.getTrackingId();
            logger.info("{}: Get Request to get Employee By Id",traceId);
            Employee employee=employeeService.getEmployeeBtId(empId,traceId);
            return new ResponseEntity<>(employee,HttpStatus.OK);
        }catch (ValidationException e){
            String errorMessage = e.getExceptionMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    /**
     * REST API to update employee details
     * @param id
     * @param employee
     * @return success message
     */

    @PutMapping("{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable("id") int id,@RequestBody Employee employee) {
        try {
            String traceId = Util.getTrackingId();
            logger.info("{}: Put Request to update Employee By Id",traceId);
            Employee updatedEmployee=employeeService.updateEmployee(employee,id,traceId);
            return new ResponseEntity<>(updatedEmployee,HttpStatus.OK);
        }catch (ValidationException e){
            String errorMessage=e.getExceptionMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    /**
     * REST API to delete employee
     * @param id
     * @return success message
     */

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") int id) throws ValidationException{
        try{
            String traceId = Util.getTrackingId();
            logger.info("{}: Delete Request to delete Employee By Id",traceId);
            employeeService.deleteEmployee(id,traceId);
            return new ResponseEntity<String>("Employee deleted successfully...",HttpStatus.OK);
        }catch (ValidationException e){
            String errorMessage=e.getExceptionMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}
