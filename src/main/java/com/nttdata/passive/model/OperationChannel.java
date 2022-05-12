package com.nttdata.passive.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Document(collection="operation-channel")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class OperationChannel {
    @Id
    private String id;
    private String description;
    
}
