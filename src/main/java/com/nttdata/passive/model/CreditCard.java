package com.nttdata.passive.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {

	private String cardNumber;    
    private String code;
    private String pin;
    private Date dueDate;
	
}
