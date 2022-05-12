package com.nttdata.passive.dto;

import com.nttdata.passive.model.State;
import com.nttdata.passive.model.TypeProduct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductNaturalPersonalCreditCardRegisterReq {
	private Double creditLimit;
    private String idCustomer;
    private TypeProduct typeProduct;
    private State productState;
}
