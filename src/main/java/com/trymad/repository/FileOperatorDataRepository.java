package com.trymad.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;

import com.trymad.App;
import com.trymad.api.JsonUtil;
import com.trymad.api.Loadout;
import com.trymad.api.OperatorDataRepository;
import com.trymad.model.MapLoadout;
import com.trymad.model.Operator;
import com.trymad.model.Weapon;
import com.trymad.model.WeaponCategory;
import com.trymad.util.DirectoryUtils;
import com.trymad.util.JsonFileUtil;

import javafx.scene.image.Image;

public class FileOperatorDataRepository implements OperatorDataRepository {

    private final JsonUtil jsonUtil = new JsonFileUtil();

    @Override
    public Optional<Operator> extractOperatorByName(String opFormattedName) {

        if (!opDirectoryIsExist(opFormattedName)) return Optional.empty();
        final JSONObject dataJson = jsonUtil.getOperatorJson(opFormattedName);

        final Image opImage = getOpImage(dataJson);
        final Image opIcon = getOpIcon(dataJson);
        final String opName = getOpName(dataJson);

        return Optional.of(new Operator(opName, opImage, opIcon));
    }

    @Override
    public Optional<Loadout> extractLoadoutByName(String opFormattedName) {

        if (!opDirectoryIsExist(opFormattedName)) return Optional.empty();
        final JSONObject jsonData = jsonUtil.getOperatorJson(opFormattedName);
        
        final Map<WeaponCategory, List<Weapon>> weaponMap = MapLoadout.getWeaponMap();
        
        for (WeaponCategory category : WeaponCategory.values()) {
            weaponMap.get(category).addAll(getWeaponList(category, jsonData));
        }

        return Optional.of(new MapLoadout(weaponMap));
    }

    private List<Weapon> getWeaponList(WeaponCategory category, JSONObject jsonData) {
        final List<Weapon> weaponList = new ArrayList<>();
        final JSONObject weaponsCategoryJson = jsonData.getJSONObject("loadout")
            .getJSONObject(category.getFormattedName());
        
        final Iterator<String> weaponsIterator = weaponsCategoryJson.keys();
        while(weaponsIterator.hasNext()) {
            final String weaponFormattedName = weaponsIterator.next();
            weaponList.add(getWeapon(weaponFormattedName, category, jsonData));
        }

        return weaponList;
    }

    private Weapon getWeapon(
        String weaponFormattedName, WeaponCategory category, JSONObject jsonData) {
        final JSONObject weaponJson = jsonData.getJSONObject("loadout")
        .getJSONObject(category.getFormattedName())
        .getJSONObject(weaponFormattedName);

        final String weaponName = weaponJson.getString("name");
        final String weaponType = weaponJson.getString("type");
        final Image weaponImage = 
            getWeaponImage(jsonData.getString("formattedName"), category, weaponJson);

        return new Weapon(weaponName, weaponType, weaponImage);
    }

    private boolean opDirectoryIsExist(String opFormattedName) {
        return App.class.getResource(DirectoryUtils.RELATIVE_DIRECTORY_PATH + "/" + opFormattedName) 
            == null ? false : true;

    }

    private Image getOpImage(JSONObject dataJson)  {
        return new Image(App.class.getResourceAsStream(
            DirectoryUtils.RELATIVE_DIRECTORY_PATH + "/" 
            + dataJson.getString("formattedName") + "/"
            + dataJson.getString("image")));
    }
    
    private Image getOpIcon(JSONObject dataJson) {
        return new Image(App.class.getResourceAsStream(
            DirectoryUtils.RELATIVE_DIRECTORY_PATH + "/" 
            + dataJson.getString("formattedName") + "/"
            + dataJson.getString("icon")));
    }

    private String getOpName(JSONObject dataJson) {
        return dataJson.getString("name");
    }

    private Image getWeaponImage(String opFormattedName, WeaponCategory type, JSONObject weaponJson) {
        return new Image(App.class.getResourceAsStream(
            DirectoryUtils.RELATIVE_DIRECTORY_PATH + "/"
            + opFormattedName + "/"
            + "loadout/"
            + type.getFormattedName() + "/"
            + weaponJson.getString("name").toLowerCase().replace("\"", "") + "/"
            + weaponJson.getString("image")));
    }
}
