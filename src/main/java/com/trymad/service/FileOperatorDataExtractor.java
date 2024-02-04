package com.trymad.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilterReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.trymad.App;
import com.trymad.api.OperatorDataExtractor;
import com.trymad.model.Operator;
import com.trymad.model.Weapon;
import com.trymad.model.WeaponTypes;

import javafx.scene.image.Image;

public class FileOperatorDataExtractor implements OperatorDataExtractor {

    private final String RESOURCE_RELATIVE_DIRECTORY_PATH = "operatorsData";
    private final String FILE_EXTENSION = ".png";

    @Override
    public Optional<Operator> extractOperatorByName(String opFormattedName) {
        final URL uriPathToOpDirectory = 
            App.class.getResource(RESOURCE_RELATIVE_DIRECTORY_PATH + "/" + opFormattedName);
        if (uriPathToOpDirectory == null) return Optional.empty();

        Image opImage = null;
        Image opIcon = null;
        String opName = "hyi";

        try {
            opImage = getOpImage(opFormattedName);
            opIcon = getOpIcon(opFormattedName);
            opName = getOpName(opFormattedName);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(new Operator(opName, opImage, opIcon));
    }

    @Override
    public Optional<Weapon> extractWeaponByName(
        String opFormattedName, WeaponTypes weaponType, String weaponFormattedName) {
        return null;
    }

    private Image getOpImage(String formattedOpName) throws FileNotFoundException {
        new Image(App.class.getResourceAsStream(RESOURCE_RELATIVE_DIRECTORY_PATH + "/" + formattedOpName)).getUrl();
        return new Image(
            App.class.getResourceAsStream(RESOURCE_RELATIVE_DIRECTORY_PATH + "/" + formattedOpName + "/" + formattedOpName + "_Img" + FILE_EXTENSION));
    }
    
    private Image getOpIcon(String formattedOpName) throws FileNotFoundException {
        return new Image(
            App.class.getResourceAsStream(RESOURCE_RELATIVE_DIRECTORY_PATH + "/" + formattedOpName + "/" + formattedOpName + "_Icon" + FILE_EXTENSION));
    }

    private String getOpName(String formattedOpName) throws IOException {
        InputStream stream = App.class.getResourceAsStream(RESOURCE_RELATIVE_DIRECTORY_PATH + "/" + formattedOpName + "/" + "name.txt");

        String name = "NONE";

        try {
            byte[] data = stream.readAllBytes();
            name = new String(data, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stream.close();
        }

        return name;
    }
}
