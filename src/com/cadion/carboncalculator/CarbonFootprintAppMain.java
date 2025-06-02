package com.cadion.carboncalculator;

import com.cadion.carboncalculator.pages.*;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class CarbonFootprintAppMain extends Application {

    private final List<PageWrapper> pages = new ArrayList<>();
    private int currentPageIndex = 0;

    private Button nextButton, prevButton;
    private Label globalErrorLabel; // For errors during page transitions or updates

    private ResultsPage resultsPage;

    // Wrapper to hold page node and its update logic
    private static class PageWrapper {
        Node node;
        PageUpdater updater; // Functional interface for updating user data from the page

        PageWrapper(Node node, PageUpdater updater) {
            this.node = node;
            this.updater = updater;
        }
    }
    // Functional interface for pages to implement their data update logic
    @FunctionalInterface
    interface PageUpdater {
        boolean updateUserData(); // Returns true if update/validation is successful
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Carbon Footprint Calculator");
        // Central data model
        UserData userData = new UserData(); // Initialize data model

        BorderPane rootLayout = new BorderPane();
        rootLayout.setStyle("-fx-background-color: " + AppConstants.BG_PRIMARY + ";");

        StackPane pageContainer = new StackPane();
        pageContainer.setPadding(new Insets(20));

        // Initialize pages
        // Page instances
        IntroPage introPage = new IntroPage();
        ElectricityPage electricityPage = new ElectricityPage(userData);
        TransportPage transportPage = new TransportPage(userData);
        WastePage wastePage = new WastePage(userData);
        resultsPage = new ResultsPage(userData);


        // Add pages to the list (with their data update logic)
        pages.add(new PageWrapper(introPage.createPage(), () -> true)); // Intro page has no data to update
        pages.add(new PageWrapper(electricityPage.createPage(), electricityPage::updateUserData));
        pages.add(new PageWrapper(transportPage.createPage(), transportPage::updateUserData));
        pages.add(new PageWrapper(wastePage.createPage(), wastePage::updateUserData));
        pages.add(new PageWrapper(resultsPage.createPage(), () -> true)); // Results page displays data

        for (PageWrapper pw : pages) {
            pageContainer.getChildren().add(pw.node);
        }

        rootLayout.setCenter(pageContainer);

        // Global Error Label
        globalErrorLabel = new Label();
        globalErrorLabel.setTextFill(javafx.scene.paint.Color.web(AppConstants.ERROR_COLOR));
        globalErrorLabel.setPadding(new Insets(5,20,5,20));
        globalErrorLabel.setMinHeight(25); // Reserve space
        HBox errorBox = new HBox(globalErrorLabel);
        errorBox.setAlignment(Pos.CENTER);
        rootLayout.setTop(errorBox); // ADD IT TO LAYOUT

        // Create and add navigation buttons before first showPage call
        HBox navigationBox = createNavigationButtons(); // Buttons (prevButton, nextButton) initialized HERE
        rootLayout.setBottom(navigationBox); // And added to layout HERE
        BorderPane.setAlignment(navigationBox, Pos.CENTER);
        BorderPane.setMargin(navigationBox, new Insets(0, 0, 20, 0));

        // Call showPage for the first time, after buttons are created
        showPage(currentPageIndex, false); // Show first page without animation initially

        Scene scene = new Scene(rootLayout, 650, 750);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private HBox createNavigationButtons() {
        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(10));

        prevButton = AppConstants.createStyledNavButton("Previous");
        prevButton.setOnAction(_ -> {
            globalErrorLabel.setText(""); // Clear error on navigation
            showPage(currentPageIndex - 1, true);
        });

        nextButton = AppConstants.createStyledNavButton("Next");
        nextButton.setOnAction(_ -> {
            globalErrorLabel.setText(""); // Clear error on navigation
            // Update data from current page before moving to the next
            boolean canProceed = pages.get(currentPageIndex).updater.updateUserData();

            if (canProceed) {
                if (currentPageIndex == pages.size() - 2) { // If next page is the results page
                    resultsPage.calculateAndDisplayResults();
                }
                showPage(currentPageIndex + 1, true);
            } else {
                // Display an error if updateUserData fails (e.g., validation error)
                globalErrorLabel.setText("Please correct the input on the current page.");
            }
        });

        prevButton.setDisable(true); // Initially disabled
        hbox.getChildren().addAll(prevButton, nextButton);
        return hbox;
    }

    private void showPage(int index, boolean animate) {
        if (index < 0 || index >= pages.size()) {
            return;
        }
        globalErrorLabel.setText(""); // Clear global error when changing page

        Node currentPageNode = pages.get(currentPageIndex).node;
        Node nextPageNode = pages.get(index).node;

        if (animate) {
            FadeTransition fadeOut = getFadeTransition(index, currentPageNode, nextPageNode);
            fadeOut.play();
        } else { // Show page directly without animation (for initial load)
            for(PageWrapper pw : pages){
                pw.node.setVisible(false);
                pw.node.setOpacity(1.0); // Reset opacity
            }
            nextPageNode.setVisible(true);
            currentPageIndex = index;
            updateNavigationButtons();
        }
    }

    private FadeTransition getFadeTransition(int index, Node currentPageNode, Node nextPageNode) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), currentPageNode);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(_ -> {
            currentPageNode.setVisible(false);
            nextPageNode.setOpacity(0.0);
            nextPageNode.setVisible(true);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(200), nextPageNode);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();

            currentPageIndex = index;
            updateNavigationButtons();
        });
        return fadeOut;
    }

    private void updateNavigationButtons() {
        prevButton.setDisable(currentPageIndex == 0);
        nextButton.setDisable(currentPageIndex == pages.size() - 1); // Disable next on the last page

        if (currentPageIndex == pages.size() - 2) { // Page before results
            nextButton.setText("Calculate & See Results");
        } else if (currentPageIndex == pages.size() - 1 ) { // On results page
            // Optionally change 'Next' to 'Start Over' or hide it
            // nextButton.setText("Start Over");
            // nextButton.setOnAction(e -> showPage(0, true)); // Example: Start over
        } else {
            nextButton.setText("Next");
        }
    }
}