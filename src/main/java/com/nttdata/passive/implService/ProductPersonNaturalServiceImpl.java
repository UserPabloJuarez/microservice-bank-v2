package com.nttdata.passive.implService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
import com.nttdata.passive.helpers.GenericFunction;
import com.nttdata.passive.model.Operation;
import com.nttdata.passive.model.Product;
import com.nttdata.passive.model.ProductPaySchedule;
import com.nttdata.passive.model.State;
import com.nttdata.passive.model.TypeOperation;
import com.nttdata.passive.repository.ProductRepository;
import com.nttdata.passive.service.ProductNaturalPersonService;

public class ProductPersonNaturalServiceImpl implements ProductNaturalPersonService{

	private static final Logger log = LoggerFactory.getLogger(ProductPersonNaturalServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

	@Override
	public Mono<EntityDto<ProductNaturalPersonalCreditRegisterReq>> saveCredit(
			ProductNaturalPersonalCreditRegisterReq entity) {
		
		return Mono.just(entity)
                .map(retorno -> new EntityDto<ProductNaturalPersonalCreditRegisterReq>(true,
                                "Cuenta creada", retorno))
                .flatMap(obj -> {

                        log.info("Inicia proceso de credito");
                        log.info(entity.toString());
                        return productRepository.findAll()
                                        .filter(product -> {

                                                return product.getIdCustomers()
                                                                .contains(entity.getIdCustomer())
                                                                && product.getIdProductType().equals(
                                                                                entity.getTypeProduct().getId());
                                        })
                                        .collectList()
                                        .flatMap(p -> {

                                                if (p.size() > 0) {
                                                        log.info("No Paso validacion");
                                                        return Mono.just(
                                                                        new EntityDto<ProductNaturalPersonalCreditRegisterReq>(
                                                                                        false,
                                                                                        "Ya tiene un credito registrado",
                                                                                        null));
                                                } else {

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
                                                                        new ArrayList<Operation>(),
                                                                        null,
                                                                        entity.getTypeProduct(),
                                                                        entity.getProductState());

                                                        return productRepository
                                                                        .insert(product)
                                                                        .flatMap(nuevo -> {

                                                                        return Mono.just(
                                       new EntityDto<ProductNaturalPersonalCreditRegisterReq>(
                                                        true,
                                                       "Cuenta creada",
                                                        new ProductNaturalPersonalCreditRegisterReq(
                                                        nuevo.getBalance(),
                                                        nuevo.getProductPaysSchedule(),
                                                        entity.getIdCustomer(),
                                                        entity.getTypeProduct(),
                                                        entity.getProductState())));
                                                   });

                                                }

                                        });
                });
	}

	@Override
	public Flux<ProductNaturalPersonListCreditResp> getCredit(String idCustomer) {
		return productRepository.findAll()
                .filter(p -> p.getIdCustomers().contains(idCustomer))
                .map(mapper -> new ProductNaturalPersonListCreditResp(
                                mapper.getId(),
                                mapper.getProductCode(),
                                mapper.getBalance(),
                                mapper.getCreditLimit(),
                                mapper.getProductPaysSchedule(),
                                idCustomer,
                                null,
                                mapper.getTypeProduct(),
                                mapper.getProductState()));
	}

	@Override
	public Mono<EntityDto<ProductNaturalPersonalCreditCardRegisterReq>> saveCreditCard(
			ProductNaturalPersonalCreditCardRegisterReq entity) {
		log.info("Entro al metodo");

        return Mono.just(entity)
                        .map(retorno -> new EntityDto<ProductNaturalPersonalCreditCardRegisterReq>(true,
                                        "Cuenta creada", retorno))
                        .flatMap(obj -> {

                                log.info("Inicia proceso");
                                return productRepository.findAll()
                                                .filter(product -> {

                                          return product.getIdCustomers().contains(entity.getIdCustomer())
                                           && product.getIdProductType().equals(
                                           entity.getTypeProduct()                                                                                                        .getId());
                                                })
                                                .collectList()
                                                .flatMap(p -> {
                                                if (p.size() > 0) {
                                                log.info("No Paso validacion");
                                                return Mono.just(
                                               new EntityDto<ProductNaturalPersonalCreditCardRegisterReq>(
                                               false, "Ya tiene una tarjeta credito registrado", null));
                                  } else {
                                         String productRandom = UUID.randomUUID()
                                        .toString().replace("-", "");
                                        List<String> idCustomers = new ArrayList<String>();
                                        idCustomers.add(entity.getIdCustomer());
                                        Product product = new Product(null,productRandom.toString(),
                                           entity.getCreditLimit(),
                                           entity.getCreditLimit(),
                                           entity.getTypeProduct().getId(),
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
                           new EntityDto<ProductNaturalPersonalCreditCardRegisterReq>(true,
                           "Cuenta creada", new ProductNaturalPersonalCreditCardRegisterReq(
                           nuevo.getCreditLimit(),
                           entity.getIdCustomer(),
                           entity.getTypeProduct(),
                           entity.getProductState())));
                               });
                          }
                  });
         });
	}

	@Override
	public Flux<ProductNaturalPersonListCreditCardResp> getCreditCard(String idCustomer) {
		 return productRepository.findAll()
                 .filter(p -> p.getIdCustomers().contains(idCustomer))
                 .map(mapper -> new ProductNaturalPersonListCreditCardResp(
                                 mapper.getId(),
                                 mapper.getProductCode(),
                                 mapper.getBalance(),
                                 mapper.getCreditLimit(),
                                 idCustomer,
                                 mapper.getOperations(),
                                 mapper.getTypeProduct(),
                                 mapper.getProductState()));
	}

	@SuppressWarnings("unused")
	@Override
	public Mono<EntityDto<OperationPersonNaturalCreditReq>> regOperationCredit(OperationPersonNaturalCreditReq entity) {
		log.info("Entro al metodo Reg. Operation");

        return Mono.just(entity)
                        .map(retorno -> new EntityDto<OperationPersonNaturalCreditReq>(true,
                                        "Operacion con exito",
                                        retorno))
                        .flatMap(obj -> {

                                return productRepository.findById(entity.getIdProduct())
                                                .flatMap(p -> {

                                                        log.info("Entro al metodo Reg. Operation 2");

                                                        var pay = p.getProductPaysSchedule().stream()
                                                        .sorted(Comparator.comparing(
                                                         ProductPaySchedule::getDatePay))
                                                         .filter(item -> (item.getState().getId().equals("D")))
                                                         .findFirst()
                                                         .orElse(null);

                                                        log.info("Entro al metodo Reg. Operation 3");
                                                        log.info(pay.toString());
                                                        log.info(entity.toString());

                                                 if (pay == null)
                                                 return Mono.just(new EntityDto<OperationPersonNaturalCreditReq>(
                                                        false, "No se encontro deuda", null));
                         else {
                               log.info("Entro al metodo Reg. Operation 4");
                           if (Double.compare(pay.getValue(), entity.getValue())  != 0) {
                             return Mono.just(new EntityDto<OperationPersonNaturalCreditReq>(false,
                                                  "El monto no corresponde a la deuda.", null));
                       }
                             log.info("Entro al metodo Reg. Operation 5");
                            p.getProductPaysSchedule().forEach(item -> {
                             if (item.equals(pay)) {
                                 item.setDatePayed(new Date());
                                 item.setState(new State("P", "Pagado"));
                               }
                            });

            Operation operation = new Operation(UUID.randomUUID().toString().replace("-", ""),
                                  new TypeOperation("PAY", "Pago"),
                                      entity.getDescription(),
                                      entity.getValue(),
                                      0.0,
                                      entity.getValue(),
                                      new Date(),
                                      entity.getOperationChannel());
                                      p.getOperations().add(operation);
                                      productRepository.save(p);
                             return Mono.just(new EntityDto<OperationPersonNaturalCreditReq>(
                                    true,"Letra Pagada", null));
                          }
                  });
           });
	}

	@Override
	public Mono<CreditNaturalPersonListOperationResp> getOperationCreditByIdProduct(String id) {
		return productRepository.findById(id)
                .doOnNext(p -> log.info(p.toString()))
                .map(mapper -> new CreditNaturalPersonListOperationResp(
                                mapper.getProductCode(),
                                mapper.getBalance(),
                                mapper.getIdCustomers(),
                                mapper.getTypeProduct(),
                                mapper.getOperations()));
	}

	@Override
	public Mono<EntityDto<OperationPersonNaturalCreditCardReq>> regOperationCreditCard(
			OperationPersonNaturalCreditCardReq entity) {
		return Mono.just(entity)
                .map(retorno -> new EntityDto<OperationPersonNaturalCreditCardReq>(true,
                                "Operacion con exito",
                                retorno))
                .flatMap(obj -> {

                        return productRepository.findById(entity.getIdProduct())
                                        .flatMap(p -> {

                                                if (entity.getTypeOperation().getId().equals("WIT")) {

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

                                                    return Mono.just(new EntityDto<OperationPersonNaturalCreditCardReq>(
                                                    true, "Letra Pagada", null));

                          } else if (entity.getTypeOperation().getId().equals("PAY")) {

                                 if (p.getBalance() < entity.getValue()) {
                                     return Mono.just(new EntityDto<OperationPersonNaturalCreditCardReq>(
                                            false, "La operación supera el monto disponible", null));
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

                      return Mono.just(new EntityDto<OperationPersonNaturalCreditCardReq>(
                             true, "Operación permitida", null));
                   }
            } else {
                      return Mono.just(new EntityDto<OperationPersonNaturalCreditCardReq>(
                                       false, "Operación no permitida", null));
                       }
                   });

             });
	}

	@Override
	public Mono<CreditCardNaturalPersonListOperationResp> getOperationCreditByIdProductCard(String id) {
		return productRepository.findById(id)
                .doOnNext(p -> log.info(p.toString()))
                .map(mapper -> new CreditCardNaturalPersonListOperationResp(
                                mapper.getProductCode(),
                                mapper.getBalance(),
                                mapper.getCreditLimit(),
                                mapper.getIdCustomers(),
                                mapper.getTypeProduct(),
                                mapper.getOperations()));
	}

	@Override
	public Mono<EntityDto<ProductOperationCreditCardReq>> regOperationCreditCardByCard(
			ProductOperationCreditCardReq entity) {
		return Mono.just(entity)
                .map(retorno -> new EntityDto<ProductOperationCreditCardReq>(true,
                                "Operacion con exito",
                                retorno))
                .flatMap(obj -> {

                        return productRepository.findAll()
                                        .filter(p -> p.getCreditCard().getCardNumber()
                                        .equals(entity.getCardNumber())
                                        && p.getCreditCard().getPin().equals(entity.getPin())).collectList()
                                        .flatMap(list -> {

                        var p = list.get(0);
                        if (p.getBalance() < entity.getValue()) {
                        return Mono.just(
                        new EntityDto<ProductOperationCreditCardReq>(
                        false, "La operación supera el monto disponible", null));

             } else {
                   Operation operation = new Operation(UUID.randomUUID().toString().replace("-", ""),
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
                   return Mono.just(new EntityDto<ProductOperationCreditCardReq>(
                          true, "Operación permitida", null));
                      }
                  });
          });
	}
    
    
	
}
