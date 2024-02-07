package com.trymad.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.trymad.api.Loadout;
import com.trymad.api.OperatorAPI;
import com.trymad.api.OperatorRandomizer;
import com.trymad.model.MapLoadout;
import com.trymad.model.OperatorData;
import com.trymad.model.Weapon;
import com.trymad.model.WeaponCategory;

public class FileOperatorRandomiser implements OperatorRandomizer {

    private List<OperatorData> operators;
    private final Random random;

    public FileOperatorRandomiser(OperatorAPI api) {
        random = new Random();
        operators = api.getOperators();
    }

    @Override
    public OperatorData getRandomOperatorData() {
        final int randomOperatorIndex = random.nextInt(0, operators.size());

        final OperatorData operatorData = operators.get(randomOperatorIndex);

        int randomPrimaryWeaponIndex = random.nextInt(0, operatorData.getPrimaryWeapons().size());
        int randomSecondaryWeaponIndex = random.nextInt(0, operatorData.getSecondaryWeapons().size());
        int randomGadgetIndex = random.nextInt(0, operatorData.getGadgets().size());
        int randomUniqueAbilityIndex = random.nextInt(0, operatorData.getUniqueAbilities().size());

        final List<Weapon> randomPrimaryWeapon = 
            Collections.singletonList(operatorData.getPrimaryWeapons().get(randomPrimaryWeaponIndex));
        
        final List<Weapon> randomSecondaryWeapon = 
            Collections.singletonList(operatorData.getSecondaryWeapons().get(randomSecondaryWeaponIndex));

        final List<Weapon> randomGadget =
            Collections.singletonList(operatorData.getGadgets().get(randomGadgetIndex));

        final List<Weapon> randomUniqueAbility =
            Collections.singletonList(operatorData.getUniqueAbilities().get(randomUniqueAbilityIndex));

        final Map<WeaponCategory, List<Weapon>> weaponMap = MapLoadout.getWeaponMap();

        weaponMap.get(WeaponCategory.GADGET).addAll(randomGadget);
        weaponMap.get(WeaponCategory.UNIQUE_ABILITY).addAll(randomUniqueAbility);
        weaponMap.get(WeaponCategory.SECONDARY_WEAPON).addAll(randomSecondaryWeapon);
        weaponMap.get(WeaponCategory.PRIMARY_WEAPON).addAll(randomPrimaryWeapon);

        final Loadout randomLoadout = new MapLoadout(weaponMap);
        return new OperatorData(operatorData.operatorData(), randomLoadout);
    }
    
}
