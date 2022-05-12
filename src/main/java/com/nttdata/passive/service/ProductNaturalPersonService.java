package com.nttdata.passive.service;

import com.nttdata.passive.dto.CreditCardNaturalPersonListOperationResp;
import com.nttdata.passive.dto.CreditNaturalPersonListOperationResp;
import com.nttdata.passive.dto.EntityDto;
import com.nttdata.passive.dto.OperationPersonNaturalCreditCardReq;
import com.nttdata.passive.dto.OperationPersonNaturalCreditReq;
import com.nttdata.passive.dto.ProductNaturalPersonListCreditCardResp;
import com.nttdata.passive.dto.ProductNaturalPersonListCreditResp;
import com.nttdata.passive.dto.ProductNaturalPersonalCreditCardRegisterReq;
import com.nttdata.passive.dto.ProductNaturalPersonalCreditRegisterReq;
import com.nttdata.passive.dto.ProductOperationCreditCardReq;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductNaturalPersonService {

	 public Mono<EntityDto<ProductNaturalPersonalCreditRegisterReq>> saveCredit(ProductNaturalPersonalCreditRegisterReq entity);
	 public Flux<ProductNaturalPersonListCreditResp> getCredit(String idCustomer);
	 public Mono<EntityDto<ProductNaturalPersonalCreditCardRegisterReq>> saveCreditCard(ProductNaturalPersonalCreditCardRegisterReq entity);
	 public Flux<ProductNaturalPersonListCreditCardResp> getCreditCard(String idCustomer);
	 public Mono<EntityDto<OperationPersonNaturalCreditReq>> regOperationCredit(OperationPersonNaturalCreditReq entity);
	 public Mono<CreditNaturalPersonListOperationResp> getOperationCreditByIdProduct(String id);
	 public Mono<EntityDto<OperationPersonNaturalCreditCardReq>> regOperationCreditCard(OperationPersonNaturalCreditCardReq entity);
	 public Mono<CreditCardNaturalPersonListOperationResp> getOperationCreditByIdProductCard(String id);
	 public Mono<EntityDto<ProductOperationCreditCardReq>> regOperationCreditCardByCard(ProductOperationCreditCardReq entity);
	
}
