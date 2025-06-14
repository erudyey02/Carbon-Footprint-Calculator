// File: org/letrancpe/carboncalculator/view/TransportPage.java
package org.letrancpe.carboncalculator.view;

import org.letrancpe.carboncalculator.controller.MainController;
import org.letrancpe.carboncalculator.model.UserData;
import org.letrancpe.carboncalculator.model.dto.FlightEntry;
import org.letrancpe.carboncalculator.model.dto.TransportEntry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.ArrayList;

/**
 * TransportPage (View component)
 * Collects user data related to transportation habits.
 * UPDATED: Reorganized into a TabPane for better clarity and grouping of sections.
 */
public class TransportPage {

    private final MainController controller;
    private final UserData userData;

    // Fields for Regular Transport Tab
    private TableView<TransportEntry> regularTransportTable;
    private ComboBox<String> modeComboBox;
    private TextField distanceField;
    private ComboBox<String> frequencyComboBoxRegular;
    private TextField fuelEfficiencyField;
    private ComboBox<String> fuelTypeComboBox;
    private Spinner<Integer> passengersSpinner;

    // Fields for Flights Tab
    private TableView<FlightEntry> flightsTable;
    private ComboBox<String> flightHaulComboBox;
    private ComboBox<String> flightClassComboBox;
    private TextField numFlightsField;
    private TextField avgFlightDistanceField;

    // Fields for Annual Car Travel Tab
    private TextField annualCarKmField;
    private ComboBox<String> annualCarFuelTypeComboBox;
    private TextField annualCarEfficiencyField;

    private Label errorMessageLabel;

    private final ObservableList<TransportEntry> regularTransportData = FXCollections.observableArrayList();
    private final ObservableList<FlightEntry> flightData = FXCollections.observableArrayList();


    public TransportPage(MainController controller, UserData userData) {
        this.controller = controller;
        this.userData = userData;
        if (userData.getRegularTransportEntries() != null) {
            regularTransportData.addAll(userData.getRegularTransportEntries());
        }
        if (userData.getFlightEntries() != null) {
            flightData.addAll(userData.getFlightEntries());
        }
    }

    public Node getView() {
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_LEFT);
        mainLayout.setStyle("-fx-background-color: #F1F8E9; -fx-border-color: #C8E6C9; -fx-border-width: 1; -fx-border-radius: 5;");

        Label title = new Label("Transportation Habits");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        // --- TabPane Implementation ---
        TabPane tabPane = new TabPane();

        Tab regularTransportTab = new Tab("Regular Trips", createRegularTransportSection());
        Tab annualCarTravelTab = new Tab("Other Car Travel", createAnnualCarTravelSection());
        Tab flightsTab = new Tab("Air Travel", createFlightsSection());

        // Prevent tabs from being closed
        regularTransportTab.setClosable(false);
        annualCarTravelTab.setClosable(false);
        flightsTab.setClosable(false);

        tabPane.getTabs().addAll(regularTransportTab, annualCarTravelTab, flightsTab);


        // --- Error Message and Save Button ---
        errorMessageLabel = new Label();
        errorMessageLabel.setStyle("-fx-text-fill: red;");

        Button saveButton = new Button("Save & Next (Waste)");
        saveButton.setStyle("-fx-background-color: #66BB6A; -fx-text-fill: white; -fx-padding: 8 15; -fx-background-radius: 5;");
        saveButton.setOnAction(_ -> saveDataAndProcess());

        VBox buttonAndErrorBox = new VBox(10, errorMessageLabel, saveButton);
        buttonAndErrorBox.setAlignment(Pos.CENTER_RIGHT); // Align button to the right
        buttonAndErrorBox.setPadding(new Insets(20, 0, 0, 0)); // Add padding on top

        mainLayout.getChildren().addAll(title, tabPane, buttonAndErrorBox);

        // No need to wrap the whole page in a ScrollPane if tabs manage content height
        return mainLayout;
    }

    @SuppressWarnings("unchecked")
    private Node createRegularTransportSection() {
        VBox section = new VBox(15);
        section.setPadding(new Insets(15));

        regularTransportTable = new TableView<>(regularTransportData);
        TableColumn<TransportEntry, String> modeCol = new TableColumn<>("Mode");
        modeCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMode()));
        TableColumn<TransportEntry, Number> distCol = new TableColumn<>("Distance (km)");
        distCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getDistanceKm()));
        TableColumn<TransportEntry, String> freqCol = new TableColumn<>("Frequency");
        freqCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFrequency()));
        regularTransportTable.getColumns().addAll(modeCol, distCol, freqCol);
        regularTransportTable.setPlaceholder(new Label("No regular transport modes added yet."));
        regularTransportTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);


        GridPane inputsGrid = createStyledGridPane();
        modeComboBox = new ComboBox<>();
        modeComboBox.getItems().addAll("Car", "Motorcycle", "Tricycle", "Jeepney", "Bus", "Train/MRT/LRT", "Bicycle", "Walking");
        addLabeledControl(inputsGrid, "Mode:", modeComboBox, 0, "Select transport mode.");

        distanceField = new TextField();
        distanceField.setPromptText("e.g., 10");
        addLabeledControl(inputsGrid, "Distance (km):", distanceField, 1, "Enter distance for this mode (e.g., per trip, per day, per week).");

        frequencyComboBoxRegular = new ComboBox<>();
        frequencyComboBoxRegular.getItems().addAll("Per Day", "Per Week", "Per Month", "Per Year (Total)");
        frequencyComboBoxRegular.setPromptText("Select frequency");
        addLabeledControl(inputsGrid, "Frequency:", frequencyComboBoxRegular, 2, "How often is this distance traveled?");

        Label fuelTypeLabel = new Label("Fuel Type (Car/Moto):");
        fuelTypeComboBox = new ComboBox<>();
        fuelTypeComboBox.getItems().addAll("Gasoline", "Diesel");
        HBox fuelTypeBox = new HBox(5, fuelTypeLabel, fuelTypeComboBox);

        Label fuelEfficiencyLabel = new Label("Fuel Efficiency (km/L):");
        fuelEfficiencyField = new TextField(); fuelEfficiencyField.setPromptText("e.g., 12");
        HBox fuelEfficiencyBox = new HBox(5, fuelEfficiencyLabel, fuelEfficiencyField);

        Label passengersLabel = new Label("Avg. Passengers (Car):");
        passengersSpinner = new Spinner<>(1, 10, 1);
        HBox passengersBox = new HBox(5, passengersLabel, passengersSpinner);

        VBox conditionalInputs = new VBox(5, fuelTypeBox, fuelEfficiencyBox, passengersBox);
        conditionalInputs.setVisible(false);
        inputsGrid.add(conditionalInputs, 1, 3);

        modeComboBox.valueProperty().addListener((_, _, newVal) -> {
            boolean isPrivateVehicle = "Car".equalsIgnoreCase(newVal) || "Motorcycle".equalsIgnoreCase(newVal);
            conditionalInputs.setVisible(isPrivateVehicle);
            passengersBox.setVisible("Car".equalsIgnoreCase(newVal));
        });

        Button addRegularTransportButton = new Button("Add Regular Transport");
        addRegularTransportButton.setOnAction(_ -> addRegularTransportEntry());

        Button removeRegularTransportButton = new Button("Remove Selected");
        removeRegularTransportButton.setOnAction(_ -> {
            TransportEntry selected = regularTransportTable.getSelectionModel().getSelectedItem();
            if (selected != null) regularTransportData.remove(selected);
        });
        HBox addRemoveBox = new HBox(10, addRegularTransportButton, removeRegularTransportButton);
        addRemoveBox.setAlignment(Pos.CENTER_RIGHT);

        VBox formContainer = new VBox(15, inputsGrid, addRemoveBox);

        section.getChildren().addAll(regularTransportTable, formContainer);
        return section;
    }

    private Node createAnnualCarTravelSection() {
        VBox section = new VBox(15);
        section.setPadding(new Insets(15));

        GridPane grid = createStyledGridPane();

        annualCarKmField = new TextField();
        annualCarKmField.setPromptText("e.g., 1000");
        addLabeledControl(grid, "Total Annual Km:", annualCarKmField, 0, "Total kilometers driven by car for non-commute travel per year.");

        annualCarFuelTypeComboBox = new ComboBox<>();
        annualCarFuelTypeComboBox.getItems().addAll("Gasoline", "Diesel", "Not Applicable");
        addLabeledControl(grid, "Fuel Type:", annualCarFuelTypeComboBox, 1, "Fuel type for non-commute car travel.");

        annualCarEfficiencyField = new TextField();
        annualCarEfficiencyField.setPromptText("e.g., 10 km/L");
        addLabeledControl(grid, "Avg. Fuel Efficiency (km/L):", annualCarEfficiencyField, 2, "Average fuel efficiency for these trips.");

        section.getChildren().add(grid);
        return section;
    }

    @SuppressWarnings("unchecked")
    private Node createFlightsSection() {
        VBox section = new VBox(15);
        section.setPadding(new Insets(15));

        flightsTable = new TableView<>(flightData);
        TableColumn<FlightEntry, String> haulCol = new TableColumn<>("Haul Type");
        haulCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getHaulType()));
        TableColumn<FlightEntry, String> classCol = new TableColumn<>("Class");
        classCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCabinClass()));
        TableColumn<FlightEntry, Number> numCol = new TableColumn<>("# One-Way Flights");
        numCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getTotalOneWayFlightsPerYear()));
        TableColumn<FlightEntry, Number> distColFlight = new TableColumn<>("Avg. Dist. (km)");
        distColFlight.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getAverageDistancePerFlightKm()));
        flightsTable.getColumns().addAll(haulCol, classCol, numCol, distColFlight);
        flightsTable.setPlaceholder(new Label("No flights added yet."));
        flightsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        GridPane inputsGrid = createStyledGridPane();
        flightHaulComboBox = new ComboBox<>();
        flightHaulComboBox.getItems().addAll("Short (<1500km)", "Medium (1500-4000km)", "Long (>4000km)");
        addLabeledControl(inputsGrid, "Haul Type:", flightHaulComboBox, 0, "Select flight haul type.");

        flightClassComboBox = new ComboBox<>();
        flightClassComboBox.getItems().addAll("Economy", "Business");
        addLabeledControl(inputsGrid, "Cabin Class:", flightClassComboBox, 1, "Select cabin class.");

        numFlightsField = new TextField();
        numFlightsField.setPromptText("e.g., 2");
        addLabeledControl(inputsGrid, "# One-Way Flights/Year:", numFlightsField, 2, "Total number of one-way flights of this type per year.");

        avgFlightDistanceField = new TextField();
        avgFlightDistanceField.setPromptText("e.g., 800 for Short Haul");
        addLabeledControl(inputsGrid, "Avg. One-Way Distance (km):", avgFlightDistanceField, 3, "Average one-way distance for this type of flight.");

        Button addFlightButton = new Button("Add Flight Entry");
        addFlightButton.setOnAction(_ -> addFlightEntry());

        Button removeFlightButton = new Button("Remove Selected Flight");
        removeFlightButton.setOnAction(_ -> {
            FlightEntry selected = flightsTable.getSelectionModel().getSelectedItem();
            if (selected != null) flightData.remove(selected);
        });
        HBox flightButtonsBox = new HBox(10, addFlightButton, removeFlightButton);
        flightButtonsBox.setAlignment(Pos.CENTER_RIGHT);

        VBox formContainer = new VBox(15, inputsGrid, flightButtonsBox);

        section.getChildren().addAll(flightsTable, formContainer);
        return section;
    }

    private void addRegularTransportEntry() {
        errorMessageLabel.setText("");
        try {
            String mode = modeComboBox.getValue();
            String frequency = frequencyComboBoxRegular.getValue();
            double distance = parseDoubleField(distanceField, "Distance (km)", true);

            if (mode == null || mode.isEmpty()) {
                throw new IllegalArgumentException("Please select a transport mode.");
            }
            if (frequency == null || frequency.isEmpty()) {
                throw new IllegalArgumentException("Please select a frequency for regular transport.");
            }

            TransportEntry newEntry = new TransportEntry(mode, distance, frequency);

            if ("Car".equalsIgnoreCase(mode) || "Motorcycle".equalsIgnoreCase(mode)) {
                String fuelType = fuelTypeComboBox.getValue();
                double fuelEfficiency = parseDoubleField(fuelEfficiencyField, "Fuel Efficiency (km/L)", true);
                if (fuelType == null || fuelType.isEmpty()) {
                    throw new IllegalArgumentException("Fuel type is required for Car/Motorcycle.");
                }
                newEntry.setFuelType(fuelType);
                newEntry.setFuelEfficiencyKmL(fuelEfficiency);
                if ("Car".equalsIgnoreCase(mode)) {
                    newEntry.setPassengers(passengersSpinner.getValue());
                }
            }

            regularTransportData.add(newEntry);
            clearRegularTransportInputs();
            System.out.println("Added regular transport entry: " + mode);

        } catch (IllegalArgumentException e) {
            appendErrorMessage(e.getMessage());
        }
    }

    private void clearRegularTransportInputs() {
        modeComboBox.getSelectionModel().clearSelection();
        distanceField.clear();
        frequencyComboBoxRegular.getSelectionModel().clearSelection();
        fuelTypeComboBox.getSelectionModel().clearSelection();
        fuelEfficiencyField.clear();
        passengersSpinner.getValueFactory().setValue(1);
    }

    private void addFlightEntry() {
        errorMessageLabel.setText("");
        try {
            String haulType = flightHaulComboBox.getValue();
            String cabinClass = flightClassComboBox.getValue();
            int numFlights = (int) parseDoubleField(numFlightsField, "# One-Way Flights/Year", true);
            double avgDistance = parseDoubleField(avgFlightDistanceField, "Avg. One-Way Distance (km)", true);

            if (haulType == null || haulType.isEmpty()) {
                throw new IllegalArgumentException("Please select a flight haul type.");
            }
            if (cabinClass == null || cabinClass.isEmpty()) {
                throw new IllegalArgumentException("Please select a cabin class.");
            }

            FlightEntry newEntry = new FlightEntry(haulType, cabinClass, numFlights, avgDistance);
            flightData.add(newEntry);
            clearFlightInputs();
            System.out.println("Added flight entry: " + haulType + " " + cabinClass);

        } catch (IllegalArgumentException e) {
            appendErrorMessage(e.getMessage());
        }
    }

    private void clearFlightInputs() {
        flightHaulComboBox.getSelectionModel().clearSelection();
        flightClassComboBox.getSelectionModel().clearSelection();
        numFlightsField.clear();
        avgFlightDistanceField.clear();
    }

    private GridPane createStyledGridPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
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
        userData.setRegularTransportEntries(new ArrayList<>(regularTransportData));
        userData.setFlightEntries(new ArrayList<>(flightData));
        try {
            userData.setAnnualNonCommuteCarKm(parseDoubleField(annualCarKmField, "Annual Car Km (Non-Commute)", false));
            String annualCarFuel = annualCarFuelTypeComboBox.getValue();
            userData.setAnnualNonCommuteCarFuelType(annualCarFuel);
            boolean carKmEntered = userData.getAnnualNonCommuteCarKm() > 0;
            boolean carFuelTypeSelected = annualCarFuel != null && !"Not Applicable".equals(annualCarFuel);
            if (carKmEntered && carFuelTypeSelected) {
                userData.setAnnualNonCommuteCarFuelEfficiencyKmL(parseDoubleField(annualCarEfficiencyField, "Annual Car Efficiency (Non-Commute)", true));
            } else if (carKmEntered && annualCarFuel == null) {
                appendErrorMessage("Fuel type is required for non-commute car travel if distance is greater than 0.");
                dataIsValid = false;
            } else if (annualCarEfficiencyField.getText() != null && !annualCarEfficiencyField.getText().isEmpty()) {
                userData.setAnnualNonCommuteCarFuelEfficiencyKmL(parseDoubleField(annualCarEfficiencyField, "Annual Car Efficiency (Non-Commute)", false));
            } else {
                userData.setAnnualNonCommuteCarFuelEfficiencyKmL(0);
            }
        } catch (IllegalArgumentException e) {
            appendErrorMessage(e.getMessage());
            dataIsValid = false;
        }
        if (dataIsValid) {
            System.out.println("Transport Page: Data saved to UserData model.");
            controller.processTransportData();
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