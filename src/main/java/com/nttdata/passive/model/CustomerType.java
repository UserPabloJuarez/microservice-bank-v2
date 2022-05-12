package com.nttdata.passive.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection="customer-type")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class CustomerType {
	@Id
    private String id;
    private String description;      
    
    public CustomerType(String description) {
        this.description = description;
    }
}
