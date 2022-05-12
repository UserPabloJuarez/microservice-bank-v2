package com.nttdata.passive.model;

import java.util.Date;

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
public class Operation {

    private String operation;
    private TypeOperation typeOperation;
    private String description;
    private Double monto;
    private Double comision;
    private Double total;
    private Date dateOperation;
    private OperationChannel operationChannel;
	
}
