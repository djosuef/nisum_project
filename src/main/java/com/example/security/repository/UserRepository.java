package com.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.security.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query(nativeQuery = false,value = "Select u FROM User u WHERE u.email = ?1")
       User findByEmailUser(String email);
}
