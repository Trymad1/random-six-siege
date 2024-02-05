package com.trymad.model;

import javafx.scene.image.Image;

public record Weapon(String name, String type, Image image) {

    public String getFormattedName() {
        return name().toLowerCase();
    }
}
