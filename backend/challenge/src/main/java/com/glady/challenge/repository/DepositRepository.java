package com.glady.challenge.repository;

import com.glady.challenge.model.entities.Deposit;
import org.springframework.data.repository.CrudRepository;

public interface DepositRepository extends CrudRepository<Deposit, Long> {
}
