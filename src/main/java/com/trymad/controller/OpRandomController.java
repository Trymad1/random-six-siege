package com.trymad.controller;

import java.io.File;

import com.trymad.api.OperatorRandomizer;
import com.trymad.model.OperatorData;
import com.trymad.model.Weapon;
import com.trymad.service.FileOperatorRandomiser;
import com.trymad.service.OperatorDataService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class OpRandomController {

    public OperatorRandomizer randomizer;

    @FXML
    public ImageView gadgetImg;

    @FXML
    public Label gadgetLabel;

    @FXML
    public Label gadgetType;

    @FXML
    public AnchorPane mainPane;

    @FXML
    public ImageView opIcon;

    @FXML
    public ImageView opImage;

    @FXML
    public Label opName;

    @FXML
    public AnchorPane operatorPane;

    @FXML
    public ImageView primaryWeaponImg;

    @FXML
    public Label primaryWeaponLabel;

    @FXML
    public Label primaryWeaponType;

    @FXML
    public Button randomButton;

    @FXML
    public ImageView secondaryWeaponImg;

    @FXML
    public Label secondaryWeaponLabel;

    @FXML
    public Label secondaryWeaponType;

    @FXML
    public ImageView uniqueAbilityImg;

    @FXML
    public Label uniqueAbilityLabel;

    @FXML
    public Label uniqueAbilityType;

    @FXML
    public AnchorPane weaponPane;

    @FXML
    public void randomButtonPressed(ActionEvent event) {
        setOperatorData(randomizer.getRandomOperatorData());
    }

    public void initialize() {

        OperatorDataService operatorService = new OperatorDataService();
        randomizer = new FileOperatorRandomiser(operatorService);
        OperatorData od = randomizer.getRandomOperatorData();
        setOperatorData(od);

    }

    public void setOperatorData(OperatorData data) {
        opName.setText(data.operatorData().name().toUpperCase());
        opImage.setImage(data.operatorData().image());
        opIcon.setImage(data.operatorData().icon());

        final Weapon primaryWeapon = data.getFirstPrimaryWeapon();
        final Weapon secondaryWeapon = data.getFirstSecondaryWeapon();
        final Weapon gadget = data.getFirstGadget();
        final Weapon uniqueAbility = data.getFirstUniqueAbilities();

        gadgetImg.setImage(gadget.image());
        primaryWeaponImg.setImage(primaryWeapon.image());
        secondaryWeaponImg.setImage(secondaryWeapon.image());
        uniqueAbilityImg.setImage(uniqueAbility.image());

        gadgetLabel.setText(gadget.name());
        gadgetType.setText(getTypeForWeapon(gadget.type()));
        
        primaryWeaponLabel.setText(primaryWeapon.name());
        primaryWeaponType.setText(getTypeForWeapon(primaryWeapon.type()));

        secondaryWeaponLabel.setText(secondaryWeapon.name());
        secondaryWeaponType.setText(getTypeForWeapon(secondaryWeapon.type()));

        uniqueAbilityLabel.setText(uniqueAbility.name());
        uniqueAbilityType.setText(getTypeForWeapon(uniqueAbility.type()));

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-1.0); // Устанавливаем яркость на минимум
        colorAdjust.setContrast(-1.0); // Устанавливаем контрастность на минимум
        colorAdjust.setSaturation(0.0); // Устанавливаем насыщенность на ноль
        uniqueAbilityImg.setEffect(colorAdjust);
    }

    private String getTypeForWeapon(String weaponType) {
        return weaponType.toLowerCase().equals("none") ? "" : weaponType;
    }

}

