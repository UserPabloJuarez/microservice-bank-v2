package com.nttdata.passive.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection="tipo-documento")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class DocumentType {

    @Id
    private String id;
    private String description;
     
}
