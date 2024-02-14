package com.trymad.model;

import java.util.List;

import com.trymad.api.Loadout;

import javafx.scene.image.Image;

public record OperatorData (Operator operatorData, Loadout loadoutData) {
    
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
        return loadoutData.getUniqueAbility().get(0);
    }

    public final Weapon getFirstPrimaryWeapon() {
        return loadoutData.getPrimaryWeapons().get(0);
    }

    public final Weapon getFirstSecondaryWeapon() {
        return loadoutData.getSecondaryWeapons().get(0);
    }

    public final Weapon getFirstGadget() {
        return loadoutData.getGadgets().get(0);
    }
}
