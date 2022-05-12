package com.nttdata.passive.abstraction;

import java.util.Date;
import java.util.UUID;

import com.nttdata.passive.dto.EntityDto;
import com.nttdata.passive.dto.OperationPersonNaturalAccountReq;
import com.nttdata.passive.model.Account;
import com.nttdata.passive.model.AccountType;
import com.nttdata.passive.model.Operation;
import com.nttdata.passive.model.TypeOperation;

public class OperationsPersonNaturalFixed extends OperationsPersonNatural {

	//private static final Logger log = LoggerFactory.getLogger(OperationsPersonNaturalFixed.class);

	@Override
	public EntityDto<Account> Dep(OperationPersonNaturalAccountReq entity, Account x, AccountType y) {
		 
		if (y.getMovementsConfig().getDepositDate().getTime() != (new Date()).getTime())
	            return new EntityDto<Account>(false,
	                    "Solo puede realizar retiros en las fecha indicadas.", null);
	        else {
	            Operation operation = new Operation(
	                    UUID.randomUUID().toString().replace("-", ""),
	                    new TypeOperation("DEP", "Deposito"),
	                    entity.getDescription(),
	                    entity.getValue(),
	                    y.getCommission(),
	                    entity.getValue() - y.getCommission(),
	                    new Date(),
	                    entity.getOperationChannel());

	            x.getOperations().add(operation);

	            return new EntityDto<Account>(true,
	                    "Operación permitida.", x);

	        }
	}

	@Override
	public EntityDto<Account> Pay(OperationPersonNaturalAccountReq entity, Account x, AccountType y) {
		if (entity.getValue() >= (x.getBalance()))
            return new EntityDto<Account>(false,
                    "El monto a realizar no debe de superar la cuenta", null);
        else {
            Operation operation = new Operation(
                    UUID.randomUUID().toString().replace("-", ""),
                    new TypeOperation("PAY", "Pago"),
                    entity.getDescription(),
                    entity.getValue(),
                    y.getCommission(),
                    entity.getValue() - y.getCommission(),
                    new Date(),
                    entity.getOperationChannel());

            x.getOperations().add(operation);

            return new EntityDto<Account>(true,
                    "Operación aceptada.", x);
        }
	}

	@Override
	public EntityDto<Account> Wit(OperationPersonNaturalAccountReq entity, Account x, AccountType y) {
		if (y.getMovementsConfig().getWithdrawalDate().getTime() != (new Date()).getTime())
            return new EntityDto<Account>(false,
                    "El valor a retirar debe ser mayor a la saldo de la cuenta.", null);
        else {
            Operation operation = new Operation(
                    UUID.randomUUID().toString().replace("-", ""),
                    new TypeOperation("WIT", "Retiro"),
                    entity.getDescription(),
                    entity.getValue(),
                    y.getCommission(),
                    entity.getValue() - y.getCommission(),
                    new Date(),
                    entity.getOperationChannel());

            x.getOperations().add(operation);

            return new EntityDto<Account>(true,
                    "Operación permitida.", x);
        }
	}
	
	
	
}
