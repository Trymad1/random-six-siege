package com.trymad.api;

import java.util.List;
import java.util.Optional;

import com.trymad.model.Operator;
import com.trymad.model.Weapon;
import com.trymad.model.WeaponTypes;

public interface OperatorDataExtractor {
    Optional<Operator> extractOperatorByName(String opformattedName);
    Optional<Weapon> extractWeaponByName(String opFormattedName, WeaponTypes weaponType, String weaponFormattedName);
}
