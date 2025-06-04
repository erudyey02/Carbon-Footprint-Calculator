// File: org/letrancpe/carboncalculator/view/DietPage.java
package org.letrancpe.carboncalculator.view;

import org.letrancpe.carboncalculator.controller.MainController;
import org.letrancpe.carboncalculator.model.UserData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * DietPage (View component)
 * Collects user data related to dietary habits.
 */
public class DietPage {

    private MainController controller;
    private UserData userData;

    // UI Elements for Diet Inputs
    private ComboBox<String> redMeatFrequencyComboBox;
    private ComboBox<String> poultryFrequencyComboBox;
    private ComboBox<String> fishSeafoodFrequencyComboBox;
    private TextField dailyRiceServingsField;
    private ComboBox<String> riceTypeComboBox;
    private ComboBox<String> dietaryPatternComboBox;
    private TextField weeklyFoodWasteField;
    private ComboBox<String> localFoodSourcingComboBox;

    private Label errorMessageLabel;

    public DietPage(MainController controller, UserData userData) {
        this.controller = controller;
        this.userData = userData;
    }

    public Node getView() {
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_LEFT);
        mainLayout.setStyle("-fx-background-color: #F1F8E9; -fx-border-color: #C8E6C9; -fx-border-width: 1; -fx-border-radius: 5;");

        Label title = new Label("Dietary Habits");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        GridPane dietGrid = createStyledGridPane();

        // --- Meat, Poultry, Fish Frequency ---
        String[] frequencyOptions = {"Daily", "3-5 times/week", "1-2 times/week", "Rarely/Never"};

        redMeatFrequencyComboBox = new ComboBox<>();
        redMeatFrequencyComboBox.getItems().addAll(frequencyOptions);
        redMeatFrequencyComboBox.setPromptText("Select frequency");
        addLabeledControl(dietGrid, "Red Meat Consumption:", redMeatFrequencyComboBox, 0, "Frequency of consuming red meat (beef, pork, etc.).");

        poultryFrequencyComboBox = new ComboBox<>();
        poultryFrequencyComboBox.getItems().addAll(frequencyOptions);
        poultryFrequencyComboBox.setPromptText("Select frequency");
        addLabeledControl(dietGrid, "Poultry Consumption:", poultryFrequencyComboBox, 1, "Frequency of consuming poultry (chicken, turkey, etc.).");

        fishSeafoodFrequencyComboBox = new ComboBox<>();
        fishSeafoodFrequencyComboBox.getItems().addAll(frequencyOptions);
        fishSeafoodFrequencyComboBox.setPromptText("Select frequency");
        addLabeledControl(dietGrid, "Fish/Seafood Consumption:", fishSeafoodFrequencyComboBox, 2, "Frequency of consuming fish or seafood.");

        // --- Rice Consumption ---
        dailyRiceServingsField = new TextField();
        dailyRiceServingsField.setPromptText("e.g., 2");
        addLabeledControl(dietGrid, "Daily Rice Servings:", dailyRiceServingsField, 3, "Average number of rice servings per day (1 serving ~60g uncooked).");

        riceTypeComboBox = new ComboBox<>();
        riceTypeComboBox.getItems().addAll("Irrigated", "Rainfed", "Unknown/Average");
        riceTypeComboBox.setPromptText("Select rice type");
        addLabeledControl(dietGrid, "Rice Type (if known):", riceTypeComboBox, 4, "Type of rice predominantly consumed, if known.");


        // --- Dietary Pattern & Food Waste ---
        dietaryPatternComboBox = new ComboBox<>();
        dietaryPatternComboBox.getItems().addAll("Meat-eater (Typical)", "Reduced Meat / Flexitarian", "Vegetarian (No meat/fish)", "Vegan (No animal products)");
        dietaryPatternComboBox.setPromptText("Select dietary pattern");
        addLabeledControl(dietGrid, "Overall Dietary Pattern:", dietaryPatternComboBox, 5, "Select the pattern that best describes the overall diet.");

        weeklyFoodWasteField = new TextField();
        weeklyFoodWasteField.setPromptText("e.g., 0.5");
        addLabeledControl(dietGrid, "Weekly Food Waste (kg):", weeklyFoodWasteField, 6, "Estimated kilograms of food wasted per week.");

        localFoodSourcingComboBox = new ComboBox<>();
        localFoodSourcingComboBox.getItems().addAll("Often", "Sometimes", "Rarely", "Not Sure/Don't Track");
        localFoodSourcingComboBox.setPromptText("Select frequency");
        addLabeledControl(dietGrid, "Local Food Sourcing (Optional):", localFoodSourcingComboBox, 7, "Frequency of choosing locally sourced food items. (For recommendations)");


        // --- Error Message and Save Button ---
        errorMessageLabel = new Label();
        errorMessageLabel.setStyle("-fx-text-fill: red;");

        Button saveButton = new Button("Save & Next (Transport)");
        saveButton.setStyle("-fx-background-color: #66BB6A; -fx-text-fill: white; -fx-padding: 8 15; -fx-background-radius: 5;");
        saveButton.setOnAction(e -> saveDataAndProcess());

        VBox buttonAndErrorBox = new VBox(10, errorMessageLabel, saveButton);
        buttonAndErrorBox.setAlignment(Pos.CENTER_LEFT);
        buttonAndErrorBox.setPadding(new Insets(10,0,0,0));

        mainLayout.getChildren().addAll(title, dietGrid, buttonAndErrorBox);

        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #F1F8E9;");

        return scrollPane;
    }

    private GridPane createStyledGridPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10,0,10,0));
        return grid;
    }

    private void addLabeledControl(GridPane grid, String labelText, Node control, int rowIndex, String tooltipText) {
        Label label = new Label(labelText);
        if (control instanceof Control) {
            ((Control) control).setTooltip(new Tooltip(tooltipText));
        }
        grid.add(label, 0, rowIndex);
        grid.add(control, 1, rowIndex);
        GridPane.setHgrow(control, javafx.scene.layout.Priority.ALWAYS);
    }

    private void saveDataAndProcess() {
        errorMessageLabel.setText("");
        boolean dataIsValid = true;

        userData.setRedMeatFrequency(redMeatFrequencyComboBox.getValue());
        userData.setPoultryFrequency(poultryFrequencyComboBox.getValue());
        userData.setFishSeafoodFrequency(fishSeafoodFrequencyComboBox.getValue());
        userData.setRiceType(riceTypeComboBox.getValue());
        userData.setDietaryPattern(dietaryPatternComboBox.getValue());
        userData.setLocalFoodSourcingFrequency(localFoodSourcingComboBox.getValue());

        try {
            userData.setDailyRiceServings(parseDoubleField(dailyRiceServingsField, "Daily Rice Servings", true));
            userData.setWeeklyFoodWasteKg(parseDoubleField(weeklyFoodWasteField, "Weekly Food Waste", true));
        } catch (IllegalArgumentException e) {
            appendErrorMessage(e.getMessage());
            dataIsValid = false;
        }

        if (dataIsValid) {
            System.out.println("Diet Page: Data saved to UserData model.");
            controller.processDietData(); // Inform controller
            // TODO: Navigate to the next page (e.g., Transport) via the controller
        }
    }

    private double parseDoubleField(TextField field, String fieldName, boolean isRequired) throws IllegalArgumentException {
        String text = field.getText();
        if (text == null || text.trim().isEmpty()) {
            if (isRequired) {
                throw new IllegalArgumentException(fieldName + " cannot be empty.");
            }
            return 0.0;
        }
        try {
            double value = Double.parseDouble(text.trim());
            if (value < 0) {
                throw new IllegalArgumentException(fieldName + " must be a non-negative value.");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format for " + fieldName + ". Please enter a valid number.");
        }
    }

    private void appendErrorMessage(String message) {
        if (errorMessageLabel.getText().isEmpty()) {
            errorMessageLabel.setText(message);
        } else {
            errorMessageLabel.setText(errorMessageLabel.getText() + "\n" + message);
        }
    }
}
