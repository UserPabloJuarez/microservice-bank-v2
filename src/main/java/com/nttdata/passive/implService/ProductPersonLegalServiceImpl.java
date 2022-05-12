package com.nttdata.passive.implService;

import com.nttdata.passive.dto.CreditCardLegalPersonListOperationResp;
import com.nttdata.passive.dto.CreditNaturalBussinesListOperationResp;
import com.nttdata.passive.dto.EntityDto;
import com.nttdata.passive.dto.OperationPersonLegalCreditCardReq;
import com.nttdata.passive.dto.OperationPersonLegalCreditReq;
import com.nttdata.passive.dto.ProductBussinesPersonCreditCardRegisterReq;
import com.nttdata.passive.dto.ProductBussinesPersonCreditRegisterReq;
import com.nttdata.passive.dto.ProductBussinesPersonListCreditCardResp;
import com.nttdata.passive.dto.ProductBussinesPersonListCreditResp;
import com.nttdata.passive.helpers.GenericFunction;
import com.nttdata.passive.model.Operation;
import com.nttdata.passive.model.Product;
import com.nttdata.passive.model.ProductPaySchedule;
import com.nttdata.passive.model.State;
import com.nttdata.passive.model.TypeOperation;
import com.nttdata.passive.repository.ProductRepository;
import com.nttdata.passive.service.ProductLegalPersonService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ProductPersonLegalServiceImpl  implements ProductLegalPersonService{

	private static final Logger log = LoggerFactory.getLogger(ProductPersonLegalServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

	@Override
	public Mono<EntityDto<ProductBussinesPersonCreditRegisterReq>> saveCredit(
			ProductBussinesPersonCreditRegisterReq entity) {
		return Mono.just(entity)
                .map(retorno -> new EntityDto<ProductBussinesPersonCreditRegisterReq>(true, "Cuenta creada", retorno))
                .flatMap(obj -> {

                    String productRandom = UUID.randomUUID()
                            .toString().replace("-", "");
                    List<String> idCustomers = new ArrayList<String>();
                    idCustomers.add(entity.getIdCustomer());

                    Product product = new Product(
                            null,
                            productRandom.toString(),
                            0.0,
                            entity.getBalance(),
                            entity.getTypeProduct().getId(),
                            entity.getProductState().getId(),
                            entity.getProductPaySchedules(),
                            idCustomers,
                            null,
                            null,
                            entity.getTypeProduct(),
                            entity.getProductState());

                    return productRepository
                            .insert(product)
                            .flatMap(nuevo -> {

                                return Mono.just(
                                        new EntityDto<ProductBussinesPersonCreditRegisterReq>(
                                                true,
                                                "Cuenta creada",
                                                new ProductBussinesPersonCreditRegisterReq(
                                                        nuevo.getBalance(),
                                                        nuevo.getProductPaysSchedule(),
                                                        entity.getIdCustomer(),
                                                        entity.getTypeProduct(),
                                                        entity.getProductState())));

                            });
                });

	}

	@Override
	public Flux<ProductBussinesPersonListCreditResp> getCredit(String idCustomer) {
		 return productRepository.findAll()
			        .filter(p -> p.getIdCustomers().contains(idCustomer))
			        .map(mapper -> new ProductBussinesPersonListCreditResp(
			                        mapper.getId(),
			                        mapper.getProductCode(),
			                        mapper.getBalance(),
			                        mapper.getCreditLimit(),
			                        mapper.getProductPaysSchedule(),
			                        idCustomer,
			                        null,
			                        mapper.getTypeProduct(),
			                        mapper.getProductState()
			                        )
			                    );
	}

	@Override
	public Mono<EntityDto<ProductBussinesPersonCreditCardRegisterReq>> saveCreditCard(
			ProductBussinesPersonCreditCardRegisterReq entity) {
		log.info("Entro al metodo");

        return Mono.just(entity)
                .map(retorno -> new EntityDto<ProductBussinesPersonCreditCardRegisterReq>(true, "Cuenta creada", retorno))
                .flatMap(obj -> {

                    log.info("Inicia proceso");
                    String productRandom = UUID.randomUUID()
                                            .toString().replace("-", "");
                                    List<String> idCustomers = new ArrayList<String>();
                                    idCustomers.add(entity.getIdCustomer());

                                    Product product = new Product(
                                        null, 
                                        productRandom.toString(), 
                                        entity.getCreditLimit(), 
                                        null, entity.getTypeProduct().getId(), 
                                        entity.getProductState().getId(),
                                        null, 
                                        idCustomers, 
                                        new ArrayList<Operation>(), 
                                        GenericFunction.generateCard(), 
                                        entity.getTypeProduct(),
                                        entity.getProductState());

    
                                    return productRepository
                                            .insert(product)
                                            .flatMap(nuevo -> {

                                                return Mono.just(
                                                        new EntityDto<ProductBussinesPersonCreditCardRegisterReq>(
                                                                true,
                                                                "Cuenta creada",
                                                                new ProductBussinesPersonCreditCardRegisterReq(
                                                                        nuevo.getCreditLimit(),
                                                                        entity.getIdCustomer(),
                                                                        entity.getTypeProduct(),
                                                                        entity.getProductState())));

                                            });
                });
	}

	@Override
	public Flux<ProductBussinesPersonListCreditCardResp> getCreditCard(String idCustomer) {
		 return productRepository.findAll()
			        .filter(p -> p.getIdCustomers().contains(idCustomer))
			        .map(mapper -> new ProductBussinesPersonListCreditCardResp(
			                        mapper.getId(),
			                        mapper.getProductCode(),
			                        mapper.getBalance(),
			                        mapper.getCreditLimit(),
			                        idCustomer,
			                        mapper.getOperations(),
			                        mapper.getTypeProduct(),
			                        mapper.getProductState()
			                        )
			                    );
	}

	@Override
	public Mono<EntityDto<OperationPersonLegalCreditReq>> regOperationCredit(OperationPersonLegalCreditReq entity) {
		log.info("Entro al metodo Reg. Operation");

        return Mono.just(entity)
                        .map(retorno -> new EntityDto<OperationPersonLegalCreditReq>(true,
                                        "Operacion con exito",
                                        retorno))
                        .flatMap(obj -> {

                                return productRepository.findById(entity.getIdProduct())
                                                .flatMap(p -> {

                                                        var pay = p.getProductPaysSchedule().stream()
                                                        .sorted(Comparator.comparing(ProductPaySchedule::getDatePay))
                                                        .filter(item -> (item.getState().getId().equals("D")))
                                                        .findFirst()
                                                        .orElse(null);
                                                        
                                                        if(pay == null) 
                                                             return Mono.just(
                                                                        new EntityDto<OperationPersonLegalCreditReq>(
                                                                                        false,
                                                                                        "No se encontro deuda",
                                                                                        null));
                                                        else {
                                                                p.getProductPaysSchedule().forEach(item -> {
                                                                        if(pay.equals(pay)){
                                                                                item.setDatePayed(new Date());
                                                                                item.setState(new State("P","Pagado"));
                                                                        }
                                                                });

                                                                Operation operation = new Operation(
                                                                UUID.randomUUID().toString().replace("-", ""),
                                                                new TypeOperation("PAY", "Pago"),
                                                                entity.getDescription(),
                                                                entity.getValue(),
                                                                0.0,
                                                                entity.getValue(),
                                                                new Date(),
                                                                entity.getOperationChannel());

                                                                p.getOperations().add(operation);

                                                                productRepository.save(p);

                                                                return Mono.just(
                                                                        new EntityDto<OperationPersonLegalCreditReq>(
                                                                                        true,
                                                                                        "Letra Pagada",
                                                                                        null));
                                                        }

                                                });

                        });
	}

	@Override
	public Mono<CreditNaturalBussinesListOperationResp> getOperationCreditByIdProduct(String id) {
		return productRepository.findById(id)
                .doOnNext(p -> log.info(p.toString()))
                .map(mapper -> new CreditNaturalBussinesListOperationResp(
                                mapper.getProductCode(),
                                mapper.getBalance(),
                                mapper.getIdCustomers(),
                                mapper.getTypeProduct(),
                                mapper.getOperations()));
	}

	@Override
	public Mono<EntityDto<OperationPersonLegalCreditCardReq>> regOperationCreditCard(
			OperationPersonLegalCreditCardReq entity) {
		 return Mono.just(entity)
                 .map(retorno -> new EntityDto<OperationPersonLegalCreditCardReq>(true,
                                 "Operacion con exito",
                                 retorno))
                 .flatMap(obj -> {

                         return productRepository.findById(entity.getIdProduct())
                                         .flatMap(p -> {

                                                 
                                                 if(entity.getTypeProduct().getId().equals("WIT")) {

                                                         Operation operation = new Operation(
                                                         UUID.randomUUID().toString().replace("-", ""),
                                                         new TypeOperation("WIT", "Pago Tarjeta"),
                                                         entity.getDescription(),
                                                         entity.getValue(),
                                                         0.0,
                                                         entity.getValue(),
                                                         new Date(),
                                                         entity.getOperationChannel());

                                                         p.getOperations().add(operation);

                                                         p.setBalance(p.getBalance() + entity.getValue());

                                                         productRepository.save(p);

                                                         return Mono.just(
                                                                 new EntityDto<OperationPersonLegalCreditCardReq>(
                                                                                 true,
                                                                                 "Letra Pagada",
                                                                                 null));


                                                 } else if(entity.getTypeProduct().getId().equals("PAY")) {

                                                         if(p.getBalance() <entity.getValue()) {
                                                                 return Mono.just(
                                                                 new EntityDto<OperationPersonLegalCreditCardReq>(
                                                                                 false,
                                                                                 "La operación supera el monto disponible",
                                                                                 null));

                                                         } else {
                                                                 Operation operation = new Operation(
                                                                 UUID.randomUUID().toString().replace("-", ""),
                                                                 new TypeOperation("WIT", "Pago"),
                                                                 entity.getDescription(),
                                                                 entity.getValue(),
                                                                 0.0,
                                                                 entity.getValue(),
                                                                 new Date(),
                                                                 entity.getOperationChannel());

                                                                 p.getOperations().add(operation);

                                                                 p.setBalance(p.getBalance() - entity.getValue());

                                                                 productRepository.save(p);

                                                                 return Mono.just(
                                                                         new EntityDto<OperationPersonLegalCreditCardReq>(
                                                                                         true,
                                                                                         "Operación permitida",
                                                                                         null));
                                                         }
                                                         
                                                 } else {
                                                         return Mono.just(
                                                                 new EntityDto<OperationPersonLegalCreditCardReq>(
                                                                                 false,
                                                                                 "Operación no permitida",
                                                                                 null));
                                                 }
                                         });

                 });
	}

	@Override
	public Mono<CreditCardLegalPersonListOperationResp> getOperationCreditByIdProductCard(String id) {
		return productRepository.findById(id)
                .doOnNext(p -> log.info(p.toString()))
                .map(mapper -> new CreditCardLegalPersonListOperationResp(
                                mapper.getProductCode(),
                                mapper.getBalance(),
                                mapper.getCreditLimit(),
                                mapper.getIdCustomers(),
                                mapper.getTypeProduct(),
                                mapper.getOperations()));
	}
    
    
	
}
