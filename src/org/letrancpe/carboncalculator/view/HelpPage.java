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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * HelpPage (View component)
 */
public class HelpPage {
    private final MainController controller;
    public HelpPage(MainController controller) { this.controller = controller; }
    public Node getView() {
        VBox contentLayout = new VBox(15);
        contentLayout.setPadding(new Insets(20)); contentLayout.setAlignment(Pos.TOP_LEFT);
        contentLayout.setStyle("-fx-background-color: #F1F8E9;");
        Label title = new Label("Help & Information"); title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Label methodologyTitle = new Label("Calculation Methodology"); methodologyTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        methodologyTitle.setPadding(new Insets(10,0,5,0));
        Label methodologyIntro = new Label("This calculator estimates an annual carbon footprint in kilograms of CO2 equivalent (kg CO2e) based on data provided about lifestyle. Emission factors, which convert activities (e.g., kWh of electricity, liters of fuel) into CO2e, are primarily sourced with a focus on the Philippines where available. Global or regional averages are used otherwise, with all sources noted below.");
        methodologyIntro.setWrapText(true); methodologyIntro.setMaxWidth(700);
        Label housingMethodology = new Label("Housing Footprint Note: Currently, the calculator does not assign a direct CO2e value for different dwelling types or structural energy efficiency ratings. This is due to limited availability of specific, generalizable emission factors for these aspects in the Philippines (e.g., for embodied carbon of building structures). The housing-related carbon footprint is calculated based on reported operational energy consumption (electricity, cooking fuels) and water usage. Information on dwelling type and efficiency features helps in tailoring recommendations.");
        housingMethodology.setWrapText(true); housingMethodology.setMaxWidth(700); housingMethodology.setPadding(new Insets(5,0,0,0));
        Label sourcesTitle = new Label("Emission Factor Sources:"); sourcesTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        sourcesTitle.setPadding(new Insets(15,0,5,0));
        VBox sourcesList = new VBox(8);

        sourcesList.getChildren().add(createSectionTitle("Energy Consumption:"));
        // ... (Energy sources implementation unchanged)

        sourcesList.getChildren().add(createSectionTitle("Diet:"));
        // ... (Diet sources implementation unchanged)

        sourcesList.getChildren().add(createSectionTitle("Transportation:"));
        // ... (Transportation sources implementation unchanged)

        sourcesList.getChildren().add(createSectionTitle("Waste Management:"));
        sourcesList.getChildren().add(createSourceEntry("MSW to Landfill (Metro Manila):", AppConstants.EF_WASTE_MSW_LANDFILL_METRO_MANILA_KGCO2E_PER_KG + " kg CO2e/kg", "Source: Oida et al. (2019), likely AR4 GWP. Factor is for mixed waste to a managed anaerobic landfill."));

        sourcesList.getChildren().add(createSectionTitle("Goods Consumption:"));
        sourcesList.getChildren().add(createSourceEntry("Clothing (Placeholder):", AppConstants.EF_GOODS_CLOTHING_KGCO2E_PER_PHP_PLACEHOLDER + " kg CO2e/PHP", "Placeholder value. Specific spend-based factors require complex EEIO model data not readily available."));
        sourcesList.getChildren().add(createSourceEntry("Electronics (Placeholder):", AppConstants.EF_GOODS_ELECTRONICS_KGCO2E_PER_PHP_PLACEHOLDER + " kg CO2e/PHP", "Placeholder value. Specific spend-based factors require complex EEIO model data not readily available."));


        Label generalNotes = new Label("General Notes:\n- This list of sources is not exhaustive and will be updated as more factors are integrated.\n- The calculator prioritizes Philippines-specific data. Global or regional averages are used as alternatives.\n- GWP (Global Warming Potential) values used for CO2e conversion are generally from IPCC AR5 unless specified otherwise.\n- Emission factors can vary. The values used represent best available estimates based on research.");
        generalNotes.setWrapText(true); generalNotes.setMaxWidth(700); generalNotes.setPadding(new Insets(10,0,0,0));
        contentLayout.getChildren().addAll(title, methodologyTitle, methodologyIntro, housingMethodology, sourcesTitle, sourcesList, generalNotes);
        ScrollPane scrollPane = new ScrollPane(contentLayout); scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #F1F8E9;");
        return scrollPane;
    }
    private Label createSectionTitle(String title) { Label sectionTitle = new Label(title); sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14)); sectionTitle.setPadding(new Insets(10, 0, 2, 0)); return sectionTitle; }
    private TextFlow createSourceEntry(String item, String value, String sourceDetails) {
        Text itemText = new Text(item + " "); itemText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 12));
        Text valueText = new Text(value + "\n"); valueText.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        Text sourceText = new Text("    Source/Note: " + sourceDetails + "\n"); sourceText.setFont(Font.font("Arial", FontWeight.NORMAL, 11)); sourceText.setFill(Color.SLATEGRAY);
        TextFlow textFlow = new TextFlow(itemText, valueText, sourceText); textFlow.setMaxWidth(680); textFlow.setPadding(new Insets(0,0,5,0)); return textFlow;
    }
}
