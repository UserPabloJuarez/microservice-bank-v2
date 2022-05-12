package com.nttdata.passive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountNaturalPersonListResp {

	private String id;
    private String accountNumber;
    private Double balance;
    private String accountTypeDescription;
    private String idCustomer;
    private String stateDescription;
	
}
