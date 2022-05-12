package com.nttdata.passive.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Document(collection="account-type")
public class AccountType {

	@Id
    private String id;
    private String description;
    private Integer withdrawalMovements;
    private Double commission;
    private Integer depositMovements;
    private MovementsConfig movementsConfig;
	
}
