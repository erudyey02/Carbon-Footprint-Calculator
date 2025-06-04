// File: org/letrancpe/carboncalculator/controller/MainController.java
package org.letrancpe.carboncalculator.controller;

import org.letrancpe.carboncalculator.model.UserData;
import org.letrancpe.carboncalculator.service.CalculationService;
import org.letrancpe.carboncalculator.service.ReductionPlannerService;
import org.letrancpe.carboncalculator.service.impl.CalculationServiceImpl;
import org.letrancpe.carboncalculator.service.impl.ReductionPlannerServiceImpl;
import org.letrancpe.carboncalculator.view.DietPage; // Added DietPage import
import org.letrancpe.carboncalculator.view.HelpPage;
import org.letrancpe.carboncalculator.view.HousingPage;
import org.letrancpe.carboncalculator.view.IntroPage;


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

    // Model components
    private UserData userData;

    // Service components
    private CalculationService calculationService;
    private ReductionPlannerService reductionPlannerService;

    // View components (instances of page UI nodes)
    private Node introPageNode;
    private Node housingPageNode;
    private Node dietPageNode; // Added DietPage node
    private Node helpPageNode;
    // TODO: Add nodes for other pages (Transport, Waste, Goods, Results) as they are developed.

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
        IntroPage introPage = new IntroPage(this);
        this.introPageNode = introPage.getView();

        HousingPage housingPage = new HousingPage(this, userData);
        this.housingPageNode = housingPage.getView();

        DietPage dietPage = new DietPage(this, userData); // Initialize DietPage
        this.dietPageNode = dietPage.getView();

        HelpPage helpPage = new HelpPage(this);
        this.helpPageNode = helpPage.getView();

        // TODO: Initialize other view pages as they are created.
    }

    /**
     * Configures the initial layout of the application,
     * including the navigation bar and the first page to be displayed.
     */
    public void initLayout() {
        HBox navigationBar = new HBox(10);
        navigationBar.setStyle("-fx-background-color: #A5D6A7; -fx-padding: 10px; -fx-alignment: CENTER_LEFT;");

        Label titleLabel = new Label("Carbon Calculator PH");
        titleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        titleLabel.setTextFill(Color.DARKSLATEGRAY);

        Button introButton = createNavButton("Introduction");
        introButton.setOnAction(e -> showPage(introPageNode));

        Button housingButton = createNavButton("Housing & Energy");
        housingButton.setOnAction(e -> showPage(housingPageNode));

        Button dietButton = createNavButton("Diet"); // Added Diet button
        dietButton.setOnAction(e -> showPage(dietPageNode));

        // TODO: Add navigation buttons for Transport, Waste, Goods, and Results pages.

        Button helpButton = createNavButton("Help / Sources");
        helpButton.setOnAction(e -> showPage(helpPageNode));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        navigationBar.getChildren().addAll(titleLabel, spacer, introButton, housingButton, dietButton, helpButton); // Added dietButton
        rootLayout.setTop(navigationBar);
        rootLayout.setCenter(pageContainer);
        showPage(introPageNode);
    }

    private Button createNavButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #388E3C; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;"));
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

    public void processHousingData() {
        System.out.println("Processing housing data (placeholder)...");
        // Example: After processing housing, navigate to the Diet page
        showPage(dietPageNode);
    }

    public void processDietData() {
        System.out.println("Processing diet data (placeholder)...");
        // Example: After processing diet, navigate to the next logical page (e.g., Transport)
        // showPage(transportPageNode); // Hypothetical next page
    }

    public UserData getUserData() {
        return userData;
    }
}
