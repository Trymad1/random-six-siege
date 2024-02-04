package com.trymad.model;

public enum WeaponTypes {
    PRIMARY_WEAPON,
    SECONDARY_WEAPON,
    UNIQUE_ABILITY,
    GADGET;

    public String getFormattedName() {
        return toString().toLowerCase();
    }

    public String getName() {
        return toString().replace("_", " ");
    }
}
