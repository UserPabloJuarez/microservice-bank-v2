package com.nttdata.passive.model;

import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Data
@Builder
@Document(collection="account")
public class Account {
	@Id
    private String id;
    private String accountNumber;
    private Double balance;
    private String idAccountType;
    private String idState;
    private Collection<String> idCustomers;
    @Nullable
    private AccountBusiness accountBusiness;
    private Collection<Operation> operations;
    private AccountType accountType;
    private State state;
    private CreditCard creditCard;

}
