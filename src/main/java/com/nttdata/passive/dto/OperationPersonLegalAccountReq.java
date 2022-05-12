package com.nttdata.passive.dto;

import com.nttdata.passive.model.OperationChannel;
import com.nttdata.passive.model.TypeProduct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationPersonLegalAccountReq {
	private String idAccount;
    private String idCustomer;
    private TypeProduct typeProduct;
    private Double value;
    private String description;
    private OperationChannel operationChannel;
    
}
