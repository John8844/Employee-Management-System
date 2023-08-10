package com.ems.employeemanagement.repository;

import com.ems.employeemanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByName(String userName);

    //User findUserByEmailIgnoreCaseAndPassword(String email, String password);

    User findUserByEmail(String email);
}
