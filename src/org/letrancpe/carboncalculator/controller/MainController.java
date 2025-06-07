// File: org/letrancpe/carboncalculator/controller/MainController.java
package org.letrancpe.carboncalculator.controller;

import org.letrancpe.carboncalculator.model.UserData;
import org.letrancpe.carboncalculator.service.CalculationService;
import org.letrancpe.carboncalculator.service.ReductionPlannerService;
import org.letrancpe.carboncalculator.service.impl.CalculationServiceImpl;
import org.letrancpe.carboncalculator.service.impl.ReductionPlannerServiceImpl;
import org.letrancpe.carboncalculator.view.*; // Import all views

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * MainController directs the overall application flow,
 * handles navigation between different views (pages), and mediates interaction
 * between the model and the views. It is a central component of the MVC architecture.
 */
public class MainController {

    private final Stage primaryStage;
    private final BorderPane rootLayout;
    private final StackPane pageContainer;

    private UserData userData;
    private CalculationService calculationService;
    private ReductionPlannerService reductionPlannerService;

    private Node introPageNode;
    private Node housingPageNode;
    private Node dietPageNode;
    private Node transportPageNode;
    private Node wastePageNode; // Added WastePage node
    private Node helpPageNode;
    // TODO: Add nodes for Goods, Results pages.

    public MainController(Stage primaryStage, BorderPane rootLayout) {
        this.primaryStage = primaryStage;
        this.rootLayout = rootLayout;
        this.pageContainer = new StackPane();
        this.pageContainer.setStyle("-fx-padding: 10px;");

        initializeModelsAndServices();
        initializeViews();
    }

    private void initializeModelsAndServices() {
        this.userData = new UserData();
        this.calculationService = new CalculationServiceImpl();
        this.reductionPlannerService = new ReductionPlannerServiceImpl();
    }

    private void initializeViews() {
        this.introPageNode = new IntroPage(this).getView();
        this.housingPageNode = new HousingPage(this, userData).getView();
        this.dietPageNode = new DietPage(this, userData).getView();
        this.transportPageNode = new TransportPage(this, userData).getView();
        this.wastePageNode = new WastePage(this, userData).getView(); // Initialize WastePage
        this.helpPageNode = new HelpPage(this).getView();
    }

    public void initLayout() {
        HBox navigationBar = new HBox(10);
        navigationBar.setStyle("-fx-background-color: #A5D6A7; -fx-padding: 10px; -fx-alignment: CENTER_LEFT;");

        Label titleLabel = new Label("Carbon Calculator PH");
        titleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        titleLabel.setTextFill(Color.DARKSLATEGRAY);

        Button introButton = createNavButton("Introduction");
        introButton.setOnAction(_ -> showPage(introPageNode));

        Button housingButton = createNavButton("Housing & Energy");
        housingButton.setOnAction(_ -> showPage(housingPageNode));

        Button dietButton = createNavButton("Diet");
        dietButton.setOnAction(_ -> showPage(dietPageNode));

        Button transportButton = createNavButton("Transport");
        transportButton.setOnAction(_ -> showPage(transportPageNode));

        Button wasteButton = createNavButton("Waste"); // Added Waste button
        wasteButton.setOnAction(_ -> showPage(wastePageNode));

        Button helpButton = createNavButton("Help / Sources");
        helpButton.setOnAction(_ -> showPage(helpPageNode));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        navigationBar.getChildren().addAll(titleLabel, spacer, introButton, housingButton, dietButton, transportButton, wasteButton, helpButton); // Added wasteButton
        rootLayout.setTop(navigationBar);
        rootLayout.setCenter(pageContainer);
        showPage(introPageNode);
    }

    private Button createNavButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        button.setOnMouseEntered(_ -> button.setStyle("-fx-background-color: #388E3C; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;"));
        button.setOnMouseExited(_ -> button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;"));
        return button;
    }

    public void showPage(Node pageNode) {
        pageContainer.getChildren().clear();
        if (pageNode != null) {
            pageContainer.getChildren().add(pageNode);
        } else {
            Label errorLabel = new Label("Error: Page not found or not yet implemented.");
            pageContainer.getChildren().add(errorLabel);
        }
    }

    public void processHousingData() { showPage(dietPageNode); }
    public void processDietData() { showPage(transportPageNode); }
    public void processTransportData() { showPage(wastePageNode); }
    public void processWasteData() {
        System.out.println("Processing waste data (placeholder)...");
        // TODO: Navigate to the next page (e.g., Goods Consumption)
        // showPage(goodsPageNode);
    }

    public UserData getUserData() {
        return userData;
    }
}
