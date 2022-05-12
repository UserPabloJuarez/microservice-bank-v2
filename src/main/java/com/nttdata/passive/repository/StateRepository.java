package com.nttdata.passive.repository;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.passive.model.State;

public interface StateRepository extends ReactiveMongoRepository<State, String>{
}
