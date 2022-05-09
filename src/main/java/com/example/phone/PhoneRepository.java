package com.example.phone;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    //User findByUsername(String username);
}
