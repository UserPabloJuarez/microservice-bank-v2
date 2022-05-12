package com.nttdata.passive.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.nttdata.passive.model.TypeProduct;

public interface ProductTypeRepository extends ReactiveMongoRepository<TypeProduct, String>{
}
