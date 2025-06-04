// File: src/module-info.java
module org.letrancpe.carboncalculator {
    // Declare dependencies on the JavaFX modules the aplication uses.
    requires javafx.controls; // For UI controls, layouts, etc.
    requires javafx.graphics; // For basic graphics, scene, stage, color, font, etc.

    // 'opens' directives allow specific JavaFX modules to access packages
    // via reflection. This is important if JavaFX needs to, for example,
    // instantiate controllers from FXML (not used in current base), or access
    // properties of custom UI components.
    // Opening to javafx.graphics is a common requirement.
    opens org.letrancpe.carboncalculator.controller to javafx.graphics;
    opens org.letrancpe.carboncalculator.view to javafx.graphics;
    opens org.letrancpe.carboncalculator.model to javafx.graphics; // If model classes are directly bound in UI in complex ways
    opens org.letrancpe.carboncalculator to javafx.graphics; // Opens the base package

    // 'exports' makes packages available to other modules if this
    // project were to be used as a library. For a standalone application,
    // exports are less critical but good practice.
    exports org.letrancpe.carboncalculator;
    exports org.letrancpe.carboncalculator.controller;
    exports org.letrancpe.carboncalculator.model;
    exports org.letrancpe.carboncalculator.model.dto;
    exports org.letrancpe.carboncalculator.service;
    exports org.letrancpe.carboncalculator.service.impl;
    exports org.letrancpe.carboncalculator.view;
}