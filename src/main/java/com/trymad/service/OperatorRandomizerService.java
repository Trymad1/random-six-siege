package com.trymad.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

        final List<Weapon> randomPrimaryWeapon = getRandomWeaponAsList(
                getRandomWeapon(operatorData.getPrimaryWeapons()));

        final List<Weapon> randomSecondaryWeapon = getRandomWeaponAsList(
                getRandomWeapon(operatorData.getSecondaryWeapons()));

        final List<Weapon> randomGadget = getRandomWeaponAsList(
                getRandomWeapon(operatorData.getGadgets()));

        final List<Weapon> randomUniqueAbility = getRandomWeaponAsList(
                getRandomWeapon(operatorData.getUniqueAbilities()));

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

    private Optional<Weapon> getRandomWeapon(final List<Weapon> weaponsList) {
        final int listSize = weaponsList.size();
        if (listSize <= 0) {
            return Optional.empty();
        }
        int randomIndex = random.nextInt(0, listSize);
        return Optional.of(weaponsList.get(randomIndex));
    }

    private List<Weapon> getRandomWeaponAsList(Optional<Weapon> weapon) {
        if (weapon.isPresent())
            return Collections.singletonList(weapon.get());
        else
            return Collections.emptyList();
    }

    @Override
    public OperatorSide getSide() {
        return currentSide;
    }
}
