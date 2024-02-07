package com.trymad.model;

import javafx.scene.image.Image;

public record Operator(String name, Image image, Image icon) {

    public String getFormattedName() {
        return name().replace('Ã', 'A')
                     .replace('ä', 'a')
                     .replace('Ø', 'O')
                     .replace('ã', 'a')
                     .replace("\"", "")
                     .toLowerCase();
    }
}
