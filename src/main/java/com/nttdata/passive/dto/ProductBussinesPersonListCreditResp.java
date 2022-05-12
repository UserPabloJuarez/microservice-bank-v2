package com.nttdata.passive.dto;

import java.util.Collection;

import com.nttdata.passive.model.Operation;
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
public class ProductBussinesPersonListCreditResp {
	
	private String id;
    private String productCode;
    private Double balance;
    private Double creditLimit;
    private Collection<ProductPaySchedule> productPaySchedules;
    private String idCustomer;
    private Collection<Operation> operations;

    private TypeProduct typeProduct;
    private State productState;
}
