// File: org/letrancpe/carboncalculator/model/dto/Recommendation.java
package org.letrancpe.carboncalculator.model.dto;

/**
 * Recommendation (Data Transfer Object - Model)
 * Represents a single personalized recommendation for reducing carbon footprint.
 */
public class Recommendation {
    private String category;
    private String text;
    private String rationale;

    public Recommendation(String category, String text) {
        this.category = category;
        this.text = text;
    }

    public Recommendation(String category, String text, String rationale) {
        this.category = category;
        this.text = text;
        this.rationale = rationale;
    }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getRationale() { return rationale; }
    public void setRationale(String rationale) { this.rationale = rationale; }

    @Override
    public String toString() {
        // Updated for better display in a ListView
        return text + (rationale != null && !rationale.isEmpty() ? " (" + rationale + ")" : "");
    }
}
