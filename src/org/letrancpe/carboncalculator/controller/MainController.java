// File: org/letrancpe/carboncalculator/controller/MainController.java
package org.letrancpe.carboncalculator.controller;

import org.letrancpe.carboncalculator.model.UserData;
import org.letrancpe.carboncalculator.service.CalculationService;
import org.letrancpe.carboncalculator.service.ReductionPlannerService;
import org.letrancpe.carboncalculator.service.impl.CalculationServiceImpl;
import org.letrancpe.carboncalculator.service.impl.ReductionPlannerServiceImpl;
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
import javafx.stage.Stage;

/**
 * MainController directs the overall application flow,
 * handles navigation between different views (pages), and mediates interaction
 * between the model and the views. It is a central component of the MVC architecture.
 */
public class MainController {

    private final Stage primaryStage;
    private final BorderPane rootLayout; // Main layout of the application window.
    private final StackPane pageContainer; // Container for switching pages.

    // Model components
    private UserData userData;
    // AppConstants are primarily static, instantiation is generally not needed.

    // Service components
    private CalculationService calculationService;
    private ReductionPlannerService reductionPlannerService;

    // View components (instances of page UI nodes)
    private Node introPageNode;
    private Node housingPageNode;
    private Node helpPageNode;
    // TODO: Add nodes for other pages (e.g., DietPage, TransportPage) as they are developed.

    public MainController(Stage primaryStage, BorderPane rootLayout) {
        this.primaryStage = primaryStage;
        this.rootLayout = rootLayout;
        this.pageContainer = new StackPane(); // Pages are stacked; only one is visible at a time.
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
        // Instantiate page views. These methods return the UI Node for each page.
        IntroPage introPage = new IntroPage(this); // Controller passed for action delegation.
        this.introPageNode = introPage.getView();

        HousingPage housingPage = new HousingPage(this, userData); // Controller and relevant model data passed.
        this.housingPageNode = housingPage.getView();

        HelpPage helpPage = new HelpPage(this);
        this.helpPageNode = helpPage.getView();

        // TODO: Initialize other view pages as they are created.
    }

    /**
     * Configures the initial layout of the application,
     * including the navigation bar and the first page to be displayed.
     */
    public void initLayout() {
        HBox navigationBar = new HBox(10); // Spacing between navigation elements.
        navigationBar.setStyle("-fx-background-color: #A5D6A7; -fx-padding: 10px; -fx-alignment: CENTER_LEFT;"); // Style for the navigation bar.

        Label titleLabel = new Label("Carbon Calculator PH");
        titleLabel.setFont(Font.font("Arial", 20));
        titleLabel.setTextFill(Color.DARKSLATEGRAY);

        Button introButton = createNavButton("Introduction");
        introButton.setOnAction(e -> showPage(introPageNode));

        Button housingButton = createNavButton("Housing & Energy"); // Currently combined; can be split later.
        housingButton.setOnAction(e -> showPage(housingPageNode));

        // TODO: Add navigation buttons for Diet, Transport, Waste, Goods, and Results pages.

        Button helpButton = createNavButton("Help / Sources");
        helpButton.setOnAction(e -> showPage(helpPageNode));

        Region spacer = new Region(); // Used to push elements (e.g., buttons) to one side.
        HBox.setHgrow(spacer, Priority.ALWAYS);

        navigationBar.getChildren().addAll(titleLabel, spacer, introButton, housingButton, helpButton);
        rootLayout.setTop(navigationBar);
        rootLayout.setCenter(pageContainer);
        showPage(introPageNode); // Display the initial page.
    }

    private Button createNavButton(String text) {
        Button button = new Button(text);
        // Basic styling for navigation buttons.
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #388E3C; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;"));
        return button;
    }

    /**
     * Switches the currently displayed page in the pageContainer.
     * @param pageNode The UI Node of the page to display.
     */
    public void showPage(Node pageNode) {
        pageContainer.getChildren().clear(); // Remove the current page.
        if (pageNode != null) {
            pageContainer.getChildren().add(pageNode); // Add the new page.
        } else {
            // Display an error message if the page node is null.
            Label errorLabel = new Label("Error: Page not found or not yet implemented.");
            pageContainer.getChildren().add(errorLabel);
        }
    }

    /**
     * Processes data related to housing.
     * This method is typically called from a view (e.g., HousingPage) after data input.
     */
    public void processHousingData() {
        // Placeholder for processing logic.
        System.out.println("Processing housing data (placeholder)...");
        System.out.println("Dwelling Type from UserData: " + userData.getDwellingType());
        // Further actions, like navigation or service calls, would occur here.
        // Example: showPage(nextPageNode);
    }

    /**
     * Provides access to the UserData object.
     * @return The UserData instance.
     */
    public UserData getUserData() {
        return userData;
    }

    // TODO: Implement methods for navigating to other specific pages as they are developed.
    // Example: public void navigateToDietPage() { showPage(dietPageNode); }
}
