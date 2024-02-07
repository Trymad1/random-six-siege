package com.trymad.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.trymad.api.Loadout;

public record MapLoadout (Map<WeaponCategory, List<Weapon>> weaponMap) implements Loadout {

    @Override
    public List<Weapon> getPrimaryWeapons() {
        return weaponMap.get(WeaponCategory.PRIMARY_WEAPON);
    }

    @Override
    public List<Weapon> getSecondaryWeapons() {
        return weaponMap.get(WeaponCategory.SECONDARY_WEAPON);
    }

    @Override
    public List<Weapon> getUniqueAbility() {
        return weaponMap.get(WeaponCategory.UNIQUE_ABILITY);
    }

    @Override
    public List<Weapon> getGadgets() {
        return weaponMap.get(WeaponCategory.GADGET);
    }

    public static Map<WeaponCategory, List<Weapon>> getWeaponMap() {
        final Map<WeaponCategory, List<Weapon>> weaponMap = new HashMap<>();
        weaponMap.put(WeaponCategory.PRIMARY_WEAPON, new ArrayList<>());
        weaponMap.put(WeaponCategory.SECONDARY_WEAPON, new ArrayList<>());
        weaponMap.put(WeaponCategory.GADGET, new ArrayList<>());
        weaponMap.put(WeaponCategory.UNIQUE_ABILITY, new ArrayList<>());

        return weaponMap;
    }
}
