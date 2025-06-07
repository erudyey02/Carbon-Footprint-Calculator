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
 * Displays help information, details about the calculation methodology,
 * and sources for the emission factors used.
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
        Label goodsMethodology = new Label("Goods Consumption Note: Spend-based emission factors for goods are highly complex and vary significantly by region and data model. The factors used here are illustrative placeholders and represent an area for future improvement with more specific, local data.");
        goodsMethodology.setWrapText(true); goodsMethodology.setMaxWidth(700); goodsMethodology.setPadding(new Insets(5,0,0,0));

        Label sourcesTitle = new Label("Emission Factor Sources:"); sourcesTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        sourcesTitle.setPadding(new Insets(15,0,5,0));
        VBox sourcesList = new VBox(8);

        // --- Energy Consumption Sources ---
        sourcesList.getChildren().add(createSectionTitle("Energy Consumption:"));
        sourcesList.getChildren().add(createSourceEntry("Grid Electricity (Luzon-Visayas):", AppConstants.EF_GRID_ELECTRICITY_LUZON_VISAYAS_KGCO2_PER_KWH + " kg CO2/kWh (CO2 only)", "JCM Project Design Documents PH-P001/PH-P002 (DOE Data 2015-2017). Note: CO2-only factor; comprehensive CO2e factor preferred."));
        sourcesList.getChildren().add(createSourceEntry("Grid Electricity (Mindanao):", AppConstants.EF_GRID_ELECTRICITY_MINDANAO_KGCO2_PER_KWH + " kg CO2/kWh (CO2 only)", "JCM Project Design Documents PH-P001/PH-P002 (DOE Data 2015-2017). Note: CO2-only factor."));
        sourcesList.getChildren().add(createSourceEntry("Grid Electricity (National Placeholder):", AppConstants.EF_GRID_ELECTRICITY_NATIONAL_PH_KGCO2E_PER_KWH_PLACEHOLDER + " kg CO2(e)/kWh", "Example placeholder (Climatiq 0.691 kgCO2/kWh - 2019 data, CO2 only). Actual CO2e factor from IEA or DENR preferred."));
        sourcesList.getChildren().add(createSourceEntry("LPG (Cooking):", AppConstants.EF_COOKING_LPG_KGCO2E_PER_KG + " kg CO2e/kg", "Derived from IPCC (2006) NCV & EFs, AR5 GWPs."));
        sourcesList.getChildren().add(createSourceEntry("Kerosene (Cooking):", AppConstants.EF_COOKING_KEROSENE_KGCO2E_PER_L + " kg CO2e/L", "Derived from IPCC (2006) NCV & EFs, assumed density 0.80 kg/L, AR5 GWPs."));
        sourcesList.getChildren().add(createSourceEntry("Charcoal (Cooking):", AppConstants.EF_COOKING_CHARCOAL_KGCO2E_PER_KG + " kg CO2e/kg", "Derived from IPCC (2006) NCV & EFs, AR5 GWPs (direct combustion only)."));
        sourcesList.getChildren().add(createSourceEntry("Wood (Cooking, Non-Sustainable):", AppConstants.EF_COOKING_WOOD_NON_SUSTAINABLE_KGCO2E_PER_KG + " kg CO2e/kg", "Derived from IPCC (2006) NCV & EFs, AR5 GWPs (includes CO2 from non-sustainable harvesting)."));
        sourcesList.getChildren().add(createSourceEntry("Wood (Cooking, Sustainable - CH4 & N2O only):", AppConstants.EF_COOKING_WOOD_SUSTAINABLE_KGCO2E_PER_KG + " kg CO2e/kg", "Derived from IPCC (2006) NCV & EFs, AR5 GWPs (CO2 considered biogenic neutral)."));
        sourcesList.getChildren().add(createSourceEntry("Water Supply (Manila Water):", AppConstants.EF_WATER_MANILA_KGCO2E_PER_M3 + " kg CO2e/m³", "Manila Water Sustainability Report 2018. Specific to Manila Water concession. (1 m³ = 1000 Liters)"));

        // --- Diet Sources ---
        sourcesList.getChildren().add(createSectionTitle("Diet:"));
        sourcesList.getChildren().add(createSourceEntry("Average Red Meat (PH Weighted):", AppConstants.EF_DIET_RED_MEAT_AVG_PH_KGCO2E_PER_KG + " kg CO2e/kg", "SE Asia farm-gate EFs (derived from FAOSTAT data per kg protein), weighted by PH consumption."));
        sourcesList.getChildren().add(createSourceEntry("Poultry (Chicken - PH LCA):", AppConstants.EF_DIET_POULTRY_CHICKEN_PH_LCA_KGCO2E_PER_KG_CW + " kg CO2e/kg CW", "Source: Espino & Bellotindos (2020), intensive broiler. CW = Carcass Weight."));
        sourcesList.getChildren().add(createSourceEntry("Average Fish/Seafood (Proxy):", AppConstants.EF_DIET_FISH_AVG_GLOBAL_PROXY_KGCO2E_PER_KG_CAUTION + " kg CO2e/kg", "Placeholder using PH Milkfish factor (source verification needed) or global proxy. Use with caution."));
        sourcesList.getChildren().add(createSourceEntry("Irrigated Rice (PH LCA):", AppConstants.EF_DIET_RICE_IRRIGATED_PH_KGCO2E_PER_KG + " kg CO2e/kg", "Source: Bautista et al. (2014); uses SAR-like GWPs."));
        sourcesList.getChildren().add(createSourceEntry("Rainfed Rice (PH LCA):", AppConstants.EF_DIET_RICE_RAINFED_PH_KGCO2E_PER_KG + " kg CO2e/kg", "Source: Bautista et al. (2014); uses SAR-like GWPs."));
        sourcesList.getChildren().add(createSourceEntry("Food Waste (Metro Manila Landfill):", AppConstants.EF_DIET_FOOD_WASTE_LANDFILL_MM_KGCO2E_PER_KG + " kg CO2e/kg", "Source: Derived from Oida et al. (2019); AR5 GWP for CH4."));
        sourcesList.getChildren().add(createSourceEntry("Avg. Plant-Based Staples (Proxy):", AppConstants.EF_DIET_PLANT_STAPLES_AVG_PROXY_KGCO2E_PER_KG + " kg CO2e/kg", "Placeholder based on global medians. Use with caution."));

        // --- Transportation Sources ---
        sourcesList.getChildren().add(createSectionTitle("Transportation:"));
        sourcesList.getChildren().add(createSourceEntry("Car - Gasoline Combustion:", AppConstants.EF_TRANSPORT_CAR_GASOLINE_COMBUSTION_KGCO2_PER_L + " kg CO2/L", "CO2 only. Source: SunEarthTools.com / US EPA data."));
        sourcesList.getChildren().add(createSourceEntry("Car - Diesel Combustion:", AppConstants.EF_TRANSPORT_CAR_DIESEL_COMBUSTION_KGCO2_PER_L + " kg CO2/L", "CO2 only. Source: SunEarthTools.com / US EPA data."));
        sourcesList.getChildren().add(createSourceEntry("Motorcycle (Derived):", AppConstants.EF_TRANSPORT_MOTORCYCLE_DERIVED_KGCO2_PER_KM + " kg CO2/km", "CO2 only. Derived from assumed fuel efficiency & gasoline EF."));
        sourcesList.getChildren().add(createSourceEntry("Tricycle (Passenger, Derived):", AppConstants.EF_TRANSPORT_TRICYCLE_PASSENGER_DERIVED_KGCO2_PER_PKM + " kg CO2/pkm", "CO2 only. Derived from Biona et al. (2007) & assumed 3 passengers."));
        sourcesList.getChildren().add(createSourceEntry("Jeepney (Passenger):", AppConstants.EF_TRANSPORT_JEEPNEY_PASSENGER_KGCO2E_PER_PKM_CAUTION + " kg CO2e/pkm", "Previously considered value; source verification needed. Use with caution."));
        sourcesList.getChildren().add(createSourceEntry("Bus (Passenger):", AppConstants.EF_TRANSPORT_BUS_PASSENGER_KGCO2E_PER_PKM_CAUTION + " kg CO2e/pkm", "Previously considered value; source verification needed. Use with caution."));
        sourcesList.getChildren().add(createSourceEntry("Train/MRT/LRT (Manila Estimate):", AppConstants.EF_TRANSPORT_TRAIN_MANILA_ESTIMATE_KGCO2E_PER_PKM + " kg CO2e/pkm", "Estimated based on ADB model structure & plausible energy/grid data. High uncertainty."));
        sourcesList.getChildren().add(createSourceEntry("Flights (Economy):", "~" + AppConstants.EF_TRANSPORT_FLIGHT_SHORT_HAUL_ECONOMY_RFI3_KGCO2E_PER_PKM + " - " + AppConstants.EF_TRANSPORT_FLIGHT_LONG_HAUL_ECONOMY_RFI3_KGCO2E_PER_PKM + " kg CO2e/pkm", "Global average, includes RFI=3. Source: Derived based on myclimate.org concepts. Varies by haul length."));

        // --- Waste Management Sources ---
        sourcesList.getChildren().add(createSectionTitle("Waste Management:"));
        sourcesList.getChildren().add(createSourceEntry("MSW to Landfill (Metro Manila):", AppConstants.EF_WASTE_MSW_LANDFILL_METRO_MANILA_KGCO2E_PER_KG + " kg CO2e/kg", "Source: Oida et al. (2019), likely AR4 GWP. Factor is for mixed waste to a managed anaerobic landfill."));

        // --- Goods Consumption Sources ---
        sourcesList.getChildren().add(createSectionTitle("Goods Consumption:"));
        sourcesList.getChildren().add(createSourceEntry("Clothing (Placeholder):", AppConstants.EF_GOODS_CLOTHING_KGCO2E_PER_PHP_PLACEHOLDER + " kg CO2e/PHP", "Placeholder value. Specific spend-based factors require complex EEIO model data not readily available."));
        sourcesList.getChildren().add(createSourceEntry("Electronics (Placeholder):", AppConstants.EF_GOODS_ELECTRONICS_KGCO2E_PER_PHP_PLACEHOLDER + " kg CO2e/PHP", "Placeholder value. Specific spend-based factors require complex EEIO model data not readily available."));


        Label generalNotes = new Label("General Notes:\n- This list of sources is not exhaustive and will be updated as more factors are integrated.\n- The calculator prioritizes Philippines-specific data. Global or regional averages are used as alternatives.\n- GWP (Global Warming Potential) values used for CO2e conversion are generally from IPCC AR5 unless specified otherwise.\n- Emission factors can vary. The values used represent best available estimates based on research.");
        generalNotes.setWrapText(true); generalNotes.setMaxWidth(700); generalNotes.setPadding(new Insets(10,0,0,0));
        contentLayout.getChildren().addAll(title, methodologyTitle, methodologyIntro, housingMethodology, goodsMethodology, sourcesTitle, sourcesList, generalNotes);
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