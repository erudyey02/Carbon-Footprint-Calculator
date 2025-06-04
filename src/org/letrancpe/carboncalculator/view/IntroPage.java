// File: org/letrancpe/carboncalculator/view/IntroPage.java
package org.letrancpe.carboncalculator.view;

import org.letrancpe.carboncalculator.controller.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * IntroPage (View component)
 * Displays the introduction screen of the application.
 */
public class IntroPage {

    private MainController controller;

    public IntroPage(MainController controller) {
        this.controller = controller;
    }

    public Node getView() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #F1F8E9; -fx-border-color: #C8E6C9; -fx-border-width: 1; -fx-border-radius: 5;");

        Label title = new Label("Welcome to the Carbon Footprint Calculator!");
        title.setFont(Font.font("Arial", 24));
        title.setTextAlignment(TextAlignment.CENTER);
        title.setWrapText(true);

        Label description = new Label(
                "This application helps estimate an annual carbon footprint " +
                        "based on lifestyle in the Philippines. It guides through " +
                        "sections for Housing, Energy, Transport, Diet, Waste, and Goods Consumption.\n\n" +
                        "Let's get started!"
        );
        description.setFont(Font.font("Arial", 14));
        description.setTextAlignment(TextAlignment.CENTER);
        description.setWrapText(true);
        description.setMaxWidth(500);

        Button startButton = new Button("Begin Calculation");
        startButton.setFont(Font.font("Arial", 16));
        startButton.setStyle("-fx-background-color: #66BB6A; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5;");
        startButton.setOnAction(e -> {
            // Navigation to the first data input page (e.g., Housing & Energy).
            // This is illustrative; actual navigation is primarily handled by MainController's top bar.
            // A more direct navigation method on the controller could be called here if desired.
            System.out.println("Intro Page: 'Begin Calculation' clicked. Use the top navigation bar to proceed to 'Housing & Energy'.");
        });

        layout.getChildren().addAll(title, description, startButton);
        return layout;
    }
}
