// File: org/letrancpe/carboncalculator/view/ResultsPage.java
package org.letrancpe.carboncalculator.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.letrancpe.carboncalculator.controller.MainController;
import org.letrancpe.carboncalculator.model.dto.CalculatedFootprintData;
import org.letrancpe.carboncalculator.model.dto.Recommendation;

import java.util.List;
import java.util.Map;

/**
 * ResultsPage (View component)
 * Displays the final calculated carbon footprint, a category breakdown chart,
 * and personalized recommendations.
 */
public class ResultsPage {

    private final MainController controller;
    private VBox mainLayout;
    private Label totalFootprintLabel;
    private BarChart<String, Number> footprintChart;
    private ListView<Recommendation> recommendationsListView;

    public ResultsPage(MainController controller) {
        this.controller = controller;
        createView();
    }

    private void createView() {
        mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setStyle("-fx-background-color: #F1F8E9; -fx-border-color: #C8E6C9; -fx-border-width: 1; -fx-border-radius: 5;");

        Label title = new Label("Your Carbon Footprint Results");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        totalFootprintLabel = new Label("Total Annual Footprint: Calculating...");
        totalFootprintLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        totalFootprintLabel.setStyle("-fx-text-fill: #1B5E20;");

        // --- Chart Section ---
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Category");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Footprint (kg CO2e)");
        footprintChart = new BarChart<>(xAxis, yAxis);
        footprintChart.setTitle("Footprint Breakdown by Category");
        footprintChart.setLegendVisible(false);

        // --- Recommendations Section ---
        Label recommendationsTitle = new Label("Personalized Recommendations");
        recommendationsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        recommendationsTitle.setPadding(new Insets(10, 0, 5, 0));

        recommendationsListView = new ListView<>();
        recommendationsListView.setPrefHeight(250); // Set a preferred height for the list view
        recommendationsListView.setPlaceholder(new Label("No recommendations generated."));

        mainLayout.getChildren().addAll(title, totalFootprintLabel, footprintChart, recommendationsTitle, recommendationsListView);
    }

    public Node getView() {
        // Return a ScrollPane in case content overflows on smaller screens
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #F1F8E9;");
        return scrollPane;
    }

    /**
     * Updates the UI elements on this page with the calculated data.
     * @param footprintData The calculated footprint data.
     * @param recommendations A list of personalized recommendations.
     */
    public void updateResults(CalculatedFootprintData footprintData, List<Recommendation> recommendations) {
        // Update Total Footprint Label
        totalFootprintLabel.setText(String.format("Total Annual Footprint: %.2f kg CO2e", footprintData.getTotalAnnualFootprintKgCO2e()));

        // Update Bar Chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Footprint");

        Map<String, Double> breakdown = footprintData.getFootprintByCategory();
        for (Map.Entry<String, Double> entry : breakdown.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        footprintChart.getData().clear();
        footprintChart.getData().add(series);

        // Update Recommendations ListView
        ObservableList<Recommendation> recommendationItems = FXCollections.observableArrayList(recommendations);
        recommendationsListView.setItems(recommendationItems);
    }
}
