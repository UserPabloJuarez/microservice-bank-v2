package com.nttdata.passive.dto;

import com.nttdata.passive.model.AccountType;
import com.nttdata.passive.model.State;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountSaveRegisterReq {
	private String id;
    private String accountNumber;
    private Double balance;
    private String idCustomer;
    private AccountType accountType;
    private State state;
}
