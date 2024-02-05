package com.trymad.api;

import java.util.List;
import java.util.Optional;

import com.trymad.model.Operator;
import com.trymad.model.Weapon;
import com.trymad.model.WeaponCategory;

public interface OperatorDataExtractor {
    Optional<Operator> extractOperatorByName(String opformattedName);
    Optional<Loadout> extractLoadoutByName(String opFormattedName);
}
