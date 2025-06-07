// File: org/letrancpe/carboncalculator/CarbonFootprintAppMain.java
package org.letrancpe.carboncalculator;

import org.letrancpe.carboncalculator.controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * CarbonFootprintAppMain serves as the entry point for the JavaFX application.
 * It configures the main stage and initializes the MainController,
 * which subsequently manages the application's views and overall MVC flow.
 *
 * To run this application, the JavaFX SDK must be configured correctly.
 * For modular projects (using module-info.java), the module path needs to be set
 * in the IDE or build system. For non-modular projects, VM options such as
 * --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.graphics
 * may be necessary. Newer JDKs might also require --enable-native-access=javafx.graphics.
 */
public class CarbonFootprintAppMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Carbon Footprint Calculator");

        // The root layout for the main application window.
        // This layout will be populated by the MainController.
        BorderPane rootLayout = new BorderPane();

        // Initialize the MainController.
        // The controller is responsible for setting up views and handling application logic.
        MainController mainController = new MainController(primaryStage, rootLayout);
        mainController.initLayout(); // Initializes navigation and the first page.

        Scene scene = new Scene(rootLayout, 1080, 720);

        // Basic styling to suggest the monochrome green theme.
        // This can be developed into a dedicated CSS file later.
        scene.getRoot().setStyle("-fx-font-family: 'Arial'; -fx-base: #E8F5E9;"); // Light green base color.

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}