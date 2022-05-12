package com.nttdata.passive.dto;

import java.util.Collection;

import com.nttdata.passive.model.AccountType;
import com.nttdata.passive.model.Operation;
import com.nttdata.passive.model.State;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountLegalPersonListOperationResp {
	private String accountNumber;
    private Double balance;
    private Collection<String> idCustomers;
    private AccountType accountType;
    private State state;
    private Collection<Operation> operations;
}
