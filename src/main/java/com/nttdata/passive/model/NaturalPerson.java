package com.nttdata.passive.model;

import java.util.Date;
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
public class NaturalPerson {
    private String firstName;
    private String lastName;
    private Date dateBirth;
}
