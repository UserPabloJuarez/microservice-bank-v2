package com.nttdata.passive.service;

import com.nttdata.passive.dto.EntityDto;
import com.nttdata.passive.dto.OperationPersonNaturalAccountReq;
import com.nttdata.passive.dto.ReportPersonNatural;
import com.nttdata.passive.dto.AccountNaturalPersonListOperationResp;
import com.nttdata.passive.dto.AccountNaturalPersonListResp;
import com.nttdata.passive.dto.AccountSaveRegisterReq;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountNaturalPersonService {

	public Mono<EntityDto<AccountSaveRegisterReq>> save(AccountSaveRegisterReq entity);
    public Flux<AccountNaturalPersonListResp> findByIdCustomer(String id);
    public Mono<EntityDto<OperationPersonNaturalAccountReq>> regOperation(OperationPersonNaturalAccountReq entity);
    public Mono<AccountNaturalPersonListOperationResp> getOperationByIdAccount(String id);
    public Mono<EntityDto<ReportPersonNatural>> getReport(String id);
	
}
