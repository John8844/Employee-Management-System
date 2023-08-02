package com.ems.employeemanagement.service.impl;

import com.ems.employeemanagement.exception.ResourceNotFoundException;
import com.ems.employeemanagement.model.Employee;
import com.ems.employeemanagement.repository.EmployeeRepository;
import com.ems.employeemanagement.service.EmployeeService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public Employee saveEmployee(Employee employee,String traceId) {
        logger.info("{}: Function start: EmployeeServiceImpl.saveEmployee()",traceId);
        Employee newEmployee = employeeRepository.save(employee);
        logger.info("{}: Function end: EmployeeServiceImpl.saveEmployee()",traceId);
        return newEmployee;
    }

    @Override
    public List<Employee> getAllEmployee(String traceId) {
        logger.info("{}: Function start: EmployeeServiceImpl.getAllEmployees()",traceId);
        List<Employee> employees=employeeRepository.findAll();
        logger.info("{}: Function end: EmployeeServiceImpl.getAllEmployees()",traceId);
        return employees;
    }

    @Override
    public Employee getEmployeeBtId(int id,String traceId) {
        logger.info("{}: Function start: EmployeeServiceImpl.getEmployeeById()",traceId);
        Optional<Employee> employee=employeeRepository.findById(id);
        if(employee.isPresent()){
            logger.info("{}: Function end: EmployeeServiceImpl.getEmployeeById()",traceId);
            return employee.get();
        }else {
            throw new ResourceNotFoundException("Employee","Id",id);
        }

    }

    @Override
    public Employee updateEmployee(Employee employee, int id, String traceId) {
        logger.info("{}: Function start: EmployeeServiceImpl.updateEmployee()",traceId);
        Employee oldEmployee = employeeRepository.findById(id).orElseThrow( ()->new ResourceNotFoundException("Employee", "Id", id));

        oldEmployee.setName(employee.getName());
        oldEmployee.setRole(employee.getRole());
        oldEmployee.setSalary(employee.getSalary());

        employeeRepository.save(oldEmployee);
        logger.info("{}: Function end: EmployeeServiceImpl.updateEmployee()",traceId);
        return oldEmployee;
    }

    @Override
    public void deleteEmployee(int id,String traceId) {
        logger.info("{}: Function start: EmployeeServiceImpl.deleteEmployee()",traceId);
        employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee","Id",id));
        employeeRepository.deleteById(id);
        logger.info("{}: Function end: EmployeeServiceImpl.deleteEmployee()",traceId);
    }
}
