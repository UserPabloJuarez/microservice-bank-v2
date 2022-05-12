package com.nttdata.passive.implService;

import org.springframework.beans.factory.annotation.Autowired;

import com.nttdata.passive.model.State;
import com.nttdata.passive.repository.StateRepository;
import com.nttdata.passive.service.StateService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class StateImpl implements StateService {

	@Autowired
    StateRepository stateRepository;

	@Override
	public Mono<State> findById(String id) {
		return stateRepository.findById(id);
	}

	@Override
	public Mono<State> save(State document) {
		return stateRepository.save(document);
	}

	@Override
	public Mono<Void> delete(State document) {
		return null;
	}

	@Override
	public Flux<State> findAll() {
		return stateRepository.findAll();
	}

	
}
