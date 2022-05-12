package com.nttdata.passive.dto;

import java.util.Collection;

import com.nttdata.passive.model.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportPersonNatural {
	 private Collection<AccountNaturalPersonListResp> accounts;
	 private Collection<Product> products;
}
