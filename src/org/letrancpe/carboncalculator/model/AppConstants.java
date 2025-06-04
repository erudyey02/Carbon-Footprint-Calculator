// File: org/letrancpe/carboncalculator/model/AppConstants.java
package org.letrancpe.carboncalculator.model;

/**
 * AppConstants (Model) stores constants used throughout the application,
 * particularly emission factors. These are based on data from the 
 * "Emission Factors Research and Analysis_.pdf" document.
 */
public class AppConstants {

    // === I. HOUSING ===
    // As per previous discussions, direct emission factors for dwelling structure or efficiency ratings
    // are not used in this phase for CO2e calculation. These inputs primarily inform recommendations.

    // === II. ENERGY CONSUMPTION ===

    // --- Grid Electricity ---
    // Note: The initial factors below are CO2-only. Comprehensive CO2e factors from sources like the
    // latest IEA data are preferred and should be sought. The calculator should ideally allow users
    // to select their grid or use a national average if their region is unknown.
    /**
     * Luzon-Visayas Grid electricity emission factor.
     * Unit: kg CO2/kWh (This factor is CO2 only)
     * Source: JCM Project Design Documents PH-P001/PH-P002 (DOE Data 2015-2017)
     */
    public static final double EF_GRID_ELECTRICITY_LUZON_VISAYAS_KGCO2_PER_KWH = 0.507;

    /**
     * Mindanao Grid electricity emission factor.
     * Unit: kg CO2/kWh (This factor is CO2 only)
     * Source: JCM Project Design Documents PH-P001/PH-P002 (DOE Data 2015-2017)
     */
    public static final double EF_GRID_ELECTRICITY_MINDANAO_KGCO2_PER_KWH = 0.468;

    /**
     * Placeholder for a National Average Philippine Grid electricity emission factor.
     * Unit: kg CO2e/kWh (Ideally CO2e, but may start with CO2 if that's the only available data)
     * Source: Requires further research (e.g., latest IEA data). Climatiq (0.691 kgCO2/kWh, 2019 data, noted as CO2 only) is an example.
     */
    public static final double EF_GRID_ELECTRICITY_NATIONAL_PH_KGCO2E_PER_KWH_PLACEHOLDER = 0.691; // Example placeholder value

    // --- Cooking Fuels ---
    /**
     * LPG (Liquefied Petroleum Gas) emission factor.
     * Unit: kg CO2e/kg of LPG
     * Source: Derived from IPCC (2006) NCV & EFs, AR5 GWPs (as per research document)
     */
    public static final double EF_COOKING_LPG_KGCO2E_PER_KG = 2.99;

    /**
     * Kerosene emission factor.
     * Unit: kg CO2e/L of Kerosene
     * Source: Derived from IPCC (2006) NCV & EFs, assumed density 0.80 kg/L, AR5 GWPs (as per research document)
     */
    public static final double EF_COOKING_KEROSENE_KGCO2E_PER_L = 2.53;

    /**
     * Charcoal emission factor (represents direct combustion only).
     * Unit: kg CO2e/kg of Charcoal
     * Source: Derived from IPCC (2006) NCV & EFs, AR5 GWPs (as per research document)
     */
    public static final double EF_COOKING_CHARCOAL_KGCO2E_PER_KG = 3.48;

    /**
     * Wood emission factor (accounts for CH4 & N2O only, assuming sustainable sourcing where CO2 is biogenic neutral).
     * Unit: kg CO2e/kg of Wood
     * Source: Derived from IPCC (2006) NCV & EFs, AR5 GWPs (as per research document)
     */
    public static final double EF_COOKING_WOOD_SUSTAINABLE_KGCO2E_PER_KG = 0.15;

    /**
     * Wood emission factor (includes CO2, assuming non-sustainably sourced fuelwood).
     * Unit: kg CO2e/kg of Wood
     * Source: Derived from IPCC (2006) NCV & EFs, AR5 GWPs (as per research document)
     */
    public static final double EF_COOKING_WOOD_NON_SUSTAINABLE_KGCO2E_PER_KG = 1.90;

    // --- Water Supply ---
    /**
     * Water supply emission factor (specific to Manila Water concession area).
     * Unit: kg CO2e/m³ (cubic meter)
     * Source: Manila Water Sustainability Report 2018 (as per research document)
     * Note: 1 cubic meter = 1000 Liters.
     */
    public static final double EF_WATER_MANILA_KGCO2E_PER_M3 = 0.195;
    // TODO: Research a national average or factors for other regions for broader applicability.

    // === III. DIET ===
    // (Constants for diet-related emission factors will be populated here based on research findings).
    // Example structure from research:
    // public static final double EF_DIET_BEEF_SE_ASIA_KGCO2E_PER_KG = 67.2;
    // public static final double EF_DIET_RICE_IRRIGATED_PH_KGCO2E_PER_KG = 0.93; // Bautista et al. 2014 (SAR GWPs)

    // === IV. TRANSPORTATION ===
    // (Constants for transportation-related emission factors will be populated here).
    // Example structure from research:
    // public static final double EF_TRANSPORT_CAR_GASOLINE_COMBUSTION_KGCO2_PER_L = 2.30; // CO2 only from combustion.

    // === V. WASTE MANAGEMENT ===
    // (Constants for waste management emission factors will be populated here).
    // Example structure from research:
    // public static final double EF_WASTE_MSW_LANDFILL_METRO_MANILA_KGCO2E_PER_KG = 4.05; // Oida et al. 2019 (likely AR4 GWP)

    // === VI. GOODS CONSUMPTION ===
    // (Constants for goods consumption, likely spend-based factors, will be populated here. These are TBD and complex).


    // --- UI Constants ---
    public static final String APP_TITLE = "Carbon Footprint Calculator PH";

    // Private constructor to prevent instantiation of this utility class.
    private AppConstants() {}
}
