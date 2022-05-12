package com.nttdata.passive.dto;

import com.nttdata.passive.model.OperationChannel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOperationCreditCardReq {
	private String cardNumber;
    private String pin;   
    private String description;
    private Double value;
    private OperationChannel operationChannel;
}
