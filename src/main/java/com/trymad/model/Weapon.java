package com.trymad.model;

import javafx.scene.image.Image;

public record Weapon(String name, String type, Image image) {

    public String getFormattedName() {
        return name().toLowerCase();
    }

    @Override
    public String name() {
        return name.replace("_", " ").toUpperCase();
    }

    @Override
    public String type() {
        return type.replace("_", " ").toUpperCase();
    }
}
