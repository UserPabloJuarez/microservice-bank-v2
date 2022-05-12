package com.nttdata.passive.model;

import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Document(collection="product")
public class Product {
    @Id
    private String id;
    private String productCode;
    private Double balance;
    private Double creditLimit;
    private String idProductType;
    private String idState;
    private Collection<ProductPaySchedule> productPaysSchedule;
    private Collection<String> idCustomers;
    private Collection<Operation> operations;
    private CreditCard creditCard;

    private TypeProduct typeProduct;
    private State productState;    
    
}
