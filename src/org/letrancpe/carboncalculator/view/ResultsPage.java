// File: org/letrancpe/carboncalculator/view/ResultsPage.java
package org.letrancpe.carboncalculator.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.letrancpe.carboncalculator.controller.MainController;
import org.letrancpe.carboncalculator.model.AppConstants;
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
    private Label impactVisualizationLabel;
    private Text environmentalImpactText;
    private BarChart<String, Number> footprintChart;
    private ListView<Recommendation> recommendationsListView;

    public ResultsPage(MainController controller) {
        this.controller = controller;
        createView();
    }

    private void createView() {
        mainLayout = new VBox(25); // Increased spacing between sections
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setStyle("-fx-background-color: #F1F8E9; -fx-border-color: #C8E6C9; -fx-border-width: 1; -fx-border-radius: 5;");

        Label pageTitle = new Label("Your Carbon Footprint Results");
        pageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        pageTitle.setPadding(new Insets(0, 0, 15, 0));

        // --- 1. Summary Section ---
        VBox summarySection = new VBox(10);
        summarySection.setAlignment(Pos.TOP_CENTER);
        summarySection.setStyle("-fx-background-color: #E8F5E9; -fx-border-color: #C8E6C9; -fx-border-width: 1; -fx-border-radius: 5; -fx-padding: 15;");

        Label summaryTitle = new Label("Summary");
        summaryTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        summaryTitle.setStyle("-fx-text-fill: #1B5E20;");

        totalFootprintLabel = new Label("Total Annual Footprint: Calculating...");
        totalFootprintLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        totalFootprintLabel.setStyle("-fx-text-fill: #1B5E20;");

        impactVisualizationLabel = new Label("Equivalent to planting X trees annually.");
        impactVisualizationLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 16));
        impactVisualizationLabel.setStyle("-fx-text-fill: #33691E;");

        environmentalImpactText = new Text(
                "Every kilogram of CO2e contributes to climate change, leading to rising sea levels, extreme weather events, and harm to ecosystems. Your choices matter!"
        );
        environmentalImpactText.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        environmentalImpactText.setWrappingWidth(600);
        environmentalImpactText.setTextAlignment(TextAlignment.CENTER);
        environmentalImpactText.setStyle("-fx-fill: #555555;");

        summarySection.getChildren().addAll(summaryTitle, totalFootprintLabel, impactVisualizationLabel, environmentalImpactText);

        // --- 2. Footprint Breakdown Section ---
        VBox breakdownSection = new VBox(10);
        breakdownSection.setAlignment(Pos.TOP_CENTER);
        breakdownSection.setStyle("-fx-background-color: #E8F5E9; -fx-border-color: #C8E6C9; -fx-border-width: 1; -fx-border-radius: 5; -fx-padding: 15;");

        Label breakdownTitle = new Label("Footprint Breakdown by Category");
        breakdownTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        breakdownTitle.setStyle("-fx-text-fill: #1B5E20;");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Category");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Footprint (kg CO2e)");
        footprintChart = new BarChart<>(xAxis, yAxis);
        footprintChart.setTitle(""); // Title moved to section label
        footprintChart.setLegendVisible(false);
        footprintChart.setPrefHeight(350); // Adjusted height
        footprintChart.setCategoryGap(20);
        footprintChart.setBarGap(5);
        footprintChart.setAnimated(false); // Disable animation for easier label placement


        breakdownSection.getChildren().addAll(breakdownTitle, footprintChart);

        // --- 3. Recommendations Section ---
        // MODIFICATION START
        VBox recommendationsSection = new VBox(15); // Increased spacing from 10 to 15
        // MODIFICATION END
        recommendationsSection.setAlignment(Pos.TOP_LEFT);
        recommendationsSection.setStyle("-fx-background-color: #E8F5E9; -fx-border-color: #C8E6C9; -fx-border-width: 1; -fx-border-radius: 5; -fx-padding: 15;");

        Label recommendationsTitle = new Label("Personalized Recommendations");
        recommendationsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        recommendationsTitle.setStyle("-fx-text-fill: #1B5E20;");

        recommendationsListView = new ListView<>();
        recommendationsListView.setPrefHeight(250);
        recommendationsListView.setPlaceholder(new Label("No recommendations generated."));
        recommendationsListView.setStyle("-fx-control-inner-background: #E0E0E0; -fx-background-color: #E0E0E0;");

        recommendationsSection.getChildren().addAll(recommendationsTitle, recommendationsListView);

        mainLayout.getChildren().addAll(pageTitle, summarySection, breakdownSection, recommendationsSection);
    }

    public Node getView() {
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
        double totalFootprint = footprintData.getTotalAnnualFootprintKgCO2e();

        totalFootprintLabel.setText(String.format("Total Annual Footprint: %.2f kg CO2e", totalFootprint));

        if (AppConstants.EF_TREES_CO2_ABSORPTION_KG_PER_YEAR > 0) {
            long treesToOffset = Math.round(totalFootprint / AppConstants.EF_TREES_CO2_ABSORPTION_KG_PER_YEAR);
            impactVisualizationLabel.setText(String.format("This footprint is equivalent to the annual CO2 absorption of approximately %d mature trees.", treesToOffset));
        } else {
            impactVisualizationLabel.setText("Equivalent tree offset calculation not available.");
        }

        // Clear existing chart data and labels
        footprintChart.getData().clear();
        Node chartContent = footprintChart.lookup(".chart-content");
        if (chartContent instanceof VBox) { // Sometimes it's a VBox or Group, depends on FX version
            ((VBox) chartContent).getChildren().removeIf(node -> node instanceof Label && "data-label".equals(node.getId()));
        } else if (chartContent instanceof javafx.scene.Group) {
            ((javafx.scene.Group) chartContent).getChildren().removeIf(node -> node instanceof Label && "data-label".equals(node.getId()));
        }


        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Footprint");

        Map<String, Double> breakdown = footprintData.getFootprintByCategory();
        breakdown.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Sort categories by value (highest first)
                .forEach(entry -> series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue())));

        footprintChart.getData().add(series);

        // Add data labels to bars after the chart has been laid out
        // This needs to be done after the chart has had a chance to render its nodes.
        // Using a Platform.runLater or a listener is often necessary for dynamic updates.
        // For simplicity, we'll try to add them directly here and rely on subsequent layout passes.
        // A better approach for highly dynamic charts might involve custom Node classes or listeners.
        for (XYChart.Data<String, Number> data : series.getData()) {
            data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                if (newNode != null) {
                    addCustomDataLabel(data, newNode);
                }
            });
        }


        ObservableList<Recommendation> recommendationItems = FXCollections.observableArrayList(recommendations);
        recommendationsListView.setItems(recommendationItems);
    }

    private void addCustomDataLabel(XYChart.Data<String, Number> data, Node node) {
        // Find the bar node and add a label on top of it
        // This is a common pattern for adding labels to JavaFX chart bars.
        final Label label = new Label(String.format("%.2f", data.getYValue().doubleValue()));
        label.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #333333;");
        label.setId("data-label"); // Add an ID to easily remove them later

        // Position the label relative to the bar
        label.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            label.setLayoutX(node.getLayoutX() + (node.getBoundsInLocal().getWidth() / 2) - (newBounds.getWidth() / 2));
            label.setLayoutY(node.getLayoutY() - newBounds.getHeight() - 5); // 5 pixels above the bar
        });

        // Add the label to the chart's content pane (usually a Group or VBox)
        // This is a common pattern for adding elements dynamically to charts.
        // The parent of the node might be the Group holding all bars, or similar.
        Node chartContent = footprintChart.lookup(".chart-content");
        if (chartContent != null) {
            if (chartContent instanceof VBox) {
                ((VBox) chartContent).getChildren().add(label);
            } else if (chartContent instanceof javafx.scene.Group) {
                ((javafx.scene.Group) chartContent).getChildren().add(label);
            }
        }
    }
}