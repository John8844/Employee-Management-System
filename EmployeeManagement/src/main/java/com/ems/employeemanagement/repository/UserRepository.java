package com.ems.employeemanagement.repository;

import com.ems.employeemanagement.model.Employee;
import com.ems.employeemanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByName(String userName);
}
