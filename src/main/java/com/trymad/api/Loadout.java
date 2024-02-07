package com.trymad.api;

import java.util.List;

import com.trymad.model.Weapon;

public interface Loadout {
    public List<Weapon> getPrimaryWeapons();

    public List<Weapon> getSecondaryWeapons();

    public List<Weapon> getUniqueAbility();

    public List<Weapon> getGadgets();
}
