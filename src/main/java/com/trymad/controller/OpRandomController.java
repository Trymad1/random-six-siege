package com.trymad.controller;

import com.trymad.api.Loadout;
import com.trymad.model.Operator;
import com.trymad.service.FileOperatorDataExtractor;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class OpRandomController {

    @FXML
    public ImageView opIcon;

    @FXML
    public ImageView opImage;

    @FXML
    public Label opName;

    @FXML
    public void initialize() {
        Operator op = new FileOperatorDataExtractor().extractOperatorByName("nokk").get();
        opIcon.setImage(op.icon());
        opImage.setImage(op.image());
        opName.setText(op.name());

        Loadout loadout = new FileOperatorDataExtractor().extractLoadoutByName("nokk").get();
        System.out.println(loadout.getGadgets());
        System.out.println(loadout.getPrimaryWeapons());
        System.out.println(loadout.getSecondaryWeapons());
        System.out.println(loadout.getUniqueAbulity());
    }


}

