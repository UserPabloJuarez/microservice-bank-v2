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

import com.nttdata.passive.dto.AccountNaturalPersonListOperationResp;
import com.nttdata.passive.dto.AccountNaturalPersonListResp;
import com.nttdata.passive.dto.AccountSaveRegisterReq;
import com.nttdata.passive.dto.OperationPersonNaturalAccountReq;
import com.nttdata.passive.service.AccountNaturalPersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/account")
public class AccountController {

	//private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountNaturalPersonService accountNaturalPersonService;

    @GetMapping("/account/{id}")
	public Mono<ResponseEntity<Flux<AccountNaturalPersonListResp>>> getAccounSaveByIdClient(@PathVariable String id){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(accountNaturalPersonService.findByIdCustomer(id))
				);
	}
    
    
    @PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> createAccountNaturalPerson(@RequestBody AccountSaveRegisterReq entity){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
        return accountNaturalPersonService.save(entity)
                .map( p -> {
					
                    respuesta.put("resultado", p.isResult());
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
	public Mono<ResponseEntity<Map<String, Object>>> regOperationNaturalPerson(@RequestBody OperationPersonNaturalAccountReq entity){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
        return accountNaturalPersonService.regOperation(entity)
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
	public Mono<ResponseEntity<Mono<AccountNaturalPersonListOperationResp>>> getOperationByIdAccount(@PathVariable String id){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(accountNaturalPersonService.getOperationByIdAccount(id))
				);
	}
	
}
