package com.nttdata.passive.dto;

import com.nttdata.passive.model.OperationChannel;
import com.nttdata.passive.model.TypeOperation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationPersonNaturalAccountReq {
    private String idAccount;
    private String idCustomer;
    private TypeOperation typeOperation;
    private Double value;
    private String description;
    private OperationChannel operationChannel;
}
