package com.trymad.model;

import com.trymad.api.Loadout;

public record OperatorData (Operator operatorData, Loadout loadoutData) {
    
    @Override
    public final String toString() {
        return operatorData + "\n" + loadoutData;
    }
}
