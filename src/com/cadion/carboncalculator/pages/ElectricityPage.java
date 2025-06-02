package com.cadion.carboncalculator.pages;

import com.cadion.carboncalculator.AppConstants;
import com.cadion.carboncalculator.UserData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ElectricityPage {
    private TextField electricityKwhField;
    private TextField numAcUnitsField;
    private TextField hoursAcUsageField;
    private UserData userData;

    public ElectricityPage(UserData userData) {
        this.userData = userData;
    }

    public Node createPage() {
        VBox page = createStyledPageVBox("Electricity Consumption");

        Label descLabel = new Label("Enter your average monthly electricity usage:");
        descLabel.setStyle("-fx-font-size: 15px; -fx-text-fill: " + AppConstants.TEXT_SECONDARY_COLOR + ";");
        electricityKwhField = AppConstants.createStyledInputField("e.g., 300 kWh");

        Label altDescLabel = new Label("\nOR, if you don't know your kWh, provide AC details (Simplified Example):");
        altDescLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: " + AppConstants.TEXT_SECONDARY_COLOR + "; -fx-font-style: italic;");
        numAcUnitsField = AppConstants.createStyledInputField("Number of AC units (e.g., 1)");
        hoursAcUsageField = AppConstants.createStyledInputField("Avg. daily AC use per unit (hours, e.g., 4)");

        page.getChildren().addAll(descLabel, electricityKwhField, altDescLabel, numAcUnitsField, hoursAcUsageField);
        return page;
    }

    // Called before navigating away from this page
    public boolean updateUserData() {
        try {
            String kwhText = electricityKwhField.getText().trim();
            String numAcText = numAcUnitsField.getText().trim();
            String hoursAcText = hoursAcUsageField.getText().trim();

            if (!kwhText.isEmpty()) {
                userData.setElectricityKwh(parseDouble(kwhText, "Electricity kWh"));
                userData.setUseAlternativeElectricity(false);
            } else if (!numAcText.isEmpty() && !hoursAcText.isEmpty()) {
                userData.setNumAcUnits(parseInt(numAcText, "Number of AC Units"));
                userData.setHoursAcUsage(parseDouble(hoursAcText, "Hours AC Usage"));
                userData.setUseAlternativeElectricity(true);
                userData.setElectricityKwh(0); // Clear direct kWh if using alt
            } else {
                // If all are empty, treat as 0. Or you could require at least one set of inputs.
                userData.setElectricityKwh(0);
                userData.setNumAcUnits(0);
                userData.setHoursAcUsage(0);
                userData.setUseAlternativeElectricity(false);
            }
            return true;
        } catch (NumberFormatException e) {
            // Error handling should be done in the main app or a dedicated error label on the page
            System.err.println("Input error on Electricity Page: " + e.getMessage());
            return false; // Indicate validation failure
        }
    }

    private double parseDouble(String text, String fieldName) {
        if (text.isEmpty()) return 0.0;
        double value = Double.parseDouble(text);
        if (value < 0) throw new NumberFormatException(fieldName + " cannot be negative.");
        return value;
    }
    private int parseInt(String text, String fieldName) {
        if (text.isEmpty()) return 0;
        int value = Integer.parseInt(text);
        if (value < 0) throw new NumberFormatException(fieldName + " cannot be negative.");
        return value;
    }


    private VBox createStyledPageVBox(String titleText) {
        VBox page = new VBox(18);
        page.setAlignment(Pos.TOP_CENTER);
        page.setPadding(new Insets(25, 40, 25, 40));
        page.setStyle("-fx-background-color: " + AppConstants.BG_CONTENT_CARD + "; -fx-background-radius: 10; -fx-border-color: "+AppConstants.BORDER_COLOR_LIGHT+"; -fx-border-radius: 10;");

        Text title = new Text(titleText);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        title.setFill(javafx.scene.paint.Color.web(AppConstants.TEXT_PRIMARY_COLOR));
        page.getChildren().add(title);
        return page;
    }
}