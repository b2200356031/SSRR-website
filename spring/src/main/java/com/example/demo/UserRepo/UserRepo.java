package com.example.demo.UserRepo;

import com.example.demo.Entity.Users;
//import com.example.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    Users findUserById(Long id);
    //Users findById(Long id);
}

