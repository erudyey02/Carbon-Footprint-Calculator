package com.cadion.carboncalculator;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AppConstants {

    // --- Emission Factors (kg CO2e per unit) ---
    public static final double ELECTRICITY_FACTOR_KWH = 0.5;
    public static final double TRANSPORTATION_FACTOR_KM = 0.21;
    public static final double WASTE_FACTOR_KG = 0.35;
    public static final double TREE_ABSORPTION_KG_CO2_PER_YEAR = 22.0;

    // --- Styling Constants (Monochrome Green Palette) ---
    public static final String BG_PRIMARY = "#E8F5E9";
    public static final String BG_CONTENT_CARD = "#FFFFFF";
    public static final String BTN_PRIMARY_BG = "#4CAF50";
    public static final String BTN_PRIMARY_HOVER_BG = "#388E3C";
    public static final String TEXT_PRIMARY_COLOR = "#1B5E20";
    public static final String TEXT_SECONDARY_COLOR = "#3E764D";
    public static final String ACCENT_COLOR = "#66BB6A";
    public static final String BORDER_COLOR_LIGHT = "#D0D0D0";
    public static final String ERROR_COLOR = "#D32F2F"; // Red for errors


    // --- UI Helper Methods (Optional, but good for consistency) ---
    public static TextField createStyledInputField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setPrefWidth(300);
        textField.setMaxWidth(300);
        textField.setFont(Font.font("Arial", 14));
        textField.setStyle(
                "-fx-background-radius: 20;" +
                        "-fx-border-color: #BDBDBD;" +
                        "-fx-border-radius: 20;" +
                        "-fx-padding: 10 15;" +
                        "-fx-text-fill: " + TEXT_PRIMARY_COLOR + ";" +
                        "-fx-prompt-text-fill: " + TEXT_SECONDARY_COLOR + ";"
        );
        Tooltip.install(textField, new Tooltip("Enter a numeric value. Leave blank if not applicable or 0."));
        return textField;
    }

    public static Button createStyledNavButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        String baseStyle = "-fx-text-fill: white;" +
                "-fx-background-radius: 25;" +
                "-fx-padding: 10 25;";
        button.setStyle("-fx-background-color: " + BTN_PRIMARY_BG + ";" + baseStyle);
        button.setOnMouseEntered(_ -> button.setStyle("-fx-background-color: " + BTN_PRIMARY_HOVER_BG + ";" + baseStyle));
        button.setOnMouseExited(_ -> button.setStyle("-fx-background-color: " + BTN_PRIMARY_BG + ";" + baseStyle));
        button.setOnMousePressed(_ -> { button.setScaleX(0.95); button.setScaleY(0.95); });
        button.setOnMouseReleased(_ -> { button.setScaleX(1.0); button.setScaleY(1.0); });
        return button;
    }

    public static Label createStyledResultLabel(String initialText) {
        Label label = new Label(initialText);
        label.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        label.setTextFill(javafx.scene.paint.Color.web(TEXT_PRIMARY_COLOR));
        return label;
    }

    public static HBox createSeparator() {
        HBox separator = new HBox();
        separator.setMinHeight(1);
        separator.setMaxHeight(1);
        separator.setStyle("-fx-background-color: " + BORDER_COLOR_LIGHT + ";");
        HBox.setMargin(separator, new javafx.geometry.Insets(10, 0, 10, 0));
        return separator;
    }
}