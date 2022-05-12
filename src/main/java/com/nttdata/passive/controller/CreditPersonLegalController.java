package com.nttdata.passive.controller;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.passive.dto.CreditCardLegalPersonListOperationResp;
import com.nttdata.passive.dto.CreditNaturalBussinesListOperationResp;
import com.nttdata.passive.dto.OperationPersonLegalCreditCardReq;
import com.nttdata.passive.dto.OperationPersonLegalCreditReq;
import com.nttdata.passive.dto.ProductBussinesPersonCreditCardRegisterReq;
import com.nttdata.passive.dto.ProductBussinesPersonCreditRegisterReq;
import com.nttdata.passive.dto.ProductBussinesPersonListCreditCardResp;
import com.nttdata.passive.dto.ProductBussinesPersonListCreditResp;
import com.nttdata.passive.service.ProductLegalPersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/creditPersonLegal")
public class CreditPersonLegalController {
	
	@Autowired
	ProductLegalPersonService productLegalPersonService;

    @GetMapping("/credit/{id}")
	public Mono<ResponseEntity<Flux<ProductBussinesPersonListCreditResp>>> getCreditsByIdClient(@PathVariable String id){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(productLegalPersonService.getCredit(id))
				);
	}

    @PostMapping("/credit")
	public Mono<ResponseEntity<Map<String, Object>>> createCredit(@RequestBody ProductBussinesPersonCreditRegisterReq entity){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
        return productLegalPersonService.saveCredit(entity)
                .map( p -> {
					
                    respuesta.put("resultado", p.isResult());
                    respuesta.put("mensaje", p.getMessage());
                    respuesta.put("timestamp", new Date());
					
                    return ResponseEntity
					.created(URI.create("/api/productos/"))
					.contentType(MediaType.APPLICATION_JSON)
					.body(respuesta);
                });

	}

	@PostMapping("/operation/credit")
	public Mono<ResponseEntity<Map<String, Object>>> regOperationNaturalPerson(@RequestBody OperationPersonLegalCreditReq entity){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
        return productLegalPersonService.regOperationCredit(entity)
                .map( p -> {
                    respuesta.put("resultado", p.isResult());
                    respuesta.put("mensaje", p.getMessage());
                    respuesta.put("timestamp", new Date());
					
                    return ResponseEntity
					.created(URI.create("/api/operation"))
					.contentType(MediaType.APPLICATION_JSON)
					.body(respuesta);
                });

	}

	@GetMapping("/operations/credit/{id}")
	public Mono<ResponseEntity<Mono<CreditNaturalBussinesListOperationResp>>> getOperationByIdAccount(@PathVariable String id){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(productLegalPersonService.getOperationCreditByIdProduct(id))
				);
	}

	@GetMapping("/creditCard/{id}")
	public Mono<ResponseEntity<Flux<ProductBussinesPersonListCreditCardResp>>> getCreditsCardByIdClient(@PathVariable String id){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(productLegalPersonService.getCreditCard(id))
				);
	}

    @PostMapping("/creditCard")
	public Mono<ResponseEntity<Map<String, Object>>> createCreditCard(@RequestBody ProductBussinesPersonCreditCardRegisterReq entity){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
        return productLegalPersonService.saveCreditCard(entity)
                .map( p -> {
					
                    respuesta.put("resultado", p.isResult());
                    respuesta.put("mensaje", p.getMessage());
                    respuesta.put("timestamp", new Date());
					
                    return ResponseEntity
					.created(URI.create("/api/productos/"))
					.contentType(MediaType.APPLICATION_JSON)
					.body(respuesta);
                });

	}

	@PostMapping("/operation/creditCard")
	public Mono<ResponseEntity<Map<String, Object>>> regOperationCreditCardNaturalPerson(@RequestBody OperationPersonLegalCreditCardReq entity){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
        return productLegalPersonService.regOperationCreditCard(entity)
                .map( p -> {
                    respuesta.put("resultado", p.isResult());//p.getEntidad().getAccountNumber());
                    respuesta.put("mensaje", p.getMessage());
                    respuesta.put("timestamp", new Date());
					
                    return ResponseEntity
					.created(URI.create("/api/operation"))
					.contentType(MediaType.APPLICATION_JSON)
					.body(respuesta);
                });

	}

	@GetMapping("/operations/creditCard/{id}")
	public Mono<ResponseEntity<Mono<CreditCardLegalPersonListOperationResp>>> getOperationCreditCardByIdAccount(@PathVariable String id){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(productLegalPersonService.getOperationCreditByIdProductCard(id))
				);
	}
	
}
