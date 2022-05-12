package com.nttdata.passive.implService;

import java.util.ArrayList;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nttdata.passive.abstraction.OperationsPersonLegal;
import com.nttdata.passive.abstraction.OperationsPersonLegalCurrent;
import com.nttdata.passive.dto.AccountBussinesReq;
import com.nttdata.passive.dto.AccountLegalPersonListOperationResp;
import com.nttdata.passive.dto.AccountLegalPersonListResp;
import com.nttdata.passive.dto.EntityDto;
import com.nttdata.passive.dto.OperationPersonLegalAccountReq;
import com.nttdata.passive.helpers.AccountTypeEnum;
import com.nttdata.passive.helpers.GenericFunction;
import com.nttdata.passive.model.Account;
import com.nttdata.passive.model.Operation;
import com.nttdata.passive.repository.AccountRepository;
import com.nttdata.passive.repository.AccountTypeRepository;
import com.nttdata.passive.service.AccountLegalPersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class AccountPersonLegalImpl implements AccountLegalPersonService {

private static final Logger log = LoggerFactory.getLogger(AccountPersonLegalImpl.class);
	
	@Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountTypeRepository accountTypeRepository;
	
	@Override
	public Mono<EntityDto<AccountBussinesReq>> save(AccountBussinesReq entity) {
log.info("Entro al metodo");
		
		return Mono.just(entity)
                .map(retorno -> new EntityDto<AccountBussinesReq>(true, "Cuenta creada", entity))
                .flatMap(obj -> {

                        log.info("Inicia proceso");

                        if (entity.getAccountType().getId()
                                        .equals(AccountTypeEnum.ACCOUNT_SAVE.toString())) {
                                return Mono.just(
                                                new EntityDto<AccountBussinesReq>(
                                                                false,
                                                                "Las personas empresariales no pueden crear cuentas de ahorro.",
                                                                null));

                        } else if (entity.getAccountType().getId()
                                        .equals(AccountTypeEnum.ACCOUNT_FIXED.toString())) {
                                return Mono.just(
                                                new EntityDto<AccountBussinesReq>(
                                                                false,
                                                                "Las personas empresariales no pueden crear cuentas de plazo fijo.",
                                                                null));

                        } else {

                                log.info("Paso validacion");

                                String accountRandom = UUID.randomUUID().toString().replace("-", "");
                                Account account = new Account(null,
                                                accountRandom,
                                                entity.getBalance(),
                                                entity.getAccountType().getId(),
                                                entity.getState().getId(),
                                                new ArrayList<String>(entity.getIdCustomers()),
                                                entity.getAccountBusiness(),
                                                new ArrayList<Operation>(),
                                                entity.getAccountType(),
                                                entity.getState(),
                                                GenericFunction.generateCard());

                                obj.getEntidad().setAccountNumber(
                                                accountRandom);

                                return accountRepository
                                                .insert(account)
                                                .flatMap(nuevo -> {

                                                        return Mono.just(
                                                                        new EntityDto<AccountBussinesReq>(
                                                                                        true,
                                                                                        "Cuenta creada",
                                                                                        new AccountBussinesReq(
                                                                                                        nuevo.getAccountNumber(),
                                                                                                        nuevo.getBalance(),
                                                                                                        entity.getIdCustomers(),
                                                                                                        entity.getAccountType(),
                                                                                                        entity.getState(),
                                                                                                        entity.getAccountBusiness(),
                                                                                                        entity.getOperations())));
                                                });
                        }
                });
	}

	@Override
	public Flux<AccountLegalPersonListResp> finfindByIdCustomer(String id) {
		return accountRepository.findAll()
                .filter(p -> p.getIdCustomers().contains(id))
                .map(mapper -> new AccountLegalPersonListResp(mapper.getId(),
                                mapper.getAccountNumber(),
                                mapper.getBalance(),
                                id,
                                mapper.getAccountType().getDescription(),
                                mapper.getState().getDescription(),
                                mapper.getAccountBusiness()));
	}

	@Override
	public Mono<EntityDto<OperationPersonLegalAccountReq>> opRegister(OperationPersonLegalAccountReq entity) {
		log.info("Entro al metodo Reg. Operation");

		   return Mono.just(entity).map(retorno -> new EntityDto<OperationPersonLegalAccountReq>(true,"Operacion con exito", retorno))
		         .flatMap(obj -> {log.info("Inicia validacion 1");
		   return accountRepository.findById(entity.getIdAccount())
		          .flatMap(p -> {log.info("Inicia validacion 2");
		   return accountTypeRepository.findById(p.getIdAccountType()).flatMap(at -> {log.info("Inicia validacion 3");
		         if (entity.getTypeProduct().getId().equals("DEP")) {
		            log.info("Inicia validacion 4");
		         if (p.getIdAccountType().equals(AccountTypeEnum.ACCOUNT_CURRENT.toString())) {
		            OperationsPersonLegal operationsPersonLegal = new OperationsPersonLegalCurrent();
		            var accountDto = operationsPersonLegal.Dep(entity, p, at);
		        
		         if (accountDto.isResult()){
		        	 accountDto.getEntidad().setBalance(accountDto.getEntidad().getBalance() + entity.getValue());
		   return accountRepository.save(accountDto.getEntidad()).flatMap(na -> {
		    return Mono.just(new EntityDto<OperationPersonLegalAccountReq>(true,"Operación registrada", null));
		    });
		         } else
		        	 return Mono.just(new EntityDto<OperationPersonLegalAccountReq>(false, accountDto.getMessage(), null));
		           }
		       } else if (entity.getTypeProduct().getId().equals("WIT")) {
		        log.info("Inicia validacion 4");
		           if (p.getIdAccountType().equals(AccountTypeEnum.ACCOUNT_CURRENT.toString())) {
		        	   OperationsPersonLegal operationsPersonBussines = new OperationsPersonLegalCurrent();
		           var accountDto = operationsPersonBussines.Wit(entity, p, at);

		           if (accountDto.isResult()) {
		           accountDto.getEntidad().setBalance(accountDto.getEntidad().getBalance() + entity.getValue());
		            return accountRepository.save(accountDto.getEntidad()).flatMap(na -> {
		             return Mono.just( new EntityDto<OperationPersonLegalAccountReq>(true,
		                    "Operación registrada", null));
		           });
		          } else
		             return Mono.just(new EntityDto<OperationPersonLegalAccountReq>(false,
		                    accountDto.getMessage(), null));
		       }
		   } else if (entity.getTypeProduct().getId().equals("PAY")) {
		     log.info("Inicia validacion 4");
		          if (p.getIdAccountType().equals(AccountTypeEnum.ACCOUNT_CURRENT.toString())) {
		          OperationsPersonLegal operationsPersonBussines = new OperationsPersonLegalCurrent();
		          var accountDto = operationsPersonBussines.Pay(entity, p, at);
		     if (accountDto.isResult()) {
		         accountDto.getEntidad()
		         .setBalance(accountDto
		         .getEntidad()
		         .getBalance() + entity.getValue());
		       return accountRepository.save(accountDto.getEntidad()).flatMap(na -> {
		        return Mono.just(new EntityDto<OperationPersonLegalAccountReq>(true,
		                             "Operación registrada", null));
		       });
		    } else
		        return Mono.just(
		         new EntityDto<OperationPersonLegalAccountReq>(false,
		                     accountDto.getMessage(), null));
		      }
		     }
		        return Mono.just(
		         new EntityDto<OperationPersonLegalAccountReq>(false,
		                              "No se permite la operacion", null));
		            });
		        });
		     });
	}

	@Override
	public Mono<AccountLegalPersonListOperationResp> getOperationByIdAccount(String id) {
		log.info("Entro a listar");
        return accountRepository.findById(id)
                        .doOnNext(p -> log.info(p.toString()))
                        .map(mapper -> new AccountLegalPersonListOperationResp(
                                        mapper.getAccountNumber(),
                                        mapper.getBalance(),
                                        mapper.getIdCustomers(),
                                        mapper.getAccountType(),
                                        mapper.getState(),
                                        mapper.getOperations()));
	
	}

}
