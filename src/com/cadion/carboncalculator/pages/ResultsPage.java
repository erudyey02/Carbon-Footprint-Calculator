package com.cadion.carboncalculator.pages;

import com.cadion.carboncalculator.AppConstants;
import com.cadion.carboncalculator.UserData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ResultsPage {
    private UserData userData;
    private Label electricityResultLabel, transportResultLabel, wasteResultLabel, totalResultLabel, treeEquivalentLabel, errorLabelOnResultsPage;

    public ResultsPage(UserData userData) {
        this.userData = userData;
    }

    public Node createPage() {
        VBox page = createStyledPageVBox("Your Estimated Footprint");
        page.setAlignment(Pos.CENTER_LEFT);

        errorLabelOnResultsPage = new Label(); // Dedicated error label for this page
        errorLabelOnResultsPage.setTextFill(Color.web(AppConstants.ERROR_COLOR));
        errorLabelOnResultsPage.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        errorLabelOnResultsPage.setMinHeight(20);


        electricityResultLabel = AppConstants.createStyledResultLabel("Electricity:");
        transportResultLabel = AppConstants.createStyledResultLabel("Transportation:");
        wasteResultLabel = AppConstants.createStyledResultLabel("Waste:");

        totalResultLabel = AppConstants.createStyledResultLabel("Total Annual CO2e:");
        totalResultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        totalResultLabel.setTextFill(Color.web(AppConstants.ACCENT_COLOR));

        treeEquivalentLabel = AppConstants.createStyledResultLabel("This footprint is like...");
        treeEquivalentLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        treeEquivalentLabel.setWrapText(true);
        treeEquivalentLabel.setTextFill(Color.web(AppConstants.TEXT_SECONDARY_COLOR));

        page.getChildren().addAll(errorLabelOnResultsPage, electricityResultLabel, transportResultLabel, wasteResultLabel, AppConstants.createSeparator(), totalResultLabel, AppConstants.createSeparator(), treeEquivalentLabel);
        return page;
    }

    public void calculateAndDisplayResults() {
        if (errorLabelOnResultsPage == null) return;
        errorLabelOnResultsPage.setText(""); // Clear previous errors

        try {
            double actualElectricityKwh = userData.getElectricityKwh();

            if (userData.isUseAlternativeElectricity()) {
                // Very rough estimation: 1 AC unit, 1 Ton ~ 1.5 kWh per hour
                // This calculation should ideally be more robust or configurable.
                actualElectricityKwh = userData.getNumAcUnits() * userData.getHoursAcUsage() * 1.5 * 30; // Monthly kWh from AC
            }

            double annualElectricity = actualElectricityKwh * 12 * AppConstants.ELECTRICITY_FACTOR_KWH;
            double annualTransport = userData.getTransportKm() * 52 * AppConstants.TRANSPORTATION_FACTOR_KM;
            double annualWaste = userData.getWasteKg() * 52 * AppConstants.WASTE_FACTOR_KG;
            double totalFootprint = annualElectricity + annualTransport + annualWaste;

            electricityResultLabel.setText(String.format("Electricity: %.2f kg CO2e/year", annualElectricity));
            transportResultLabel.setText(String.format("Transportation: %.2f kg CO2e/year", annualTransport));
            wasteResultLabel.setText(String.format("Waste: %.2f kg CO2e/year", annualWaste));
            totalResultLabel.setText(String.format("Total Annual CO2e: %.2f kg", totalFootprint));

            if (totalFootprint > 0) {
                double treesNeeded = totalFootprint / AppConstants.TREE_ABSORPTION_KG_CO2_PER_YEAR;
                treeEquivalentLabel.setText(String.format("To offset this, about %.0f mature trees would need to grow for one year.", Math.ceil(treesNeeded)));
            } else {
                treeEquivalentLabel.setText("Your footprint is very low, or no data was entered for calculation!");
            }
        } catch (Exception e) { // Catch any unexpected error during calculation
            errorLabelOnResultsPage.setText("Error calculating results: " + e.getMessage());
            System.err.println("Calculation error: " + e.toString());
        }
    }

    private VBox createStyledPageVBox(String titleText) {
        VBox page = new VBox(10); // Reduced spacing for results page
        page.setAlignment(Pos.TOP_CENTER);
        page.setPadding(new Insets(25, 40, 25, 40));
        page.setStyle("-fx-background-color: " + AppConstants.BG_CONTENT_CARD + "; -fx-background-radius: 10; -fx-border-color: "+AppConstants.BORDER_COLOR_LIGHT+"; -fx-border-radius: 10;");

        Text title = new Text(titleText);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        title.setFill(javafx.scene.paint.Color.web(AppConstants.TEXT_PRIMARY_COLOR));
        page.getChildren().add(title);
        return page;
    }
}