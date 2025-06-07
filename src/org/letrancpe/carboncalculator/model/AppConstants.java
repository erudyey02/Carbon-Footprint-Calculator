// File: org/letrancpe/carboncalculator/model/AppConstants.java
package org.letrancpe.carboncalculator.model;

/**
 * AppConstants (Model) stores constants used throughout the application.
 */
public class AppConstants {

    // === I. HOUSING ===
    // (No direct emission factors for dwelling structure/efficiency rating in this phase)

    // === II. ENERGY CONSUMPTION ===
    public static final double EF_GRID_ELECTRICITY_LUZON_VISAYAS_KGCO2_PER_KWH = 0.507;
    public static final double EF_GRID_ELECTRICITY_MINDANAO_KGCO2_PER_KWH = 0.468;
    public static final double EF_GRID_ELECTRICITY_NATIONAL_PH_KGCO2E_PER_KWH_PLACEHOLDER = 0.691;
    public static final double EF_COOKING_LPG_KGCO2E_PER_KG = 2.99;
    public static final double EF_COOKING_KEROSENE_KGCO2E_PER_L = 2.53;
    public static final double EF_COOKING_CHARCOAL_KGCO2E_PER_KG = 3.48;
    public static final double EF_COOKING_WOOD_SUSTAINABLE_KGCO2E_PER_KG = 0.15;
    public static final double EF_COOKING_WOOD_NON_SUSTAINABLE_KGCO2E_PER_KG = 1.90;
    public static final double EF_WATER_MANILA_KGCO2E_PER_M3 = 0.195;

    // === III. DIET ===
    public static final double EF_DIET_RED_MEAT_AVG_PH_KGCO2E_PER_KG = 10.8;
    public static final double EF_DIET_POULTRY_CHICKEN_PH_LCA_KGCO2E_PER_KG_CW = 5.0;
    public static final double EF_DIET_FISH_AVG_GLOBAL_PROXY_KGCO2E_PER_KG_CAUTION = 8.24;
    public static final double EF_DIET_RICE_IRRIGATED_PH_KGCO2E_PER_KG = 0.93;
    public static final double EF_DIET_RICE_RAINFED_PH_KGCO2E_PER_KG = 0.47;
    public static final double EF_DIET_FOOD_WASTE_LANDFILL_MM_KGCO2E_PER_KG = 1.40;
    public static final double EF_DIET_PLANT_STAPLES_AVG_PROXY_KGCO2E_PER_KG = 0.7;
    public static final double AVG_SERVING_SIZE_MEAT_KG = 0.125;
    public static final int SERVINGS_PER_YEAR_DAILY = 365;
    public static final int SERVINGS_PER_YEAR_FREQ = 208;
    public static final int SERVINGS_PER_YEAR_OCCAS = 78;
    public static final int SERVINGS_PER_YEAR_RARELY = 12;
    public static final double RICE_SERVING_SIZE_KG = 0.060;

    // === IV. TRANSPORTATION ===
    public static final double EF_TRANSPORT_CAR_GASOLINE_COMBUSTION_KGCO2_PER_L = 2.30;
    public static final double EF_TRANSPORT_CAR_DIESEL_COMBUSTION_KGCO2_PER_L = 2.63;
    public static final double EF_TRANSPORT_MOTORCYCLE_DERIVED_KGCO2_PER_KM = 0.058;
    public static final double EF_TRANSPORT_TRICYCLE_VEHICLE_KGCO2_PER_KM = 0.041;
    public static final double EF_TRANSPORT_TRICYCLE_PASSENGER_DERIVED_KGCO2_PER_PKM = 0.014;
    public static final double EF_TRANSPORT_JEEPNEY_PASSENGER_KGCO2E_PER_PKM_CAUTION = 0.0242;
    public static final double EF_TRANSPORT_BUS_PASSENGER_KGCO2E_PER_PKM_CAUTION = 0.0097;
    public static final double EF_TRANSPORT_TRAIN_MANILA_ESTIMATE_KGCO2E_PER_PKM = 0.045;
    public static final double EF_TRANSPORT_FLIGHT_SHORT_HAUL_ECONOMY_RFI3_KGCO2E_PER_PKM = 0.28;
    public static final double EF_TRANSPORT_FLIGHT_MEDIUM_HAUL_ECONOMY_RFI3_KGCO2E_PER_PKM = 0.23;
    public static final double EF_TRANSPORT_FLIGHT_LONG_HAUL_ECONOMY_RFI3_KGCO2E_PER_PKM = 0.18;
    public static final double FLIGHT_BUSINESS_CLASS_MULTIPLIER = 1.5;

    // === V. WASTE MANAGEMENT ===
    public static final double EF_WASTE_MSW_LANDFILL_METRO_MANILA_KGCO2E_PER_KG = 4.05; // Oida et al. 2019 (likely AR4 GWP)

    // === VI. GOODS CONSUMPTION ===
    // Note: Spend-based factors are highly complex and region-specific. These are placeholders.
    // Further research into Philippine-specific EEIO models is needed for accuracy.
    /** Placeholder emission factor for clothing. Unit: kg CO2e/PHP */
    public static final double EF_GOODS_CLOTHING_KGCO2E_PER_PHP_PLACEHOLDER = 0.006; // Example proxy value
    /** Placeholder emission factor for electronics. Unit: kg CO2e/PHP */
    public static final double EF_GOODS_ELECTRONICS_KGCO2E_PER_PHP_PLACEHOLDER = 0.008; // Example proxy value

    // === VII. OFFSETTING / IMPACT VISUALIZATION ===
    /**
     * Approximate kg CO2 absorbed by a mature tree annually.
     * This is an average and can vary significantly based on tree type, age, climate, etc.
     * Using a common estimate of 22 kg CO2 per tree per year (50 lbs / 2.2 lbs/kg).
     */
    public static final double EF_TREES_CO2_ABSORPTION_KG_PER_YEAR = 22.0;


    public static final String APP_TITLE = "Carbon Footprint Calculator PH";

    private AppConstants() {}
}