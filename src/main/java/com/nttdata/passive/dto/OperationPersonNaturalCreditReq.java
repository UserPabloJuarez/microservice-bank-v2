package com.nttdata.passive.dto;

import com.nttdata.passive.model.OperationChannel;
import com.nttdata.passive.model.TypeOperation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OperationPersonNaturalCreditReq {
	private String idProduct;
    private String idCustomer;
    private TypeOperation typeOperation;
    private String description;
    private Double value;
    private OperationChannel operationChannel;
}
