// File: org/letrancpe/carboncalculator/controller/MainController.java
package org.letrancpe.carboncalculator.controller;

import org.letrancpe.carboncalculator.model.UserData;
import org.letrancpe.carboncalculator.model.dto.CalculatedFootprintData;
import org.letrancpe.carboncalculator.model.dto.Recommendation;
import org.letrancpe.carboncalculator.service.CalculationService;
import org.letrancpe.carboncalculator.service.ReductionPlannerService;
import org.letrancpe.carboncalculator.service.impl.CalculationServiceImpl;
import org.letrancpe.carboncalculator.service.impl.ReductionPlannerServiceImpl;
import org.letrancpe.carboncalculator.view.*;

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

import java.util.List;

/**
 * MainController directs the overall application flow.
 */
public class MainController {

    private final Stage primaryStage;
    private final BorderPane rootLayout;
    private final StackPane pageContainer;

    private final UserData userData;
    private final CalculationService calculationService;
    private final ReductionPlannerService reductionPlannerService;

    private Node introPageNode;
    private Node housingPageNode;
    private Node dietPageNode;
    private Node transportPageNode;
    private Node wastePageNode;
    private Node goodsPageNode;
    private ResultsPage resultsPage; // Keep instance to update it
    private Node helpPageNode;

    public MainController(Stage primaryStage, BorderPane rootLayout) {
        this.primaryStage = primaryStage;
        this.rootLayout = rootLayout;
        this.pageContainer = new StackPane();
        this.pageContainer.setStyle("-fx-padding: 10px;");

        this.userData = new UserData();
        this.calculationService = new CalculationServiceImpl();
        this.reductionPlannerService = new ReductionPlannerServiceImpl();

        initializeViews();
    }

    private void initializeViews() {
        this.introPageNode = new IntroPage(this).getView();
        this.housingPageNode = new HousingPage(this, userData).getView();
        this.dietPageNode = new DietPage(this, userData).getView();
        this.transportPageNode = new TransportPage(this, userData).getView();
        this.wastePageNode = new WastePage(this, userData).getView();
        this.goodsPageNode = new GoodsPage(this, userData).getView();
        this.resultsPage = new ResultsPage(this); // ResultsPage is instantiated here
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
        Button wasteButton = createNavButton("Waste");
        wasteButton.setOnAction(_ -> showPage(wastePageNode));
        Button goodsButton = createNavButton("Goods");
        goodsButton.setOnAction(_ -> showPage(goodsPageNode));
        Button helpButton = createNavButton("Help / Sources");
        helpButton.setOnAction(_ -> showPage(helpPageNode));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        navigationBar.getChildren().addAll(titleLabel, spacer, introButton, housingButton, dietButton, transportButton, wasteButton, goodsButton, helpButton);
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

    /**
     *  Public method to allow IntroPage to trigger navigation.
     */
    public void navigateToHousingPage() {
        showPage(housingPageNode);
    }

    public void processHousingData() { showPage(dietPageNode); }
    public void processDietData() { showPage(transportPageNode); }
    public void processTransportData() { showPage(wastePageNode); }
    public void processWasteData() { showPage(goodsPageNode); }

    public void processGoodsDataAndCalculate() {
        System.out.println("Final data saved. Calculating results...");

        CalculatedFootprintData footprintData = calculationService.calculateFootprint(userData);
        List<Recommendation> recommendations = reductionPlannerService.generateRecommendations(userData, footprintData);
        resultsPage.updateResults(footprintData, recommendations);
        showPage(resultsPage.getView());
    }

    public UserData getUserData() {
        return userData;
    }
}