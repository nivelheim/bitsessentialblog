package com.bitsessential.blog.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bitsessential.blog.entities.ApplicationUser;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUserName(String userName);
}
