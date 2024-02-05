package com.trymad.util;

public class OperatorNotFoundException extends RuntimeException {
    
    public OperatorNotFoundException(String operatorFormattedName) {
        super("Operator with name " + operatorFormattedName + " not found");
    }
}
