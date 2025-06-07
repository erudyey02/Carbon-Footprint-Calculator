// File: org/letrancpe/carboncalculator/view/WastePage.java
package org.letrancpe.carboncalculator.view;

import org.letrancpe.carboncalculator.controller.MainController;
import org.letrancpe.carboncalculator.model.UserData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * WastePage (View component)
 * Collects user data related to waste management habits.
 */
public class WastePage {

    private final MainController controller;
    private final UserData userData;

    private TextField weeklyNonRecycledWasteField;
    private Slider recyclingRateSlider;
    private Label recyclingRateLabel;

    private Label errorMessageLabel;

    public WastePage(MainController controller, UserData userData) {
        this.controller = controller;
        this.userData = userData;
    }

    public Node getView() {
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_LEFT);
        mainLayout.setStyle("-fx-background-color: #F1F8E9; -fx-border-color: #C8E6C9; -fx-border-width: 1; -fx-border-radius: 5;");

        Label title = new Label("Waste Management Habits");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        GridPane wasteGrid = createStyledGridPane();

        // --- Weekly Non-Recycled Waste Input ---
        weeklyNonRecycledWasteField = new TextField();
        weeklyNonRecycledWasteField.setPromptText("e.g., 2.5");
        addLabeledControl(wasteGrid, "Weekly Non-Recycled Waste (kg):", weeklyNonRecycledWasteField, 0, "Estimate the weight of waste sent to landfill each week (excluding composted food waste).");

        // --- Recycling Rate Input ---
        recyclingRateSlider = new Slider(0, 100, 50); // Min, Max, Initial Value
        recyclingRateSlider.setShowTickLabels(true);
        recyclingRateSlider.setShowTickMarks(true);
        recyclingRateSlider.setMajorTickUnit(25);
        recyclingRateSlider.setBlockIncrement(10);

        recyclingRateLabel = new Label("50%"); // Initial label
        recyclingRateSlider.valueProperty().addListener((_, _, newValue) ->
                recyclingRateLabel.setText(String.format("%.0f%%", newValue.doubleValue()))
        ); // Replaced statement lambda with expression lambda

        HBox sliderBox = new HBox(10, recyclingRateSlider, recyclingRateLabel);
        sliderBox.setAlignment(Pos.CENTER_LEFT);
        addLabeledControl(wasteGrid, "Recycling Rate (%):", sliderBox, 1, "Estimate the percentage of recyclable materials (paper, plastic, glass, metal) that are actually recycled.");

        // --- Error Message and Save Button ---
        errorMessageLabel = new Label();
        errorMessageLabel.setStyle("-fx-text-fill: red;");

        Button saveButton = new Button("Save & Next (Goods)");
        saveButton.setStyle("-fx-background-color: #66BB6A; -fx-text-fill: white; -fx-padding: 8 15; -fx-background-radius: 5;");
        saveButton.setOnAction(_ -> saveDataAndProcess());

        VBox buttonAndErrorBox = new VBox(10, errorMessageLabel, saveButton);
        buttonAndErrorBox.setAlignment(Pos.CENTER_LEFT);
        buttonAndErrorBox.setPadding(new Insets(10,0,0,0));

        mainLayout.getChildren().addAll(title, wasteGrid, buttonAndErrorBox);

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

        try {
            userData.setWeeklyNonRecycledWasteKg(parseDoubleField(weeklyNonRecycledWasteField, "Weekly Non-Recycled Waste", true));
            userData.setRecyclingRatePercentage(recyclingRateSlider.getValue());
        } catch (IllegalArgumentException e) {
            appendErrorMessage(e.getMessage());
            dataIsValid = false;
        }

        if (dataIsValid) {
            System.out.println("Waste Page: Data saved to UserData model.");
            controller.processWasteData();
        }
    }

    private double parseDoubleField(TextField field, String fieldName, boolean isRequired) throws IllegalArgumentException {
        String text = field.getText();
        if (text == null || text.trim().isEmpty()) {
            if (isRequired) throw new IllegalArgumentException(fieldName + " cannot be empty.");
            return 0.0;
        }
        try {
            double value = Double.parseDouble(text.trim());
            if (value < 0) throw new IllegalArgumentException(fieldName + " must be a non-negative value.");
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format for " + fieldName + ".");
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
