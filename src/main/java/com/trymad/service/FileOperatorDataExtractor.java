package com.trymad.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.ImageIcon;

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
        System.out.println("hello");
        final URL uriPathToOpDirectory = 
            App.class.getResource(RESOURCE_RELATIVE_DIRECTORY_PATH + "/" + opFormattedName);
        if (uriPathToOpDirectory == null) return Optional.empty();
        System.out.println("finded");

        Image opImage = null;
        Image opIcon = null;
        String opName = null;

        try {
            System.out.println(uriPathToOpDirectory.toURI());
            Path pathToOpDirectory = Path.of(uriPathToOpDirectory.toURI());
            opImage = getOpImage(pathToOpDirectory, opFormattedName);
            System.out.println("image loaded");
            opIcon = getOpIcon(pathToOpDirectory, opFormattedName);
            System.out.println("icon loaded");
            opName = getOpName(pathToOpDirectory);
            System.out.println("name loaded");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return Optional.empty();
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

    private Image getOpImage(Path pathToOpDir, String formattedOpName) throws FileNotFoundException {
        return new Image(new FileInputStream(pathToOpDir + "/" + formattedOpName + "_Img" + FILE_EXTENSION));
    }
    
    private Image getOpIcon(Path pathToOpDir, String formattedOpName) throws FileNotFoundException {
        return new Image(new FileInputStream(pathToOpDir + "/" + formattedOpName + "_Icon" + FILE_EXTENSION));
    }

    private String getOpName(Path pathToOpDir) throws IOException {
        List<String> result = null;

        try (Stream<Path> walk = Files.walk(pathToOpDir)) {
            result = walk
            .filter(p -> !Files.isDirectory(p))
            .map(p -> p.toString())
            .filter(f -> f.endsWith(".name"))
            .collect(Collectors.toList());
        }
        
        final File nameFile = new File(result.get(0));
        final String opName = nameFile.getName().substring(0, nameFile.getName().indexOf("."));
        return opName;
    }

}
