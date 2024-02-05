package com.trymad.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;

import com.trymad.App;
import com.trymad.api.Loadout;
import com.trymad.api.OperatorDataExtractor;
import com.trymad.model.MapLoadout;
import com.trymad.model.Operator;
import com.trymad.model.Weapon;
import com.trymad.model.WeaponCategory;

import javafx.scene.image.Image;

public class FileOperatorDataExtractor implements OperatorDataExtractor {

    private final String RESOURCE_RELATIVE_DIRECTORY_PATH = "operatorsData";

    @Override
    public Optional<Operator> extractOperatorByName(String opFormattedName) {

        if (!opDirectoryIsExist(opFormattedName)) return Optional.empty();
        final JSONObject dataJson = getJsonData(opFormattedName);

        final Image opImage = getOpImage(dataJson);
        final Image opIcon = getOpIcon(dataJson);
        final String opName = getOpName(dataJson);

        return Optional.of(new Operator(opName, opImage, opIcon));
    }

    @Override
    public Optional<Loadout> extractLoadoutByName(String opFormattedName) {

        if (!opDirectoryIsExist(opFormattedName)) return Optional.empty();
        final JSONObject jsonData = getJsonData(opFormattedName);
        
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

    private JSONObject getJsonData(String opFormattedName) {
        final InputStream inputStream = App.class.getResourceAsStream(
            RESOURCE_RELATIVE_DIRECTORY_PATH + "/" + opFormattedName + "/data.json");
        final StringBuilder content = new StringBuilder();

        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch(IOException e) {
            return new JSONObject();
        }

        return new JSONObject(content.toString());
    }

    private boolean opDirectoryIsExist(String opFormattedName) {
        return App.class.getResource(RESOURCE_RELATIVE_DIRECTORY_PATH + "/" + opFormattedName) 
            == null ? false : true;

    }

    private Image getOpImage(JSONObject dataJson)  {
        return new Image(App.class.getResourceAsStream(
            RESOURCE_RELATIVE_DIRECTORY_PATH + "/" 
            + dataJson.getString("formattedName") + "/"
            + dataJson.getString("image")));
    }
    
    private Image getOpIcon(JSONObject dataJson) {
        return new Image(App.class.getResourceAsStream(
            RESOURCE_RELATIVE_DIRECTORY_PATH + "/" 
            + dataJson.getString("formattedName") + "/"
            + dataJson.getString("icon")));
    }

    private String getOpName(JSONObject dataJson) {
        return dataJson.getString("name");
    }

    private Image getWeaponImage(String opFormattedName, WeaponCategory type, JSONObject weaponJson) {
        return new Image(App.class.getResourceAsStream(
            RESOURCE_RELATIVE_DIRECTORY_PATH + "/"
            + opFormattedName + "/"
            + "loadout/"
            + type.getFormattedName() + "/"
            + weaponJson.getString("name").toLowerCase().replace("\"", "") + "/"
            + weaponJson.getString("image")));
    }
}
