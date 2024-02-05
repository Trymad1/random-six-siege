package com.trymad.util;

public class LoadoutNotFoundException extends RuntimeException {
    public LoadoutNotFoundException(String opFormattedName) {
        super("Loadout for " + opFormattedName + " not found");
    }
}
