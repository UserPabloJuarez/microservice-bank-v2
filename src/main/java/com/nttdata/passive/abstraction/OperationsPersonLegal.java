package com.nttdata.passive.abstraction;

import com.nttdata.passive.model.Account;
import com.nttdata.passive.model.AccountType;
import com.nttdata.passive.dto.EntityDto;
import com.nttdata.passive.dto.OperationPersonLegalAccountReq;

public abstract class OperationsPersonLegal {
	public abstract EntityDto<Account> Dep(OperationPersonLegalAccountReq entity, Account account, AccountType accountType);
	public abstract EntityDto<Account> Pay(OperationPersonLegalAccountReq entity, Account account, AccountType accountType);
	public abstract EntityDto<Account> Wit(OperationPersonLegalAccountReq entity, Account account, AccountType accountType);
}
