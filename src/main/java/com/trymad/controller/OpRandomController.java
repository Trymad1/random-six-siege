package com.trymad.controller;

import org.json.JSONException;

import com.trymad.api.OperatorRandomizer;
import com.trymad.model.OperatorData;
import com.trymad.model.OperatorSide;
import com.trymad.model.Weapon;
import com.trymad.service.OperatorRandomizerService;
import com.trymad.service.OperatorDataService;
import com.trymad.util.ExceptionAlertFactory;
import com.trymad.util.OperatorsDirectoryNotFound;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class OpRandomController {

    private OperatorRandomizer randomizer;

    @FXML
    private ImageView gadgetImg, opIcon, opImage, primaryWeaponImg,
            secondaryWeaponImg, uniqueAbilityImg, randomImage,
            attackerImage, defenderImage;

    @FXML
    private Label gadgetLabel, gadgetType, opName, primaryWeaponLabel,
            primaryWeaponType, secondaryWeaponLabel, secondaryWeaponType,
            uniqueAbilityLabel, uniqueAbilityType;

    @FXML
    private AnchorPane mainPane, operatorPane, weaponPane, randomPane,
            attackerPane, defenderPane;

    @FXML
    private void randomMouseClick(MouseEvent event) {
        setOperatorData(randomizer.getRandomOperatorData());
    }

    @FXML
    private void attackerButtonPressed(MouseEvent event) {
        if (!randomizer.getSide().equals(OperatorSide.DEFENDER))
            return;

        swapOpacity(attackerImage, defenderImage);
        randomizer.setSide(OperatorSide.ATTACKER);
    }

    @FXML
    private void defenderButtonPressed(MouseEvent event) {
        if (!randomizer.getSide().equals(OperatorSide.ATTACKER))
            return;

        swapOpacity(defenderImage, attackerImage);
        randomizer.setSide(OperatorSide.DEFENDER);
    }

    private void swapOpacity(ImageView image1, ImageView image2) {
        final double opacity = image1.getOpacity();
        image1.setOpacity(image2.getOpacity());
        image2.setOpacity(opacity);
    }

    @FXML
    private void initialize() {

        final OperatorDataService operatorService = new OperatorDataService();
        try {
            randomizer = new OperatorRandomizerService(operatorService);
        } catch (OperatorsDirectoryNotFound e) {
            ExceptionAlertFactory.createAlert(e);
            System.exit(1);
        } catch (JSONException e) {
            ExceptionAlertFactory.createAlert(e);
            System.exit(1);
        }

        final OperatorData od = randomizer.getRandomOperatorData();
        setOperatorData(od);
        setAnimations();
    }

    // TODO fix hardcode setinfo in labels and image
    private void setOperatorData(OperatorData data) {
        opName.setText(data.operatorData().name().toUpperCase());
        opImage.setImage(data.operatorData().image());
        opIcon.setImage(data.operatorData().icon());

        final Weapon primaryWeapon = data.getFirstPrimaryWeapon();
        final Weapon secondaryWeapon = data.getFirstSecondaryWeapon();
        final Weapon gadget = data.getFirstGadget();
        final Weapon uniqueAbility = data.getFirstUniqueAbilities();

        if (gadget != null) {
            gadgetImg.setImage(gadget.image());
            gadgetLabel.setText(gadget.name());
            gadgetType.setText(getTypeForWeapon(gadget.type()));
        } else {
            gadgetImg.setImage(null);
            gadgetLabel.setText("");
            gadgetType.setText("");
        }

        if (primaryWeapon != null) {
            primaryWeaponImg.setImage(primaryWeapon.image());
            primaryWeaponLabel.setText(primaryWeapon.name());
            primaryWeaponType.setText(getTypeForWeapon(primaryWeapon.type()));
        } else {
            primaryWeaponImg.setImage(null);
            primaryWeaponLabel.setText("");
            primaryWeaponType.setText("");
        }

        if (secondaryWeapon != null) {
            secondaryWeaponImg.setImage(secondaryWeapon.image());
            secondaryWeaponLabel.setText(secondaryWeapon.name());
            secondaryWeaponType.setText(getTypeForWeapon(secondaryWeapon.type()));
        } else {
            secondaryWeaponImg.setImage(null);
            secondaryWeaponLabel.setText("");
            secondaryWeaponType.setText("");
        }

        if (uniqueAbility != null) {
            uniqueAbilityImg.setImage(uniqueAbility.image());
            uniqueAbilityLabel.setText(uniqueAbility.name());
            uniqueAbilityType.setText(getTypeForWeapon(uniqueAbility.type()));
        } else {
            uniqueAbilityImg.setImage(null);
            uniqueAbilityLabel.setText("");
            uniqueAbilityType.setText("");
        }

        final ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-1.0);
        colorAdjust.setContrast(-1.0);
        colorAdjust.setSaturation(0.0);
        uniqueAbilityImg.setEffect(colorAdjust);
    }

    private void setAnimations() {
        final ScaleTransition scaleInTransition = new ScaleTransition(Duration.millis(150), randomImage);
        scaleInTransition.setToX(1.2);
        scaleInTransition.setToY(1.2);

        final ScaleTransition scaleOutTransition = new ScaleTransition(Duration.millis(150), randomImage);
        scaleOutTransition.setToX(1.0);
        scaleOutTransition.setToY(1.0);

        final ScaleTransition scaleInTransition1 = new ScaleTransition(Duration.millis(150), attackerImage);
        scaleInTransition1.setToX(1.2);
        scaleInTransition1.setToY(1.2);

        final ScaleTransition scaleOutTransition1 = new ScaleTransition(Duration.millis(150), attackerImage);
        scaleOutTransition1.setToX(1.0);
        scaleOutTransition1.setToY(1.0);

        final ScaleTransition scaleInTransition2 = new ScaleTransition(Duration.millis(150), defenderImage);
        scaleInTransition2.setToX(1.2);
        scaleInTransition2.setToY(1.2);

        final ScaleTransition scaleOutTransition2 = new ScaleTransition(Duration.millis(150), defenderImage);
        scaleOutTransition2.setToX(1.0);
        scaleOutTransition2.setToY(1.0);

        randomPane.setOnMouseEntered(event -> {
            scaleInTransition.playFromStart();
        });

        randomPane.setOnMouseExited(event -> {
            scaleOutTransition.playFromStart();
        });

        attackerPane.setOnMouseEntered(event -> {
            scaleInTransition1.playFromStart();
        });

        attackerPane.setOnMouseExited(event -> {
            scaleOutTransition1.playFromStart();
        });

        defenderPane.setOnMouseEntered(event -> {
            scaleInTransition2.playFromStart();
        });

        defenderPane.setOnMouseExited(event -> {
            scaleOutTransition2.playFromStart();
        });
    }

    private String getTypeForWeapon(String weaponType) {
        return weaponType.toLowerCase().equals("none") ? "" : weaponType;
    }

}
