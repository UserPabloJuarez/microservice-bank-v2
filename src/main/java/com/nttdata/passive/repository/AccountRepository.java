package com.nttdata.passive.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.passive.model.Account;

public interface AccountRepository extends ReactiveMongoRepository<Account, String>{
}
