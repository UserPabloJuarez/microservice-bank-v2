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

import com.nttdata.passive.dto.CreditCardNaturalPersonListOperationResp;
import com.nttdata.passive.dto.CreditNaturalPersonListOperationResp;
import com.nttdata.passive.dto.OperationPersonNaturalCreditCardReq;
import com.nttdata.passive.dto.OperationPersonNaturalCreditReq;
import com.nttdata.passive.dto.ProductNaturalPersonListCreditResp;
import com.nttdata.passive.dto.ProductNaturalPersonalCreditCardRegisterReq;
import com.nttdata.passive.dto.ProductNaturalPersonalCreditRegisterReq;
import com.nttdata.passive.service.ProductNaturalPersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/creditPersonNatural")
public class CreditPersonNaturalController {

	 @Autowired
	 ProductNaturalPersonService productNaturalPersonService;

	    @GetMapping("/credit/{id}")
		public Mono<ResponseEntity<Flux<ProductNaturalPersonListCreditResp>>> getCreditsByIdClient(@PathVariable String id){
			return Mono.just(
					ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(productNaturalPersonService.getCredit(id))
					);
		}

	    @PostMapping("/credit")
		public Mono<ResponseEntity<Map<String, Object>>> createCredit(@RequestBody ProductNaturalPersonalCreditRegisterReq entity){
			
			Map<String, Object> respuesta = new HashMap<String, Object>();
			
	        return productNaturalPersonService.saveCredit(entity)
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
		public Mono<ResponseEntity<Map<String, Object>>> regOperationNaturalPerson(@RequestBody OperationPersonNaturalCreditReq entity){
			
			Map<String, Object> respuesta = new HashMap<String, Object>();
			
	        return productNaturalPersonService.regOperationCredit(entity)
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

		@GetMapping("/operations/credit/{id}")
		public Mono<ResponseEntity<Mono<CreditNaturalPersonListOperationResp>>> getOperationByIdAccount(@PathVariable String id){
			return Mono.just(
					ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(productNaturalPersonService.getOperationCreditByIdProduct(id))
					);
		}

	    @GetMapping("/creditCard/{id}")
		public Mono<ResponseEntity<Flux<ProductNaturalPersonListCreditResp>>> getCreditsCardByIdClient(@PathVariable String id){
			return Mono.just(
					ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(productNaturalPersonService.getCredit(id))
					);
		}

	    @PostMapping("/creditCard")
		public Mono<ResponseEntity<Map<String, Object>>> createCreditCard(@RequestBody ProductNaturalPersonalCreditCardRegisterReq entity){
			
			Map<String, Object> respuesta = new HashMap<String, Object>();
			
	        return productNaturalPersonService.saveCreditCard(entity)
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
		public Mono<ResponseEntity<Map<String, Object>>> regOperationCreditCardNaturalPerson(@RequestBody OperationPersonNaturalCreditCardReq entity){
			
			Map<String, Object> respuesta = new HashMap<String, Object>();
			
	        return productNaturalPersonService.regOperationCreditCard(entity)
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
		public Mono<ResponseEntity<Mono<CreditCardNaturalPersonListOperationResp>>> getOperationCreditCardByIdAccount(@PathVariable String id){
			return Mono.just(
					ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(productNaturalPersonService.getOperationCreditByIdProductCard(id))
					);
		}
	
}
