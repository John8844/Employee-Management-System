package com.ems.employeemanagement.service.impl;

import com.ems.employeemanagement.dto.EmployeeRequest;
import com.ems.employeemanagement.exception.ResourceNotFoundException;
import com.ems.employeemanagement.exception.ValidationException;
import com.ems.employeemanagement.model.Employee;
import com.ems.employeemanagement.repository.EmployeeRepository;
import com.ems.employeemanagement.service.EmployeeService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public Employee saveEmployee(EmployeeRequest employeeRequest, String traceId) throws ValidationException {
        Employee employeeInDB = fetchEmployee(employeeRequest.getName());
        logger.info("{}: Function start: EmployeeServiceImpl.saveEmployee()",traceId);
        if (employeeInDB!=null) throw new ValidationException("Employee already Exist..");
        if (employeeRequest.getSalary()<5000) throw new ValidationException("Employee Salary Must Greater than 5000...");
        Employee employee= new Employee(employeeRequest.getName(),employeeRequest.getRole(),employeeRequest.getSalary());
        Employee newEmployee = employeeRepository.save(employee);
        logger.info("{}: Function end: EmployeeServiceImpl.saveEmployee()",traceId);
        return newEmployee;
    }

    @Override
    public List<Employee> getAllEmployee(String traceId) throws ValidationException {
        logger.info("{}: Function start: EmployeeServiceImpl.getAllEmployees()",traceId);
        List<Employee> employees=employeeRepository.findAll();
        logger.info("{}: Function end: EmployeeServiceImpl.getAllEmployees()",traceId);
        return employees;
    }

    @Override
    public Employee getEmployeeBtId(int id,String traceId) throws ValidationException{
        Employee employeeInDB=findbyid(id);
        logger.info("{}: Function start: EmployeeServiceImpl.getEmployeeById()",traceId);
        if (employeeInDB==null) throw new ValidationException("Employee doesn't exist...");
        Employee employee= employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee","Id",id));
        logger.info("{}: Function end: EmployeeServiceImpl.getEmployeeById()",traceId);
        return employee;

    }

    @Override
    public Employee updateEmployee(Employee employee, int id, String traceId) throws ValidationException{
        Employee employeeInDB=findbyid(id);
        logger.info("{}: Function start: EmployeeServiceImpl.updateEmployee()",traceId);
        if (employeeInDB==null) throw new ValidationException("You Can't Update. Because, Employee doesn't exist...");
        Employee oldEmployee = employeeInDB;

        oldEmployee.setName(employee.getName());
        oldEmployee.setRole(employee.getRole());
        oldEmployee.setSalary(employee.getSalary());

        employeeRepository.save(oldEmployee);
        logger.info("{}: Function end: EmployeeServiceImpl.updateEmployee()",traceId);
        return oldEmployee;
    }

    @Override
    public void deleteEmployee(int id,String traceId) throws ValidationException{
        Employee employeeInDB=findbyid(id);
        logger.info("{}: Function start: EmployeeServiceImpl.deleteEmployee()",traceId);
        if (employeeInDB==null) throw new ValidationException("You Can't Delete. Because, Employee doesn't exist...");
        employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee","Id",id));
        employeeRepository.deleteById(id);
        logger.info("{}: Function end: EmployeeServiceImpl.deleteEmployee()",traceId);
    }

    public Employee fetchEmployee(String empName){
        return employeeRepository.findEmployeeByName(empName);
    }
    public Employee findbyid(int id){
        return employeeRepository.findEmployeeById(id);
    }
}
