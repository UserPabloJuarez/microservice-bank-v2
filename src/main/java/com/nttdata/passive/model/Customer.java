package com.nttdata.passive.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.Nullable;

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
@Document(collection="customer")
public class Customer {
	@Id
    private String id;
    private String idDocumentType;
    private String document;
    private String direction;
    private String mobilePhone;
    private String idCustomerType;
    @Nullable
    private NaturalPerson naturalPerson;
    @Nullable
    private LegalPerson legalPerson;

    @Transient
    private CustomerType customerType;
    @Transient
    private DocumentType documentType;
}
