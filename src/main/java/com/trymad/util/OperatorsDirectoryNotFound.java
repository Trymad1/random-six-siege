package com.trymad.util;

public class OperatorsDirectoryNotFound extends RuntimeException {
    public OperatorsDirectoryNotFound() {
        super("Operators directory not found - URL is null");
    }
}
