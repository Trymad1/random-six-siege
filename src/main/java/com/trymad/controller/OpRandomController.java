package com.trymad.controller;


import com.trymad.api.OperatorRandomizer;
import com.trymad.model.OperatorData;
import com.trymad.model.OperatorSide;
import com.trymad.model.Weapon;
import com.trymad.service.FileOperatorRandomiser;
import com.trymad.service.OperatorDataService;
import com.trymad.util.OperatorsDirectoryNotFound;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


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
    public Button defenderButton, attackerButton;

    @FXML
    public ImageView secondaryWeaponImg;

    @FXML
    public Label secondaryWeaponLabel;

    @FXML
    public Label secondaryWeaponType;

    @FXML
    public ImageView uniqueAbilityImg, randomImage, attackerImage, defenderImage;

    @FXML
    public Label uniqueAbilityLabel;

    @FXML
    public Label uniqueAbilityType;

    @FXML
    public AnchorPane weaponPane, randomPane, attackerPane, defenderPane;

    @FXML
    public void randomButtonPressed(MouseEvent event) {
        setOperatorData(randomizer.getRandomOperatorData());
    }
    
    @FXML
    public void attackerButtonPressed(MouseEvent event) {
        if (!randomizer.getSide().equals(OperatorSide.DEFENDER)) return;
        
        swapOpacity(attackerImage, defenderImage);
        randomizer.setSide(OperatorSide.ATTACKER);
    }

    @FXML
    public void defenderButtonPressed(MouseEvent event) {
        if (!randomizer.getSide().equals(OperatorSide.ATTACKER)) return;
        
        swapOpacity(defenderImage, attackerImage);
        randomizer.setSide(OperatorSide.DEFENDER);
    }

    private void swapOpacity(ImageView image1, ImageView image2) {
        final double opacity = image1.getOpacity();
        image1.setOpacity(image2.getOpacity());
        image2.setOpacity(opacity);
    } 

    public void initialize() {

        final OperatorDataService operatorService = new OperatorDataService();
        try {
            randomizer = new FileOperatorRandomiser(operatorService);
        } catch (OperatorsDirectoryNotFound e) {
            final Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Loading error");
            alert.setHeaderText("Can`t load operators data from operatorsData directory");
            alert.setContentText("Update operator data by running OperatorDataLoader.exe in the same directory as the jar file");
            alert.showAndWait();
            System.exit(1);
        }

        final OperatorData od = randomizer.getRandomOperatorData();
        setOperatorData(od);

        ScaleTransition scaleInTransition = new ScaleTransition(Duration.millis(150), randomImage);
        scaleInTransition.setToX(1.2);
        scaleInTransition.setToY(1.2);

        ScaleTransition scaleOutTransition = new ScaleTransition(Duration.millis(150), randomImage);
        scaleOutTransition.setToX(1.0);
        scaleOutTransition.setToY(1.0);

        ScaleTransition scaleInTransition1 = new ScaleTransition(Duration.millis(150), attackerImage);
        scaleInTransition1.setToX(1.2);
        scaleInTransition1.setToY(1.2);

        ScaleTransition scaleOutTransition1 = new ScaleTransition(Duration.millis(150), attackerImage);
        scaleOutTransition1.setToX(1.0);
        scaleOutTransition1.setToY(1.0);

        ScaleTransition scaleInTransition2 = new ScaleTransition(Duration.millis(150), defenderImage);
        scaleInTransition2.setToX(1.2);
        scaleInTransition2.setToY(1.2);

        ScaleTransition scaleOutTransition2 = new ScaleTransition(Duration.millis(150), defenderImage);
        scaleOutTransition2.setToX(1.0);
        scaleOutTransition2.setToY(1.0);

        // Устанавливаем обработчик события наведения мыши на изображение
        randomPane.setOnMouseEntered(event -> {
            // Запускаем анимацию увеличения масштаба
            scaleInTransition.playFromStart();
        });

        // Устанавливаем обработчик события ухода мыши с изображения
        randomPane.setOnMouseExited(event -> {
            // Запускаем анимацию уменьшения масштаба
            scaleOutTransition.playFromStart();
        });

        attackerPane.setOnMouseEntered(event -> {
            // Запускаем анимацию увеличения масштаба
            scaleInTransition1.playFromStart();
        });

        // Устанавливаем обработчик события ухода мыши с изображения
        attackerPane.setOnMouseExited(event -> {
            // Запускаем анимацию уменьшения масштаба
            scaleOutTransition1.playFromStart();
        });

        defenderPane.setOnMouseEntered(event -> {
            // Запускаем анимацию увеличения масштаба
            scaleInTransition2.playFromStart();
        });

        // Устанавливаем обработчик события ухода мыши с изображения
        defenderPane.setOnMouseExited(event -> {
            // Запускаем анимацию уменьшения масштаба
            scaleOutTransition2.playFromStart();
        });
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

        final ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-1.0); 
        colorAdjust.setContrast(-1.0); 
        colorAdjust.setSaturation(0.0); 
        uniqueAbilityImg.setEffect(colorAdjust);

        System.out.println(data.getOperatorSide());
    }

    private String getTypeForWeapon(String weaponType) {
        return weaponType.toLowerCase().equals("none") ? "" : weaponType;
    }

}

