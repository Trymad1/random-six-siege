package com.trymad.controller;

import com.trymad.api.OperatorRandomizer;
import com.trymad.model.OperatorData;
import com.trymad.model.Weapon;
import com.trymad.service.FileOperatorRandomiser;
import com.trymad.service.OperatorDataService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class OpRandomController {

    public OperatorRandomizer randomizer;

    @FXML
    public ImageView gadgetImg;

    @FXML
    public Label gadgetLabel;

    @FXML
    public Label gadgetType;

    @FXML
    public ImageView opIcon;

    @FXML
    public ImageView opImage;

    @FXML
    public Label opName;

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
        opName.setText(data.operatorData().name());
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
    }

    private String getTypeForWeapon(String weaponType) {
        return weaponType.toLowerCase().equals("none") ? "" : weaponType;
    }

}

