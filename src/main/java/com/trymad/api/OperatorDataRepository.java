package com.trymad.api;

import java.util.Optional;

import com.trymad.model.Operator;

public interface OperatorDataRepository {
    Optional<Operator> extractOperatorByName(String opformattedName);
    Optional<Loadout> extractLoadoutByName(String opFormattedName);
}
