// File: org/letrancpe/carboncalculator/view/HelpPage.java
package org.letrancpe.carboncalculator.view;

import org.letrancpe.carboncalculator.controller.MainController;
import org.letrancpe.carboncalculator.model.AppConstants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight; // Correctly import FontWeight
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * HelpPage (View component)
 * Displays help information, details about the calculation methodology,
 * and sources for the emission factors used.
 */
public class HelpPage {

    private MainController controller;

    public HelpPage(MainController controller) {
        this.controller = controller;
    }

    public Node getView() {
        VBox contentLayout = new VBox(15);
        contentLayout.setPadding(new Insets(20));
        contentLayout.setAlignment(Pos.TOP_LEFT);
        contentLayout.setStyle("-fx-background-color: #F1F8E9;");

        Label title = new Label("Help & Information");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20)); // Corrected: Use FontWeight.BOLD

        // --- Methodology Introduction Section ---
        Label methodologyTitle = new Label("Calculation Methodology");
        methodologyTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16)); // Corrected: Use FontWeight.BOLD
        methodologyTitle.setPadding(new Insets(10,0,5,0));

        Label methodologyIntro = new Label(
                "This calculator estimates an annual carbon footprint in kilograms of CO2 equivalent (kg CO2e) " +
                        "based on data provided about lifestyle. Emission factors, which convert activities " +
                        "(e.g., kWh of electricity, liters of fuel) into CO2e, are primarily sourced with a focus on the " +
                        "Philippines where available. Global or regional averages are used otherwise, with all sources noted below."
        );
        methodologyIntro.setWrapText(true);
        methodologyIntro.setMaxWidth(700);

        Label housingMethodology = new Label(
                "Housing Footprint Note: Currently, the calculator does not assign a direct CO2e value for different " +
                        "dwelling types or structural energy efficiency ratings. This is due to limited availability of specific, " +
                        "generalizable emission factors for these aspects in the Philippines (e.g., for embodied carbon of building structures). " +
                        "The housing-related carbon footprint is calculated based on reported operational energy consumption (electricity, cooking fuels) " +
                        "and water usage. Information on dwelling type and efficiency features helps in tailoring recommendations."
        );
        housingMethodology.setWrapText(true);
        housingMethodology.setMaxWidth(700);
        housingMethodology.setPadding(new Insets(5,0,0,0));


        // --- Emission Factor Sources Section ---
        Label sourcesTitle = new Label("Emission Factor Sources:");
        sourcesTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16)); // Corrected: Use FontWeight.BOLD
        sourcesTitle.setPadding(new Insets(15,0,5,0));

        VBox sourcesList = new VBox(8);

        // Energy Consumption Sources
        sourcesList.getChildren().add(createSectionTitle("Energy Consumption:"));
        sourcesList.getChildren().add(createSourceEntry(
                "Grid Electricity (Luzon-Visayas):",
                AppConstants.EF_GRID_ELECTRICITY_LUZON_VISAYAS_KGCO2_PER_KWH + " kg CO2/kWh (CO2 only)",
                "JCM Project Design Documents PH-P001/PH-P002 (DOE Data 2015-2017). Note: This is a CO2-only factor; a comprehensive CO2e factor is preferred if available."
        ));
        sourcesList.getChildren().add(createSourceEntry(
                "Grid Electricity (Mindanao):",
                AppConstants.EF_GRID_ELECTRICITY_MINDANAO_KGCO2_PER_KWH + " kg CO2/kWh (CO2 only)",
                "JCM Project Design Documents PH-P001/PH-P002 (DOE Data 2015-2017). Note: CO2-only factor."
        ));
        sourcesList.getChildren().add(createSourceEntry(
                "Grid Electricity (National Placeholder):",
                AppConstants.EF_GRID_ELECTRICITY_NATIONAL_PH_KGCO2E_PER_KWH_PLACEHOLDER + " kg CO2(e)/kWh",
                "Example placeholder (Climatiq 0.691 kgCO2/kWh - 2019 data, noted as CO2 only). An actual CO2e factor from IEA or DENR is preferred."
        ));
        sourcesList.getChildren().add(createSourceEntry(
                "LPG (Cooking):",
                AppConstants.EF_COOKING_LPG_KGCO2E_PER_KG + " kg CO2e/kg",
                "Derived from IPCC (2006) NCV & EFs, AR5 GWPs."
        ));
        sourcesList.getChildren().add(createSourceEntry(
                "Kerosene (Cooking):",
                AppConstants.EF_COOKING_KEROSENE_KGCO2E_PER_L + " kg CO2e/L",
                "Derived from IPCC (2006) NCV & EFs, assumed density 0.80 kg/L, AR5 GWPs."
        ));
        sourcesList.getChildren().add(createSourceEntry(
                "Charcoal (Cooking):",
                AppConstants.EF_COOKING_CHARCOAL_KGCO2E_PER_KG + " kg CO2e/kg",
                "Derived from IPCC (2006) NCV & EFs, AR5 GWPs (direct combustion only)."
        ));
        sourcesList.getChildren().add(createSourceEntry(
                "Wood (Cooking, Non-Sustainable):",
                AppConstants.EF_COOKING_WOOD_NON_SUSTAINABLE_KGCO2E_PER_KG + " kg CO2e/kg",
                "Derived from IPCC (2006) NCV & EFs, AR5 GWPs (includes CO2 from non-sustainable harvesting)."
        ));
        sourcesList.getChildren().add(createSourceEntry(
                "Wood (Cooking, Sustainable - CH4 & N2O only):",
                AppConstants.EF_COOKING_WOOD_SUSTAINABLE_KGCO2E_PER_KG + " kg CO2e/kg",
                "Derived from IPCC (2006) NCV & EFs, AR5 GWPs (CO2 considered biogenic neutral)."
        ));
        sourcesList.getChildren().add(createSourceEntry(
                "Water Supply (Manila Water):",
                AppConstants.EF_WATER_MANILA_KGCO2E_PER_M3 + " kg CO2e/m³",
                "Manila Water Sustainability Report 2018. Specific to Manila Water concession. (1 m³ = 1000 Liters)"
        ));

        // TODO: Add source entries for Diet, Transportation, Waste, and Goods categories as their AppConstants are populated.
        // Example for Diet:
        // sourcesList.getChildren().add(createSectionTitle("Diet:"));
        // sourcesList.getChildren().add(createSourceEntry("Beef (SE Asia Average):", AppConstants.EF_DIET_BEEF_KGCO2E_PER_KG + " kg CO2e/kg", "Derived from FAOSTAT data per kg protein, SE Asia farm-gate."));

        Label generalNotes = new Label(
                "General Notes:\n" +
                        "- This list of sources is not exhaustive and will be updated as more factors are integrated into the calculator.\n" +
                        "- The calculator prioritizes Philippines-specific data when available. Global or regional averages are used as alternatives.\n" +
                        "- GWP (Global Warming Potential) values used for CO2e conversion are generally from IPCC AR5 unless specified otherwise by the source documentation for a particular emission factor.\n" +
                        "- Emission factors can vary based on specific technologies, local conditions, and the vintage of the data. The values used represent the best available estimates based on the research conducted for this project."
        );
        generalNotes.setWrapText(true);
        generalNotes.setMaxWidth(700);
        generalNotes.setPadding(new Insets(10,0,0,0));

        contentLayout.getChildren().addAll(title, methodologyTitle, methodologyIntro, housingMethodology, sourcesTitle, sourcesList, generalNotes);

        ScrollPane scrollPane = new ScrollPane(contentLayout); // Makes the content scrollable.
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #F1F8E9;");

        return scrollPane;
    }

    private Label createSectionTitle(String title) {
        Label sectionTitle = new Label(title);
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Corrected: Use FontWeight.BOLD
        sectionTitle.setPadding(new Insets(10, 0, 2, 0));
        return sectionTitle;
    }

    private TextFlow createSourceEntry(String item, String value, String sourceDetails) {
        Text itemText = new Text(item + " ");
        itemText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 12)); // Corrected: Use FontWeight.SEMI_BOLD
        Text valueText = new Text(value + "\n");
        valueText.setFont(Font.font("Arial", FontWeight.NORMAL, 12)); // Use FontWeight.NORMAL for regular text
        Text sourceText = new Text("    Source/Note: " + sourceDetails + "\n");
        sourceText.setFont(Font.font("Arial", FontWeight.NORMAL, 11)); // Use FontWeight.NORMAL
        sourceText.setFill(Color.SLATEGRAY);

        TextFlow textFlow = new TextFlow(itemText, valueText, sourceText);
        textFlow.setMaxWidth(680);
        textFlow.setPadding(new Insets(0,0,5,0));
        return textFlow;
    }
}
