package com.nttdata.passive.implService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nttdata.passive.abstraction.OperationsPersonNatural;
import com.nttdata.passive.abstraction.OperationsPersonNaturalCurrent;
import com.nttdata.passive.abstraction.OperationsPersonNaturalFixed;
import com.nttdata.passive.abstraction.OperationsPersonNaturalSave;
import com.nttdata.passive.dto.AccountNaturalPersonListOperationResp;
import com.nttdata.passive.dto.AccountNaturalPersonListResp;
import com.nttdata.passive.dto.AccountSaveRegisterReq;
import com.nttdata.passive.dto.EntityDto;
import com.nttdata.passive.dto.OperationPersonNaturalAccountReq;
import com.nttdata.passive.dto.ReportPersonNatural;
import com.nttdata.passive.helpers.AccountTypeEnum;
import com.nttdata.passive.helpers.GenericFunction;
import com.nttdata.passive.model.Account;
import com.nttdata.passive.model.Operation;
import com.nttdata.passive.repository.AccountRepository;
import com.nttdata.passive.repository.AccountTypeRepository;
import com.nttdata.passive.repository.ProductRepository;
import com.nttdata.passive.service.AccountNaturalPersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class AccountPersonNaturalImpl implements AccountNaturalPersonService{

	 private static final Logger log = LoggerFactory.getLogger(AccountPersonNaturalImpl.class);

     @Autowired
     private AccountRepository accountRepository;
     @Autowired
     private AccountTypeRepository accountTypeRepository;
     @Autowired
     private ProductRepository productRepository;
	
	@Override
	public Mono<EntityDto<AccountSaveRegisterReq>> save(AccountSaveRegisterReq entity) {
		log.info("Entro al metodo");
		
		return Mono.just(entity)
             .map(retorno -> new EntityDto<AccountSaveRegisterReq>(true, "Cuenta creada", retorno))
             .flatMap(obj -> {log.info("Inicia proceso");
                        return accountRepository.findAll().filter(account -> {
                               log.info("Inicia Filtro 0 :");
                               log.info("Inicia Filtro 1 ->"
                               .concat(entity.getIdCustomer()));
                        return account.getIdCustomers().contains(entity.getIdCustomer())
                               && account.getIdAccountType().equals(entity.getAccountType().getId());
             }).collectList().flatMap(p -> {
                               log.info("Inicia validacion ->"
                               .concat(String.valueOf(p.size())));
              if (p.size() > 0) {
                 log.info("No Paso validacion");
                 return Mono.just(new EntityDto<AccountSaveRegisterReq>(false,
                          "Ya tiene una cuenta registrada", null));
              } else {
                 log.info("Paso validacion");
                 String accountRandom = UUID.randomUUID().toString().replace("-", "");
                 List<String> idCustomers = new ArrayList<String>();
                 idCustomers.add(entity.getIdCustomer());
                 Account account = new Account(null, accountRandom, entity.getBalance(),
                                   entity.getAccountType().getId(),
                                   entity.getState().getId(),
                                   idCustomers, null,
                                     new ArrayList<Operation>(),
                                     entity.getAccountType(),
                                     entity.getState(),
                                     GenericFunction.generateCard());

                  obj.getEntidad().setAccountNumber(accountRandom);
                      return accountRepository.insert(account).flatMap(nuevo -> {
                             return Mono.just(new EntityDto<AccountSaveRegisterReq>(true,
                             "Cuenta creada",new AccountSaveRegisterReq(
                                   nuevo.getId(), 
                                   nuevo.getAccountNumber(),
                                   nuevo.getBalance(),
                                   entity.getIdCustomer(),
                                   entity.getAccountType(),
                                   entity.getState())));
                               });
                             }
                 });
         });
	}

	@Override
	public Flux<AccountNaturalPersonListResp> findByIdCustomer(String id) {
		 return accountRepository.findAll()
                 .filter(x -> x.getIdCustomers().contains(id))
                 .map(mapper -> new AccountNaturalPersonListResp(mapper.getId(),
                                 mapper.getAccountNumber(),
                                 mapper.getBalance(),
                                 id,
                                 mapper.getAccountType().getDescription(),
                                 mapper.getState().getDescription()));
	}

	@Override
	public Mono<EntityDto<OperationPersonNaturalAccountReq>> regOperation(OperationPersonNaturalAccountReq entity) {
		log.info("Entro al metodo Reg. Operation");

        return Mono.just(entity)
              .map(retorno -> new EntityDto<OperationPersonNaturalAccountReq>(true,
                 "Operacion con exito", retorno))
              .flatMap(obj -> {
                   log.info("Inicia validacion 1");
               return accountRepository.findById(entity.getIdAccount())
              .flatMap(p -> {
                   log.info("Inicia validacion 2");
               return accountTypeRepository
               .findById(p.getIdAccountType())
               .flatMap(at -> {
                   log.info("Inicia validacion 3");
               if (entity.getTypeOperation().getId().equals("DEP")) {
                   log.info("Inicia validacion 4");
                  if (p.getIdAccountType().equals(AccountTypeEnum.ACCOUNT_SAVE.toString())) {
                    log.info("Inicia validacion 5");
                    OperationsPersonNatural operationsPersonNatural = new OperationsPersonNaturalSave();
                    var accountDto = operationsPersonNatural.Dep(entity, p, at);
                   if (accountDto.isResult()) {
                       accountDto.getEntidad()
                      .setBalance(accountDto
                      .getEntidad()
                      .getBalance() + entity.getValue());
                      return accountRepository.save(accountDto.getEntidad()).flatMap(na -> {
                             return Mono.just(
                                    new EntityDto<OperationPersonNaturalAccountReq>(true,
                                        "Operación registrada", null));
                     });
               } else
                 return Mono.just(
                        new EntityDto<OperationPersonNaturalAccountReq>(false,
                            accountDto.getMessage(), null));
               } else if (p.getIdAccountType().equals(AccountTypeEnum.ACCOUNT_CURRENT.toString())) {
                    OperationsPersonNatural operationsPersonNatural = new OperationsPersonNaturalCurrent();
                    var accountDto = operationsPersonNatural.Dep(entity, p, at);
                if (accountDto.isResult()) {
                    accountDto.getEntidad()
                    .setBalance(accountDto
                    .getEntidad()
                    .getBalance() + entity.getValue());
                 return accountRepository.save(accountDto.getEntidad())
                    .flatMap(na -> {
                    return Mono.just(
                    new EntityDto<OperationPersonNaturalAccountReq>(true, "Operación registrada",null));
       });
              } else
                 return Mono.just(new EntityDto<OperationPersonNaturalAccountReq>(false,
                        accountDto.getMessage(), null));
              } else if (p.getIdAccountType().equals(AccountTypeEnum.ACCOUNT_FIXED.toString())) {
                        OperationsPersonNatural operationsPersonNatural = new OperationsPersonNaturalFixed();
                        var accountDto = operationsPersonNatural
                        .Dep(entity, p, at);
                 if (accountDto.isResult()) {
                     accountDto.getEntidad()
                    .setBalance(accountDto
                    .getEntidad()
                    .getBalance() + entity.getValue());
                    return accountRepository.save(accountDto.getEntidad()).flatMap(na -> {
                      return Mono.just(new EntityDto<OperationPersonNaturalAccountReq>(true,
                       "Operación registrada", null));
                 });
               } else
                     return Mono.just(new EntityDto<OperationPersonNaturalAccountReq>(false,
                            accountDto.getMessage(), null));
               }
             } else if (entity.getTypeOperation().getId().equals("WIT")) {
                    if (p.getIdAccountType().equals(AccountTypeEnum.ACCOUNT_SAVE.toString())) {
                        OperationsPersonNatural operationsPersonNatural = new OperationsPersonNaturalSave();
                        var accountDto = operationsPersonNatural
                        .Wit(entity, p, at);
                    if (accountDto.isResult()) {
                        accountDto.getEntidad()
                        .setBalance(accountDto
                        .getEntidad()
                        .getBalance() - entity.getValue());
                   return accountRepository.save(accountDto.getEntidad()).flatMap(na -> {
                     return Mono.just(new EntityDto<OperationPersonNaturalAccountReq>(
                                        true,"Operación registrada", null));
                });
            } else
                 return Mono.just(new EntityDto<OperationPersonNaturalAccountReq>(false,
                        accountDto.getMessage(), null));
            } else if (p.getIdAccountType().equals(AccountTypeEnum.ACCOUNT_CURRENT.toString())) {
                      OperationsPersonNatural operationsPersonNatural = new OperationsPersonNaturalCurrent();
                      var accountDto = operationsPersonNatural.Wit(entity, p, at);
              if (accountDto.isResult()) {
                  accountDto.getEntidad()
                  .setBalance(accountDto
                  .getEntidad()
                  .getBalance() - entity.getValue());
             return accountRepository.save(accountDto.getEntidad()).flatMap(na -> {
                    return Mono.just(new EntityDto<OperationPersonNaturalAccountReq>(
                           true, "Operación registrada", null));
                });
             } else
                 return Mono.just( new EntityDto<OperationPersonNaturalAccountReq>(
                        false, accountDto.getMessage(), null));
          } else if (p.getIdAccountType().equals(AccountTypeEnum.ACCOUNT_FIXED.toString())) {
                     OperationsPersonNatural operationsPersonNatural = new OperationsPersonNaturalFixed();
                     var accountDto = operationsPersonNatural.Wit(entity, p, at);
            if (accountDto.isResult()) {
                accountDto.getEntidad()
                .setBalance(accountDto
                .getEntidad()
                .getBalance() - entity.getValue());
            return accountRepository.save(accountDto.getEntidad()).flatMap(na -> {
                   return Mono.just(new EntityDto<OperationPersonNaturalAccountReq>(
                          true, "Operación registrada", null));
          });
            } else
                  return Mono.just(new EntityDto<OperationPersonNaturalAccountReq>(
                         false, accountDto.getMessage(), null));
            }
         } else if (entity.getTypeOperation().getId().equals("PAY")) {
                if (p.getIdAccountType().equals(AccountTypeEnum.ACCOUNT_SAVE.toString())) {
                    OperationsPersonNatural operationsPersonNatural = new OperationsPersonNaturalSave();
                    var accountDto = operationsPersonNatural
                   .Pay(entity, p, at);
                if (accountDto.isResult()) {
                    accountDto.getEntidad()
                   .setBalance(accountDto
                   .getEntidad()
                   .getBalance() - entity.getValue());
             return accountRepository.save(accountDto.getEntidad()).flatMap(na -> {
                    return Mono.just(new EntityDto<OperationPersonNaturalAccountReq>(
                           true, "Operación registrada", null));
              });
            } else
                  return Mono.just(new EntityDto<OperationPersonNaturalAccountReq>(
                         false, accountDto.getMessage(), null));
            } else if (p.getIdAccountType().equals(AccountTypeEnum.ACCOUNT_CURRENT.toString())) {
                       OperationsPersonNatural operationsPersonNatural = new OperationsPersonNaturalCurrent();
                       var accountDto = operationsPersonNatural.Pay(entity, p, at);
                   if (accountDto.isResult()) {
                       accountDto.getEntidad()
                      .setBalance(accountDto
                      .getEntidad()
                      .getBalance() - entity.getValue());
            return accountRepository.save(accountDto.getEntidad()).flatMap(na -> {
                   return Mono.just(new EntityDto<OperationPersonNaturalAccountReq>(
                          true,"Operación registrada", null));
            });
          } else
                return Mono.just(new EntityDto<OperationPersonNaturalAccountReq>(
                       false, accountDto.getMessage(), null));
          } else if (p.getIdAccountType().equals(AccountTypeEnum.ACCOUNT_FIXED.toString())) {
                     OperationsPersonNatural operationsPersonNatural = new OperationsPersonNaturalFixed();
                     var accountDto = operationsPersonNatural.Pay(entity, p, at);
                 if (accountDto.isResult()) {
                     accountDto.getEntidad()
                     .setBalance(accountDto
                     .getEntidad()
                     .getBalance() - entity.getValue());
             return accountRepository.save(accountDto.getEntidad())
                   .flatMap(na -> {
                   return Mono.just(new EntityDto<OperationPersonNaturalAccountReq>(
                          true, "Operación registrada", null));
                   });
             } else
                   return Mono.just(new EntityDto<OperationPersonNaturalAccountReq>(
                          false, accountDto.getMessage(), null));
                  }
               }
                   return Mono.just(new EntityDto<OperationPersonNaturalAccountReq>(
                          false,"No se permite la operacion", null));
                            });
                          }).defaultIfEmpty(new EntityDto<OperationPersonNaturalAccountReq>(
                  false, "No se encontro la cuenta",null));
               });
	}

	@Override
	public Mono<AccountNaturalPersonListOperationResp> getOperationByIdAccount(String id) {
		 log.info("Entro a listar");
         return accountRepository.findById(id)
                         .doOnNext(p -> log.info(p.toString()))
                         .map(mapper -> new AccountNaturalPersonListOperationResp(
                                         mapper.getAccountNumber(),
                                         mapper.getBalance(),
                                         mapper.getIdCustomers(),
                                         mapper.getAccountType(),
                                         mapper.getState(),
                                         mapper.getOperations()));
	}

	@Override
	public Mono<EntityDto<ReportPersonNatural>> getReport(String id) {
		 return accountRepository.findAll()
                 .filter(p -> p.getIdCustomers().contains(id))
                 .map(mapper -> new AccountNaturalPersonListResp(mapper.getId(),
                                 mapper.getAccountNumber(),
                                 mapper.getBalance(),
                                 id,
                                 mapper.getAccountType().getDescription(),
                                 mapper.getState().getDescription()))
                 .collectList()
                 .flatMap(accounts -> {
                         
                         return productRepository.findAll()
                         .filter(p1 -> p1.getIdCustomers().contains(id))
                         .collectList()
                         .map(products -> {

                                 return new EntityDto<ReportPersonNatural>(true,"Resultado",
                                 new ReportPersonNatural(accounts,products));
                                 
                         });
                 });
	}

}
