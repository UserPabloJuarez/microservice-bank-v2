package com.nttdata.passive.helpers;

public enum OperationTypeEnum {
	PAY {
        public String toString() {	            
        	return "PAY";
	    }
	},
	DEP {
        public String toString() {
            return "DEP";
        }
    },
    WIT {
        public String toString() {
            return "WIT";
        }
    }
   
}
