package com.nttdata.passive.service;

import com.nttdata.passive.dto.CreditCardLegalPersonListOperationResp;
import com.nttdata.passive.dto.CreditNaturalBussinesListOperationResp;
import com.nttdata.passive.dto.EntityDto;
import com.nttdata.passive.dto.OperationPersonLegalCreditCardReq;
import com.nttdata.passive.dto.OperationPersonLegalCreditReq;
import com.nttdata.passive.dto.ProductBussinesPersonCreditCardRegisterReq;
import com.nttdata.passive.dto.ProductBussinesPersonCreditRegisterReq;
import com.nttdata.passive.dto.ProductBussinesPersonListCreditCardResp;
import com.nttdata.passive.dto.ProductBussinesPersonListCreditResp;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductLegalPersonService {
	
	public Mono<EntityDto<ProductBussinesPersonCreditRegisterReq>> saveCredit(ProductBussinesPersonCreditRegisterReq entity);
    public Flux<ProductBussinesPersonListCreditResp> getCredit(String idCustomer);
    public Mono<EntityDto<ProductBussinesPersonCreditCardRegisterReq>> saveCreditCard(ProductBussinesPersonCreditCardRegisterReq entity);
    public Flux<ProductBussinesPersonListCreditCardResp> getCreditCard(String idCustomer);
    public Mono<EntityDto<OperationPersonLegalCreditReq>> regOperationCredit(OperationPersonLegalCreditReq entity);
    public Mono<CreditNaturalBussinesListOperationResp> getOperationCreditByIdProduct(String id);
    public Mono<EntityDto<OperationPersonLegalCreditCardReq>> regOperationCreditCard(OperationPersonLegalCreditCardReq entity);
    public Mono<CreditCardLegalPersonListOperationResp> getOperationCreditByIdProductCard(String id);
}
