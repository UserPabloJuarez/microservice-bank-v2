package com.nttdata.passive.dto;

import java.util.Collection;

import com.nttdata.passive.model.Operation;
import com.nttdata.passive.model.TypeProduct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardNaturalPersonListOperationResp {
	private String productCode;
    private Double balance;
    private Double creditLimit;
    private Collection<String> idCustomers;
    private TypeProduct typeProduct;
    private Collection<Operation> operations;
}
