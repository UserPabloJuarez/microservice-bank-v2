package com.nttdata.passive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntityDto<T> {
	public boolean result;
    public String message;
    private T entidad;
}
