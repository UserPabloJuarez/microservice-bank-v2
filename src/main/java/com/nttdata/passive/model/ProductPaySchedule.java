package com.nttdata.passive.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductPaySchedule {
	private Integer letter;
    private Date datePay;
    private Date datePayed;
    private State state;
    private Double value;
}
