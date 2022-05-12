package com.nttdata.passive.helpers;

import java.util.Calendar;
import java.util.Date;

import com.nttdata.passive.model.CreditCard;


public class GenericFunction {
    
    public static CreditCard generateCard() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 8);
        Date date = cal.getTime();

        return new CreditCard(GenericFunction.getNumericString(16),  GenericFunction.getNumericString(4), GenericFunction.getNumericString(3), date);
    }

    public static String getNumericString(int n)
    {
        String AlphaNumericString = "0123456789";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int)(AlphaNumericString.length()* Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }
}
