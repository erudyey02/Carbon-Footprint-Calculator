// File: org/letrancpe/carboncalculator/model/AppConstants.java
package org.letrancpe.carboncalculator.model;

/**
 * AppConstants (Model) stores constants used throughout the application,
 * particularly emission factors. These are based on data from the 
 * "Emission Factors Research and Analysis_.pdf" document.
 */
public class AppConstants {

    // === I. HOUSING ===
    // (No direct emission factors for dwelling structure/efficiency rating in this phase)

    // === II. ENERGY CONSUMPTION ===
    // --- Grid Electricity ---
    public static final double EF_GRID_ELECTRICITY_LUZON_VISAYAS_KGCO2_PER_KWH = 0.507; // CO2 only, JCM (DOE Data 2015-2017)
    public static final double EF_GRID_ELECTRICITY_MINDANAO_KGCO2_PER_KWH = 0.468;     // CO2 only, JCM (DOE Data 2015-2017)
    public static final double EF_GRID_ELECTRICITY_NATIONAL_PH_KGCO2E_PER_KWH_PLACEHOLDER = 0.691; // Placeholder, Climatiq (2019 data, CO2 only)

    // --- Cooking Fuels ---
    public static final double EF_COOKING_LPG_KGCO2E_PER_KG = 2.99;                     // Derived IPCC (2006) NCV & EFs, AR5 GWPs
    public static final double EF_COOKING_KEROSENE_KGCO2E_PER_L = 2.53;                 // Derived IPCC (2006) NCV & EFs, density 0.80 kg/L, AR5 GWPs
    public static final double EF_COOKING_CHARCOAL_KGCO2E_PER_KG = 3.48;                // Derived IPCC (2006) NCV & EFs, AR5 GWPs (direct combustion)
    public static final double EF_COOKING_WOOD_SUSTAINABLE_KGCO2E_PER_KG = 0.15;        // Derived IPCC (2006) NCV & EFs, AR5 GWPs (CH4 & N2O only)
    public static final double EF_COOKING_WOOD_NON_SUSTAINABLE_KGCO2E_PER_KG = 1.90;    // Derived IPCC (2006) NCV & EFs, AR5 GWPs (incl. CO2)

    // --- Water Supply ---
    public static final double EF_WATER_MANILA_KGCO2E_PER_M3 = 0.195;                   // Manila Water Sustainability Report 2018 (1 m³ = 1000 L)

    // === III. DIET ===
    // --- Meat & Poultry ---
    /** Average Red Meat (PH consumption weighted, SE Asia farm-gate EFs). Unit: kgCO2e/kg (raw, as purchased) */
    public static final double EF_DIET_RED_MEAT_AVG_PH_KGCO2E_PER_KG = 10.8;
    /** Poultry (Chicken - Intensive Broiler, PH LCA). Unit: kgCO2e/kg Carcass Weight (proxy for raw, as purchased) */
    public static final double EF_DIET_POULTRY_CHICKEN_PH_LCA_KGCO2E_PER_KG_CW = 5.0; // Source: Espino & Bellotindos, 2020

    // --- Fish/Seafood ---
    /** Farmed Milkfish (PH) - Use with caution, source verification needed. Unit: kgCO2e/kg (assumed raw, as purchased) */
    public static final double EF_DIET_FISH_MILKFISH_PH_KGCO2E_PER_KG_CAUTION = 8.24; // Placeholder, needs verified source
    /** General Global Proxy for Average Fish/Seafood - Use with extreme caution. Unit: kgCO2e/kg (assumed raw, as purchased) */
    public static final double EF_DIET_FISH_AVG_GLOBAL_PROXY_KGCO2E_PER_KG_CAUTION = 8.24; // Using Milkfish as a more conservative proxy than 13.4

    // --- Rice ---
    /** Irrigated Rice (PH LCA, Bautista et al., 2014; SAR-like GWPs). Unit: kgCO2e/kg (uncooked) */
    public static final double EF_DIET_RICE_IRRIGATED_PH_KGCO2E_PER_KG = 0.93;
    /** Rainfed Rice (PH LCA, Bautista et al., 2014; SAR-like GWPs). Unit: kgCO2e/kg (uncooked) */
    public static final double EF_DIET_RICE_RAINFED_PH_KGCO2E_PER_KG = 0.47;

    // --- Food Waste ---
    /** Food Waste (to landfill, Metro Manila conditions). Unit: kgCO2e/kg of food waste */
    public static final double EF_DIET_FOOD_WASTE_LANDFILL_MM_KGCO2E_PER_KG = 1.40; // Derived Oida et al. 2019 (AR5 GWP for CH4)

    // --- Plant-Based Staples ---
    /** General Global Proxy for Average Plant-Based Staples (excluding rice) - Use with caution. Unit: kgCO2e/kg */
    public static final double EF_DIET_PLANT_STAPLES_AVG_PROXY_KGCO2E_PER_KG = 0.7; // Placeholder, based on global medians

    // --- Diet Calculation Helpers ---
    /** Assumed average serving size for meat/poultry/fish. Unit: kg per serving */
    public static final double AVG_SERVING_SIZE_MEAT_KG = 0.125; // 125g average serving
    /** Number of servings per year for "Daily" frequency. */
    public static final int SERVINGS_PER_YEAR_DAILY = 365;
    /** Number of servings per year for "3-5 times/week" frequency (average 4 times/week). */
    public static final int SERVINGS_PER_YEAR_FREQ = 4 * 52; // 208
    /** Number of servings per year for "1-2 times/week" frequency (average 1.5 times/week). */
    public static final int SERVINGS_PER_YEAR_OCCAS = (int) (1.5 * 52); // 78
    /** Number of servings per year for "Rarely" frequency (e.g., once a month). */
    public static final int SERVINGS_PER_YEAR_RARELY = 12;
    /** Assumed weight of one rice serving. Unit: kg (uncooked) */
    public static final double RICE_SERVING_SIZE_KG = 0.060; // 60g uncooked


    // === IV. TRANSPORTATION ===
    // (To be populated)

    // === V. WASTE MANAGEMENT ===
    // (To be populated)

    // === VI. GOODS CONSUMPTION ===
    // (To be populated)

    // --- UI Constants ---
    public static final String APP_TITLE = "Carbon Footprint Calculator PH";

    private AppConstants() {}
}
