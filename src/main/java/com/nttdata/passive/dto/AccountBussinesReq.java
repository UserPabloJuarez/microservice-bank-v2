package com.nttdata.passive.dto;

import java.util.Collection;

import org.springframework.expression.Operation;

import com.nttdata.passive.model.AccountBusiness;
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
public class AccountBussinesReq {
	 private String accountNumber;
	 private Double balance;
	 private Collection<String> idCustomers;
	 private AccountType accountType;
	 private State state;
	 private AccountBusiness accountBusiness;
	 private Collection<Operation> operations;
}
