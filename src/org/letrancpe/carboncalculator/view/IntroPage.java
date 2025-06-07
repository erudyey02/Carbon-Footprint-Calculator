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
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * IntroPage (View component)
 */
public class IntroPage {
    private final MainController controller;
    public IntroPage(MainController controller) { this.controller = controller; }
    public Node getView() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #F1F8E9; -fx-border-color: #C8E6C9; -fx-border-width: 1; -fx-border-radius: 5;");
        Label title = new Label("Welcome to the Carbon Footprint Calculator!");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextAlignment(TextAlignment.CENTER);
        title.setWrapText(true);
        Label description = new Label("This application helps estimate an annual carbon footprint based on lifestyle in the Philippines. It guides through sections for Housing, Energy, Transport, Diet, Waste, and Goods Consumption.\n\nLet's get started!");
        description.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        description.setTextAlignment(TextAlignment.CENTER);
        description.setWrapText(true);
        description.setMaxWidth(500);
        Button startButton = new Button("Begin Calculation");
        startButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        startButton.setStyle("-fx-background-color: #66BB6A; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5;");
        startButton.setOnAction(_ -> System.out.println("Intro Page: 'Begin Calculation' clicked. Use the top navigation bar to proceed to 'Housing & Energy'."));
        layout.getChildren().addAll(title, description, startButton);
        return layout;
    }
}
