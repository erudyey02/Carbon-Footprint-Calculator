// File: org/letrancpe/carboncalculator/model/UserData.java
package org.letrancpe.carboncalculator.model;

// Consider importing List and ArrayList if a list structure is used for appliance breakdown.
// import java.util.ArrayList;
// import java.util.List;

/**
 * UserData (Model) encapsulates all data input by the user.
 * This class will expand as more input fields are defined for different calculation categories.
 */
public class UserData {
    // === I. HOUSING ===
    private String dwellingType; // Example: Apartment/Condo, House. Primarily for recommendations.
    private double floorArea; // Unit: square meters. For recommendations or potential future detailed calculations.
    private String energyEfficiencyRating; // Optional, free-text. Primarily for recommendations.

    // === II. ENERGY CONSUMPTION ===
    // --- Electricity ---
    private String electricityGridRegion; // Example: "Luzon-Visayas", "Mindanao", "National Average/Unknown".
    private double monthlyElectricityKWh; // Overall monthly electricity consumption.
    // TODO: Consider List<ApplianceUsageEntry> for detailed appliance breakdown if that feature is implemented.

    // --- Cooking Fuels ---
    private String primaryCookingFuelType; // Example: "LPG", "Kerosene", "Charcoal", "Wood", "Electricity".
    private double cookingFuelMonthlyUsage; // Unit depends on fuel type (e.g., kg for LPG/Charcoal/Wood, L for Kerosene).
    private boolean isWoodSustainablySourced; // Boolean flag for wood fuel calculation.

    // --- Water ---
    private double dailyHouseholdWaterUsageLiters; // Total household water usage in Liters per day.

    // === III. DIET ===
    // (Fields for diet-related inputs will be added here based on research and input page design).
    // Example: private String redMeatFrequency;

    // === IV. TRANSPORTATION ===
    // (Fields for transportation-related inputs will be added here).

    // === V. WASTE MANAGEMENT ===
    // (Fields for waste management inputs will be added here).

    // === VI. GOODS CONSUMPTION ===
    // (Fields for goods consumption inputs will be added here).


    // --- Getters and Setters ---

    // Housing
    public String getDwellingType() { return dwellingType; }
    public void setDwellingType(String dwellingType) { this.dwellingType = dwellingType; }

    public double getFloorArea() { return floorArea; }
    public void setFloorArea(double floorArea) { this.floorArea = floorArea; }

    public String getEnergyEfficiencyRating() { return energyEfficiencyRating; }
    public void setEnergyEfficiencyRating(String energyEfficiencyRating) { this.energyEfficiencyRating = energyEfficiencyRating; }

    // Energy Consumption
    public String getElectricityGridRegion() { return electricityGridRegion; }
    public void setElectricityGridRegion(String electricityGridRegion) { this.electricityGridRegion = electricityGridRegion; }

    public double getMonthlyElectricityKWh() { return monthlyElectricityKWh; }
    public void setMonthlyElectricityKWh(double monthlyElectricityKWh) { this.monthlyElectricityKWh = monthlyElectricityKWh; }

    public String getPrimaryCookingFuelType() { return primaryCookingFuelType; }
    public void setPrimaryCookingFuelType(String primaryCookingFuelType) { this.primaryCookingFuelType = primaryCookingFuelType; }

    public double getCookingFuelMonthlyUsage() { return cookingFuelMonthlyUsage; }
    public void setCookingFuelMonthlyUsage(double cookingFuelMonthlyUsage) { this.cookingFuelMonthlyUsage = cookingFuelMonthlyUsage; }

    public boolean isWoodSustainablySourced() { return isWoodSustainablySourced; }
    public void setWoodSustainablySourced(boolean woodSustainablySourced) { this.isWoodSustainablySourced = woodSustainablySourced; }

    public double getDailyHouseholdWaterUsageLiters() { return dailyHouseholdWaterUsageLiters; }
    public void setDailyHouseholdWaterUsageLiters(double dailyHouseholdWaterUsageLiters) { this.dailyHouseholdWaterUsageLiters = dailyHouseholdWaterUsageLiters; }

    // TODO: Add getters and setters for Diet, Transportation, Waste, and Goods fields as they are defined.
}
