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

import com.nttdata.passive.dto.AccountBussinesReq;
import com.nttdata.passive.dto.AccountLegalPersonListOperationResp;
import com.nttdata.passive.dto.AccountLegalPersonListResp;
import com.nttdata.passive.dto.OperationPersonLegalAccountReq;
import com.nttdata.passive.service.AccountLegalPersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/accountPersonLegal")
public class AccountPersonLegalController {

	 @Autowired
	 AccountLegalPersonService accountLegalPersonService;
	 
	 @GetMapping("/account/{id}")
     public Mono<ResponseEntity<Flux<AccountLegalPersonListResp>>> getAccounSaveByIdCustomer(@PathVariable String id){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(accountLegalPersonService.finfindByIdCustomer(id))
				);
	}
	 
	@PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> createAccountPersonLegal(@RequestBody AccountBussinesReq entity){
			
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
	       return accountLegalPersonService.save(entity)
	               .map( p -> {
					
	                   respuesta.put("resultado", p.isResult());//p.getEntidad().getAccountNumber());
	                   respuesta.put("mensaje", p.getMessage());
					
					if(p.isResult()) {
						respuesta.put("cuenta", p.getEntidad().getAccountNumber());
					}
	                    respuesta.put("timestamp", new Date());
						
                    return ResponseEntity
						
						.created(URI.create("/api/productos/"))
						.contentType(MediaType.APPLICATION_JSON)
						.body(respuesta);
	                });

		}
	

	@PostMapping("/operation")
	public Mono<ResponseEntity<Map<String, Object>>> regOperationPersonNatural(@RequestBody OperationPersonLegalAccountReq entity){
			
			Map<String, Object> respuesta = new HashMap<String, Object>();
			
	        return accountLegalPersonService.opRegister(entity)
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

		@GetMapping("/operations/{id}")
		public Mono<ResponseEntity<Mono<AccountLegalPersonListOperationResp>>> getOperationByIdAccount(@PathVariable String id){

			return Mono.just(
					ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(accountLegalPersonService.getOperationByIdAccount(id))
					);
		}
	
}
