package com.trymad.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;

import com.trymad.api.Loadout;
import com.trymad.api.OperatorAPI;
import com.trymad.api.OperatorRandomizer;
import com.trymad.model.MapLoadout;
import com.trymad.model.OperatorData;
import com.trymad.model.Weapon;
import com.trymad.model.WeaponCategory;
import com.trymad.util.JsonFileUtil;

public class FileOperatorRandomiser implements OperatorRandomizer {

    private String[] operatorNames;
    private final OperatorAPI operatorApi;
    private final Random random;

    private String previousOperator;

    public FileOperatorRandomiser(OperatorAPI api) {
        this.operatorApi = api;
        random = new Random();
        previousOperator = "none";

        final JSONObject jsonInfo = new JsonFileUtil().getOperatorNames();

        Iterator<String> keys = jsonInfo.keys();
        List<String> names = new ArrayList<>();
        while((keys.hasNext())) {
            names.add(jsonInfo.getString(keys.next()));
        }

        operatorNames = names.toArray(new String[names.size()]);
    }

    @Override
    public OperatorData getRandomOperatorData() {
        final int randomOperatorIndex = random.nextInt(0, operatorNames.length);
        String operatorName = operatorNames[randomOperatorIndex];
        while (operatorName.equals(previousOperator)) {
            operatorName = operatorNames[random.nextInt(0, operatorNames.length)];
        }

        OperatorData operatorData = operatorApi.getOperatorData(operatorName);
        Loadout loadout = operatorData.loadoutData();

        int randomPrimaryWeaponIndex = random.nextInt(0, loadout.getPrimaryWeapons().size());
        int randomSecondaryWeaponIndex = random.nextInt(0, loadout.getSecondaryWeapons().size());
        int randomGadgetIndex = random.nextInt(0, loadout.getGadgets().size());
        int randomUniqueAbilityIndex = random.nextInt(0, loadout.getUniqueAbulity().size());

        final List<Weapon> randomPrimaryWeapon = 
            Collections.singletonList(loadout.getPrimaryWeapons().get(randomPrimaryWeaponIndex));
        
        final List<Weapon> randomSecondaryWeapon = 
            Collections.singletonList(loadout.getSecondaryWeapons().get(randomSecondaryWeaponIndex));

        final List<Weapon> randomGadget =
            Collections.singletonList(loadout.getGadgets().get(randomGadgetIndex));

        final List<Weapon> randomUniqueAbility =
            Collections.singletonList(loadout.getUniqueAbulity().get(randomUniqueAbilityIndex));

        final Map<WeaponCategory, List<Weapon>> weaponMap = MapLoadout.getWeaponMap();

        weaponMap.get(WeaponCategory.GADGET).addAll(randomGadget);
        weaponMap.get(WeaponCategory.UNIQUE_ABILITY).addAll(randomUniqueAbility);
        weaponMap.get(WeaponCategory.SECONDARY_WEAPON).addAll(randomSecondaryWeapon);
        weaponMap.get(WeaponCategory.PRIMARY_WEAPON).addAll(randomPrimaryWeapon);

        final Loadout randomLoadout = new MapLoadout(weaponMap);
        return new OperatorData(operatorData.operatorData(), randomLoadout);
    }
    
}
