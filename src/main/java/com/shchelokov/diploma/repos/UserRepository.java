package com.shchelokov.diploma.repos;

import com.shchelokov.diploma.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}