// File: org/letrancpe/carboncalculator/view/HousingPage.java
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
import javafx.scene.text.FontWeight; // Correctly import FontWeight
import javafx.scene.text.Text;

/**
 * HousingPage (View component)
 * Collects user data related to housing details and energy consumption.
 * This page might be split into separate Housing and Energy pages in future iterations for improved clarity.
 */
public class HousingPage {

    private MainController controller;
    private UserData userData;

    // UI Elements - Housing Section
    private ComboBox<String> dwellingTypeComboBox;
    private TextField floorAreaField;
    private TextField energyEfficiencyField;

    // UI Elements - Energy Consumption Section
    private ComboBox<String> electricityGridRegionComboBox;
    private TextField monthlyElectricityField;
    private ComboBox<String> cookingFuelTypeComboBox;
    private TextField cookingFuelUsageField;
    private Label cookingFuelUsageUnitLabel;
    private CheckBox woodSustainablySourcedCheckBox;
    private HBox woodSustainabilityBox; // Container to show/hide the wood sustainability checkbox.
    private TextField dailyWaterUsageField;

    private Label errorMessageLabel; // For displaying validation or error messages.


    public HousingPage(MainController controller, UserData userData) {
        this.controller = controller;
        this.userData = userData;
    }

    public Node getView() {
        VBox mainLayout = new VBox(20); // Main vertical layout with spacing.
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_LEFT);
        mainLayout.setStyle("-fx-background-color: #F1F8E9; -fx-border-color: #C8E6C9; -fx-border-width: 1; -fx-border-radius: 5;");

        // --- Housing Information Section ---
        Label housingTitle = new Label("Housing Information");
        housingTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Corrected: Use FontWeight.BOLD
        GridPane housingGrid = createStyledGridPane();

        dwellingTypeComboBox = new ComboBox<>();
        dwellingTypeComboBox.getItems().addAll("Apartment / Condominium", "House (any type)", "Traditional / Indigenous House", "Other");
        dwellingTypeComboBox.setPromptText("Select dwelling type");
        addLabeledControl(housingGrid, "Dwelling Type:", dwellingTypeComboBox, 0, "Select the type of primary residence. This information helps in tailoring recommendations.");

        floorAreaField = new TextField();
        floorAreaField.setPromptText("e.g., 75");
        addLabeledControl(housingGrid, "Approx. Floor Area (sq m):", floorAreaField, 1, "Enter the approximate floor area of the residence.");

        energyEfficiencyField = new TextField();
        energyEfficiencyField.setPromptText("e.g., insulated roof, new AC units, BERDE certified");
        addLabeledControl(housingGrid, "Energy Efficiency Features (Optional):", energyEfficiencyField, 2, "Describe any known energy efficiency features or ratings of the home.");
        Text efficiencyNote = new Text("(This information helps tailor recommendations; it is not used for direct CO2e calculation of the building structure in this version.)");
        efficiencyNote.setFont(Font.font("Arial", FontWeight.NORMAL, 10)); // Use FontWeight.NORMAL
        efficiencyNote.setWrappingWidth(300); // Ensure note wraps if long.
        housingGrid.add(efficiencyNote, 1, 3); // Add note below the field.


        // --- Energy Consumption Section ---
        Label energyTitle = new Label("Energy Consumption");
        energyTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Corrected: Use FontWeight.BOLD
        energyTitle.setPadding(new Insets(15, 0, 0, 0)); // Add some top margin before this section.
        GridPane energyGrid = createStyledGridPane();

        electricityGridRegionComboBox = new ComboBox<>();
        electricityGridRegionComboBox.getItems().addAll("Luzon-Visayas", "Mindanao", "National Average/Unknown");
        electricityGridRegionComboBox.setPromptText("Select electricity grid region");
        addLabeledControl(energyGrid, "Electricity Grid Region:", electricityGridRegionComboBox, 0, "Select the grid region for a more accurate electricity emission factor.");

        monthlyElectricityField = new TextField();
        monthlyElectricityField.setPromptText("e.g., 150");
        addLabeledControl(energyGrid, "Avg. Monthly Electricity (kWh):", monthlyElectricityField, 1, "Enter the overall average monthly electricity consumption in kilowatt-hours.");

        cookingFuelTypeComboBox = new ComboBox<>();
        cookingFuelTypeComboBox.getItems().addAll("LPG", "Kerosene", "Charcoal", "Wood", "Electricity", "None");
        cookingFuelTypeComboBox.setPromptText("Select primary cooking fuel");
        addLabeledControl(energyGrid, "Primary Cooking Fuel:", cookingFuelTypeComboBox, 2, "Select the main fuel used for cooking.");

        cookingFuelUsageField = new TextField();
        cookingFuelUsageField.setPromptText("e.g., 10 for LPG (kg)");
        cookingFuelUsageUnitLabel = new Label("Unit"); // This label will be updated by a listener.
        HBox cookingFuelUsageBox = new HBox(5, cookingFuelUsageField, cookingFuelUsageUnitLabel); // Group field and unit label.
        cookingFuelUsageBox.setAlignment(Pos.CENTER_LEFT);
        addLabeledControl(energyGrid, "Monthly Cooking Fuel Usage:", cookingFuelUsageBox, 3, "Enter average monthly usage of the selected cooking fuel.");

        woodSustainablySourcedCheckBox = new CheckBox("Is the wood sustainably sourced?");
        woodSustainabilityBox = new HBox(woodSustainablySourcedCheckBox);
        woodSustainabilityBox.setVisible(false); // Initially hidden, shown only if "Wood" is selected.
        energyGrid.add(woodSustainabilityBox, 1, 4); // Positioned below the cooking fuel usage input.

        // Listener to update cooking fuel unit and visibility of wood sustainability checkbox.
        cookingFuelTypeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            updateCookingFuelUsageUnit(newVal);
            woodSustainabilityBox.setVisible("Wood".equalsIgnoreCase(newVal));
        });
        updateCookingFuelUsageUnit(null); // Set initial state for the unit label and field.

        dailyWaterUsageField = new TextField();
        dailyWaterUsageField.setPromptText("e.g., 500");
        addLabeledControl(energyGrid, "Avg. Daily Household Water (Liters):", dailyWaterUsageField, 5, "Enter the total average daily water consumption for the household in Liters.");


        // --- Error Message and Save Button ---
        errorMessageLabel = new Label();
        errorMessageLabel.setStyle("-fx-text-fill: red;"); // Style for error messages.

        Button saveButton = new Button("Save & Calculate (Partial)"); // Button text can be "Save & Next" if part of a wizard.
        saveButton.setStyle("-fx-background-color: #66BB6A; -fx-text-fill: white; -fx-padding: 8 15; -fx-background-radius: 5;");
        saveButton.setOnAction(e -> saveDataAndProcess());

        VBox buttonAndErrorBox = new VBox(10, errorMessageLabel, saveButton); // Group error label and button.
        buttonAndErrorBox.setAlignment(Pos.CENTER_LEFT);
        buttonAndErrorBox.setPadding(new Insets(10,0,0,0));


        mainLayout.getChildren().addAll(housingTitle, housingGrid, energyTitle, energyGrid, buttonAndErrorBox);

        // Use a ScrollPane to ensure all content is viewable if it exceeds the window height.
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true); // Makes the scroll pane's content width match the scroll pane's width.
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #F1F8E9;"); // Match background.

        return scrollPane;
    }

    private GridPane createStyledGridPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10); // Horizontal gap between columns.
        grid.setVgap(10); // Vertical gap between rows.
        grid.setPadding(new Insets(10,0,10,0)); // Padding around the grid.
        return grid;
    }

    private void addLabeledControl(GridPane grid, String labelText, Node control, int rowIndex, String tooltipText) {
        Label label = new Label(labelText);
        if (control instanceof Control) { // Set tooltip only for Control subclasses.
            ((Control) control).setTooltip(new Tooltip(tooltipText));
        }
        grid.add(label, 0, rowIndex);
        grid.add(control, 1, rowIndex);
        GridPane.setHgrow(control, javafx.scene.layout.Priority.ALWAYS); // Allow control to grow horizontally.
    }

    private void updateCookingFuelUsageUnit(String fuelType) {
        if (fuelType == null) {
            cookingFuelUsageUnitLabel.setText("(Select fuel type)");
            cookingFuelUsageField.setDisable(true);
            cookingFuelUsageField.clear();
            return;
        }
        cookingFuelUsageField.setDisable(false);
        switch (fuelType) {
            case "LPG":
            case "Charcoal":
            case "Wood":
                cookingFuelUsageUnitLabel.setText("kg/month");
                break;
            case "Kerosene":
                cookingFuelUsageUnitLabel.setText("Liters/month");
                break;
            case "Electricity": // Electricity for cooking is part of main electricity usage.
            case "None":
                cookingFuelUsageUnitLabel.setText("(Covered by main electricity)");
                cookingFuelUsageField.setDisable(true);
                cookingFuelUsageField.clear();
                break;
            default:
                cookingFuelUsageUnitLabel.setText("Unit"); // Default if type is unrecognized.
                break;
        }
    }

    private void saveDataAndProcess() {
        errorMessageLabel.setText(""); // Clear previous error messages.
        boolean dataIsValid = true;

        // --- Housing Data Population ---
        userData.setDwellingType(dwellingTypeComboBox.getValue());
        userData.setEnergyEfficiencyRating(energyEfficiencyField.getText()); // Optional field.
        try {
            // Floor area is optional for calculation in this phase, but parse if provided.
            userData.setFloorArea(parseDoubleField(floorAreaField, "Floor Area", false));
        } catch (IllegalArgumentException e) {
            appendErrorMessage(e.getMessage());
            dataIsValid = false;
        }

        // --- Energy Data Population ---
        userData.setElectricityGridRegion(electricityGridRegionComboBox.getValue());
        try {
            userData.setMonthlyElectricityKWh(parseDoubleField(monthlyElectricityField, "Monthly Electricity", true)); // Required field.

            String cookingFuelType = cookingFuelTypeComboBox.getValue();
            userData.setPrimaryCookingFuelType(cookingFuelType);

            if (cookingFuelType != null && !cookingFuelType.equals("Electricity") && !cookingFuelType.equals("None")) {
                userData.setCookingFuelMonthlyUsage(parseDoubleField(cookingFuelUsageField, "Cooking Fuel Usage", true)); // Required if fuel type selected.
                if ("Wood".equals(cookingFuelType)) {
                    userData.setWoodSustainablySourced(woodSustainablySourcedCheckBox.isSelected());
                }
            } else {
                userData.setCookingFuelMonthlyUsage(0); // Reset or set to zero if not applicable.
            }
            userData.setDailyHouseholdWaterUsageLiters(parseDoubleField(dailyWaterUsageField, "Daily Water Usage", true)); // Required field.

        } catch (IllegalArgumentException e) {
            appendErrorMessage(e.getMessage());
            dataIsValid = false;
        }

        if (dataIsValid) {
            System.out.println("Housing & Energy Page: Data saved to UserData model.");
            controller.processHousingData(); // Inform controller (current implementation is a placeholder).
            // TODO: Implement navigation to the next page (e.g., Diet page) via the controller.
            // controller.navigateToDietPage();

            // For testing purposes, calculation can be triggered here:
            // CalculationServiceImpl calcService = new CalculationServiceImpl();
            // CalculatedFootprintData result = calcService.calculateFootprint(userData);
            // System.out.println("Test Calculation Result (kg CO2e): " + result.getTotalAnnualFootprintKgCO2e());
        }
    }

    private double parseDoubleField(TextField field, String fieldName, boolean isRequired) throws IllegalArgumentException {
        String text = field.getText();
        if (text == null || text.trim().isEmpty()) {
            if (isRequired) {
                throw new IllegalArgumentException(fieldName + " cannot be empty.");
            }
            return 0.0; // Return 0.0 for optional fields if empty.
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
