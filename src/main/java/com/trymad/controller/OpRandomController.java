package com.trymad.controller;

import com.trymad.model.OperatorData;
import com.trymad.service.OperatorDataService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class OpRandomController {

    @FXML
    public ImageView gadgetImg;

    @FXML
    public ImageView opIcon;

    @FXML
    public ImageView opImage;

    @FXML
    public Label opName;

    @FXML
    public ImageView primaryWeaponImg;

    @FXML
    public ImageView secondaryWeaponImg;

    @FXML
    public ImageView uniqueAbilityImg;

    @FXML
    public void initialize() {

        OperatorDataService operatorService = new OperatorDataService();
        OperatorData od = operatorService.getOperatorData("capitao");
        setOperatorData(od);
    }

    public void setOperatorData(OperatorData data) {
        opName.setText(data.operatorData().name());
        opImage.setImage(data.operatorData().image());
        opIcon.setImage(data.operatorData().icon());

        gadgetImg.setImage(data.loadoutData().getGadgets().get(0).image());
        primaryWeaponImg.setImage(data.loadoutData().getPrimaryWeapons().get(0).image());
        secondaryWeaponImg.setImage(data.loadoutData().getSecondaryWeapons().get(0).image());
        uniqueAbilityImg.setImage(data.loadoutData().getUniqueAbulity().get(0).image());

        System.out.println(data.loadoutData().getUniqueAbulity().get(0).image());
    }


}

