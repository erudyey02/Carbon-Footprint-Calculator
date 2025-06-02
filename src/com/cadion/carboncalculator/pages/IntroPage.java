package com.cadion.carboncalculator.pages;

import com.cadion.carboncalculator.AppConstants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class IntroPage {

    public Node createPage() {
        VBox page = new VBox(20);
        page.setAlignment(Pos.CENTER);
        page.setPadding(new Insets(30));
        page.setStyle("-fx-background-color: " + AppConstants.BG_CONTENT_CARD + "; -fx-background-radius: 10; -fx-border-color: "+AppConstants.BORDER_COLOR_LIGHT+"; -fx-border-radius: 10;");

        Text title = new Text("Welcome!");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        title.setFill(Color.web(AppConstants.TEXT_PRIMARY_COLOR));

        Label introText = new Label("Let's estimate your annual carbon footprint.\nAnswer a few questions about your lifestyle habits.\n\nClick 'Next' to begin.");
        introText.setFont(Font.font("Arial", 16));
        introText.setTextFill(Color.web(AppConstants.TEXT_SECONDARY_COLOR));
        introText.setWrapText(true);
        introText.setTextAlignment(TextAlignment.CENTER);

        page.getChildren().addAll(title, introText);
        return page;
    }
}