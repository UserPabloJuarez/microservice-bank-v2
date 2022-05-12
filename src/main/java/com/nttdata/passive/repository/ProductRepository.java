package com.nttdata.passive.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.passive.model.Product;

public interface ProductRepository extends ReactiveMongoRepository<Product, String>{
}
