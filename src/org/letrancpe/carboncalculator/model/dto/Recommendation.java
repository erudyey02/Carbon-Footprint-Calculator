// File: org/letrancpe/carboncalculator/model/dto/Recommendation.java
package org.letrancpe.carboncalculator.model.dto;

/**
 * Recommendation (Data Transfer Object - Model)
 * Represents a single personalized recommendation for reducing carbon footprint.
 */
public class Recommendation {
    private String category; // Example: Housing, Energy, Diet, Transport, Waste, Goods
    private String text; // The text of the recommendation.
    private String rationale; // Optional: Brief explanation or link to further information.

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
        return "[" + category + "] " + text + (rationale != null ? " (" + rationale + ")" : "");
    }
}
