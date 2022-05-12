package com.nttdata.passive.implService;

import org.springframework.beans.factory.annotation.Autowired;

import com.nttdata.passive.model.TypeProduct;
import com.nttdata.passive.repository.ProductTypeRepository;
import com.nttdata.passive.service.TypeProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ProductTypeImpl implements TypeProductService{

	@Autowired
    private ProductTypeRepository productTypeRepository;

	@Override
	public Mono<TypeProduct> findById(String id) {
		return null;
	}

	@Override
	public Mono<TypeProduct> save(TypeProduct document) {
		return productTypeRepository.save(document);
	}

	@Override
	public Mono<Void> delete(TypeProduct document) {
		return null;
	}

	@Override
	public Flux<TypeProduct> findAll() {
		return productTypeRepository.findAll();
	}
	
}
