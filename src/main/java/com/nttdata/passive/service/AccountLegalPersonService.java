package com.nttdata.passive.service;

import com.nttdata.passive.dto.EntityDto;
import com.nttdata.passive.dto.OperationPersonLegalAccountReq;
import com.nttdata.passive.dto.AccountBussinesReq;
import com.nttdata.passive.dto.AccountLegalPersonListOperationResp;
import com.nttdata.passive.dto.AccountLegalPersonListResp;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountLegalPersonService {	
	public Mono<EntityDto<AccountBussinesReq>> save(AccountBussinesReq entity);
	public Flux<AccountLegalPersonListResp> finfindByIdCustomer(String id);
	public Mono<EntityDto<OperationPersonLegalAccountReq>> opRegister(OperationPersonLegalAccountReq entity);
	public Mono<AccountLegalPersonListOperationResp> getOperationByIdAccount(String id);
	
}
