package com.nttdata.passive.dto;

import java.util.Collection;

import com.nttdata.passive.model.ProductPaySchedule;
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
public class ProductBussinesPersonCreditRegisterReq {
	private Double balance;
	private Collection<ProductPaySchedule> productPaySchedules;
    private String idCustomer;
    private TypeProduct typeProduct;
    private State productState;
}
