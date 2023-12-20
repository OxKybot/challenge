package com.glady.challenge.repository;

import com.glady.challenge.model.entities.Company;
import com.glady.challenge.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByCompanyAndUserName(Company company, String userName);
}
