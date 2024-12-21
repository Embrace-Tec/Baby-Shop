package com.babyshop.backend;

import javafx.beans.property.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Yasith Perera
 * @File: Variation
 * @mailto : yasithishara@gmail.com
 * @created : 2024-12-21, Saturday
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Variation {
    private IntegerProperty variationIdProperty = new SimpleIntegerProperty();
    private StringProperty variationTypeProperty = new SimpleStringProperty();
    private StringProperty variationValueProperty = new SimpleStringProperty();

    // Getters
    public int getVariationId() {
        return variationIdProperty.get();
    }

    public String getVariationType() {
        return variationTypeProperty.get();
    }

    public String getVariationValue() {
        return variationValueProperty.get();
    }

    // Setters
    public void setVariationId(int variationId) {
        this.variationIdProperty.set(variationId);
    }

    public void setVariationType(String variationType) {
        this.variationTypeProperty.set(variationType);
    }

    public void setVariationValue(String variationValue) {
        this.variationValueProperty.set(variationValue);
    }
}
