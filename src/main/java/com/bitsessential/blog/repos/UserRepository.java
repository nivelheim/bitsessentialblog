package com.bitsessential.blog.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitsessential.blog.entities.ApplicationUser;

public interface UserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUserName(String userName);
}
