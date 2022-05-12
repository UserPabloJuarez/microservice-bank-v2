package com.nttdata.passive.abstraction;

import java.util.Date;
import java.util.UUID;

import com.nttdata.passive.dto.EntityDto;
import com.nttdata.passive.dto.OperationPersonLegalAccountReq;
import com.nttdata.passive.model.AccountType;
import com.nttdata.passive.model.Operation;
import com.nttdata.passive.model.Account;
import com.nttdata.passive.model.TypeOperation;

public class OperationsPersonLegalCurrent extends OperationsPersonLegal{
	
	@Override
	public EntityDto<Account> Dep(OperationPersonLegalAccountReq entity, Account x, AccountType y){
		if(entity.getValue() < y.getCommission()) {
			
			return new EntityDto<Account>(false, "El deposito debe de ser mayor a la comision.", null);
		}else {
			Operation operation = new Operation(
					UUID.randomUUID().toString().replace("-", ""),
					new TypeOperation("DEP", "Deposito"),
					entity.getDescription(),
					entity.getValue(),
					y.getCommission(),
					entity.getValue() - y.getCommission(),
					new Date(),
					entity.getOperationChannel()
					);
			x.getOperations().add(operation);
			
			return new EntityDto<Account>(true, "Operacion aceptada", x);
		}
	}
	
	
	@Override
	public EntityDto<Account> Pay(OperationPersonLegalAccountReq entity, Account x, AccountType y) {
		
		if(entity.getValue() >= (x.getBalance())) {
			return new EntityDto<Account>(false, "El monto a realizar no debe de superar la cuenta", null);
		}else {
			Operation operation = new Operation(
					UUID.randomUUID().toString().replace("-", ""),
					new TypeOperation("PAY", "Pago"),
					entity.getDescription(),
					entity.getValue(),
					y.getCommission(),
					entity.getValue() - y.getCommission(),
					new Date(),
					entity.getOperationChannel()
					);
			x.getOperations().add(operation);
			
			return new EntityDto<Account>(true, "Operacion aceptada", x);
		}
	}


	@Override
	public EntityDto<Account> Wit(OperationPersonLegalAccountReq entity, Account x, AccountType y) {
		
		if(entity.getValue() <= y.getCommission()) {
			return new EntityDto<Account>(false, "El valor a retirar debe de ser mayor a la comision", null);
		}else if(entity.getValue() > (x.getBalance() + y.getCommission())) {
			return new EntityDto<Account>(false,"El valor a retirar debe de ser mayor al saldo de la cuenta",null);
		}else {
			Operation operation = new Operation(
					UUID.randomUUID().toString().replace("-", ""),
					new TypeOperation("WIT","Retiro"),
					entity.getDescription(),
					entity.getValue(), 
					y.getCommission(),
					entity.getValue() - y.getCommission(),
					new Date(),
					entity.getOperationChannel()
					);
			
			x.getOperations().add(operation);
			
			return new EntityDto<Account>(true, "Operación aceptada", x);
		}
	}
}