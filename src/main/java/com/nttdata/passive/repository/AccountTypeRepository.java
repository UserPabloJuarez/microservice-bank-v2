package com.nttdata.passive.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.passive.model.AccountType;

public interface AccountTypeRepository extends ReactiveMongoRepository<AccountType, String>{
}
