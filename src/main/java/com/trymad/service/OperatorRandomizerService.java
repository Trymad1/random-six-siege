package com.trymad.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.trymad.api.Loadout;
import com.trymad.api.OperatorAPI;
import com.trymad.api.OperatorRandomizer;
import com.trymad.model.MapLoadout;
import com.trymad.model.OperatorData;
import com.trymad.model.OperatorSide;
import com.trymad.model.Weapon;
import com.trymad.model.WeaponCategory;

public class OperatorRandomizerService implements OperatorRandomizer {

    private List<OperatorData> operators;
    private List<OperatorData> defenders;
    private List<OperatorData> attackers;

    private final Random random;
    private OperatorSide currentSide;

    public OperatorRandomizerService(OperatorAPI api) {
        currentSide = OperatorSide.DEFENDER;
        random = new Random();
        operators = api.getOperators();
        defenders = getOperatorsBySide(operators, OperatorSide.DEFENDER);
        attackers = getOperatorsBySide(operators, OperatorSide.ATTACKER);
    }

    @Override
    public void setSide(OperatorSide side) {
        this.currentSide = side;
    }

    @Override
    public OperatorData getRandomOperatorData() {
        final List<OperatorData> operatorsPull = getChoosedSideOperators();
        final int randomOperatorIndex = random.nextInt(0, operatorsPull.size());

        final OperatorData operatorData = operatorsPull.get(randomOperatorIndex);

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

    private List<OperatorData> getOperatorsBySide(List<OperatorData> allOperators, OperatorSide side) {
        return allOperators.stream()
        .filter(operator -> operator.getOperatorSide().equals(side))
        .collect(Collectors.toList());
    }

    private List<OperatorData> getChoosedSideOperators() {
        return currentSide.equals(OperatorSide.ATTACKER) ? attackers : defenders;
    }

    @Override
    public OperatorSide getSide() {
        return currentSide;
    }
}
