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

public class TransportPage {
    private TextField transportKmField;
    private UserData userData;

    public TransportPage(UserData userData) {
        this.userData = userData;
    }

    public Node createPage() {
        VBox page = createStyledPageVBox("Transportation Habits");

        Label descLabel = new Label("Enter your average weekly personal vehicle travel:");
        descLabel.setStyle("-fx-font-size: 15px; -fx-text-fill: " + AppConstants.TEXT_SECONDARY_COLOR + ";");
        transportKmField = AppConstants.createStyledInputField("e.g., 150 km");

        page.getChildren().addAll(descLabel, transportKmField);
        return page;
    }

    public boolean updateUserData() {
        try {
            String kmText = transportKmField.getText().trim();
            userData.setTransportKm(parseDouble(kmText, "Transport km"));
            return true;
        } catch (NumberFormatException e) {
            System.err.println("Input error on Transport Page: " + e.getMessage());
            return false;
        }
    }

    private double parseDouble(String text, String fieldName) {
        if (text.isEmpty()) return 0.0;
        double value = Double.parseDouble(text);
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