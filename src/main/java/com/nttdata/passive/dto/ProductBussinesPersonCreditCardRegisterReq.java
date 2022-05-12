package com.nttdata.passive.dto;

import com.nttdata.passive.model.State;
import com.nttdata.passive.model.TypeProduct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductBussinesPersonCreditCardRegisterReq {
	private Double creditLimit;
    private String idCustomer;
    private TypeProduct typeProduct;
    private State productState;
}
