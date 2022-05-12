package com.nttdata.passive.dto;

import com.nttdata.passive.model.AccountBusiness;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountLegalPersonListResp {
	private String id;
    private String accountNumber;
    private Double balance;
    private String idCustomer;
    private String stateDescription;
    private String accountTypeDescription;
    private AccountBusiness accountBusiness;
}
