package com.nttdata.passive.implService;

import org.springframework.beans.factory.annotation.Autowired;

import com.nttdata.passive.model.AccountType;
import com.nttdata.passive.repository.AccountTypeRepository;
import com.nttdata.passive.service.AccountTypeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class AccountTypeImpl implements AccountTypeService {

	@Autowired
    private AccountTypeRepository accountTypeRepository;

	@Override
	public Mono<AccountType> findById(String id) {
		return null;
	}

	@Override
	public Mono<AccountType> save(AccountType document) {
		return accountTypeRepository.save(document);
	}

	@Override
	public Mono<Void> delete(AccountType document) {
		return null;
	}

	@Override
	public Flux<AccountType> findAll() {
		return accountTypeRepository.findAll();
	}
	
}
