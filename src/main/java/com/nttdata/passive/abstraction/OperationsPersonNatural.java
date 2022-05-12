package com.nttdata.passive.abstraction;

import com.nttdata.passive.dto.EntityDto;
import com.nttdata.passive.dto.OperationPersonNaturalAccountReq;
import com.nttdata.passive.model.Account;
import com.nttdata.passive.model.AccountType;

public abstract class OperationsPersonNatural {
	
	public abstract EntityDto<Account> Dep(OperationPersonNaturalAccountReq entity, Account account, AccountType accountType);
	public abstract EntityDto<Account> Pay(OperationPersonNaturalAccountReq entity, Account account, AccountType accountType);
	public abstract EntityDto<Account> Wit(OperationPersonNaturalAccountReq entity, Account account, AccountType accountType);
	
}
