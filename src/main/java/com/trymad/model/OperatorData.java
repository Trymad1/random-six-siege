package com.trymad.model;

import java.util.List;

import com.trymad.api.Loadout;

import javafx.scene.image.Image;

public record OperatorData(Operator operatorData, Loadout loadoutData) {

    @Override
    public final String toString() {
        return operatorData + "\n" + loadoutData;
    }

    public final String getOperatorName() {
        return operatorData.name();
    }

    public final OperatorSide getOperatorSide() {
        return operatorData.side();
    }

    public final String getOperatorFormattedName() {
        return operatorData.getFormattedName();
    }

    public final Image getOperatorImage() {
        return operatorData.image();
    }

    public final Image getOperatorIcon() {
        return operatorData.icon();
    }

    public final List<Weapon> getPrimaryWeapons() {
        return loadoutData.getPrimaryWeapons();
    }

    public final List<Weapon> getSecondaryWeapons() {
        return loadoutData.getSecondaryWeapons();
    }

    public final List<Weapon> getGadgets() {
        return loadoutData.getGadgets();
    }

    public final List<Weapon> getUniqueAbilities() {
        return loadoutData.getUniqueAbility();
    }

    public final Weapon getFirstUniqueAbilities() {
        final List<Weapon> w = loadoutData.getUniqueAbility();
        return w.size() == 0 ? null : w.get(0);
    }

    public final Weapon getFirstPrimaryWeapon() {
        final List<Weapon> w = loadoutData.getPrimaryWeapons();
        return w.size() == 0 ? null : w.get(0);
    }

    public final Weapon getFirstSecondaryWeapon() {
        final List<Weapon> w = loadoutData.getSecondaryWeapons();
        return w.size() == 0 ? null : w.get(0);
    }

    public final Weapon getFirstGadget() {
        final List<Weapon> w = loadoutData.getGadgets();
        return w.size() == 0 ? null : w.get(0);
    }
}
