package com.glady.challenge.repository;

import com.glady.challenge.model.entities.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, String> {
}
