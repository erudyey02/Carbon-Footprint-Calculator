// File: org/letrancpe/carboncalculator/view/GoodsPage.java
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
 * GoodsPage (View component)
 * Collects user data related to goods consumption.
 */
public class GoodsPage {

    private final MainController controller;
    private final UserData userData;

    private TextField clothingSpendingField;
    private TextField electronicsSpendingField;

    private Label errorMessageLabel;

    public GoodsPage(MainController controller, UserData userData) {
        this.controller = controller;
        this.userData = userData;
    }

    public Node getView() {
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_LEFT);
        mainLayout.setStyle("-fx-background-color: #F1F8E9; -fx-border-color: #C8E6C9; -fx-border-width: 1; -fx-border-radius: 5;");

        Label title = new Label("Goods Consumption Habits");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        GridPane goodsGrid = createStyledGridPane();

        // --- Clothing Spending ---
        clothingSpendingField = new TextField();
        clothingSpendingField.setPromptText("e.g., 20000");
        addLabeledControl(goodsGrid, "Annual Clothing Spending (PHP):", clothingSpendingField, 0, "Estimate total annual spending on new clothing and apparel.");

        // --- Electronics Spending ---
        electronicsSpendingField = new TextField();
        electronicsSpendingField.setPromptText("e.g., 30000");
        addLabeledControl(goodsGrid, "Annual Electronics Spending (PHP):", electronicsSpendingField, 1, "Estimate total annual spending on new electronics (gadgets, appliances).");


        // --- Error Message and Save Button ---
        errorMessageLabel = new Label();
        errorMessageLabel.setStyle("-fx-text-fill: red;");

        Button saveButton = new Button("Finish & See Results");
        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-background-radius: 5;");
        saveButton.setOnAction(_ -> saveDataAndProcess());

        VBox buttonAndErrorBox = new VBox(10, errorMessageLabel, saveButton);
        buttonAndErrorBox.setAlignment(Pos.CENTER_LEFT);
        buttonAndErrorBox.setPadding(new Insets(10,0,0,0));

        mainLayout.getChildren().addAll(title, goodsGrid, buttonAndErrorBox);

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
            userData.setAnnualClothingSpendingPHP(parseDoubleField(clothingSpendingField, "Clothing Spending", false));
            userData.setAnnualElectronicsSpendingPHP(parseDoubleField(electronicsSpendingField, "Electronics Spending", false));
        } catch (IllegalArgumentException e) {
            appendErrorMessage(e.getMessage());
            dataIsValid = false;
        }

        if (dataIsValid) {
            System.out.println("Goods Page: Data saved to UserData model.");
            controller.processGoodsDataAndCalculate(); // Changed to trigger final calculation and navigation
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
