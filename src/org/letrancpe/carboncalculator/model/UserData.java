// File: org/letrancpe/carboncalculator/model/UserData.java
package org.letrancpe.carboncalculator.model;

/**
 * UserData (Model) encapsulates all data input by the user.
 * This class will expand as more input fields are defined for different calculation categories.
 */
public class UserData {
    // === I. HOUSING ===
    private String dwellingType;
    private double floorArea;
    private String energyEfficiencyRating;

    // === II. ENERGY CONSUMPTION ===
    private String electricityGridRegion;
    private double monthlyElectricityKWh;

    private String primaryCookingFuelType;
    private double cookingFuelMonthlyUsage;
    private boolean isWoodSustainablySourced;

    private double dailyHouseholdWaterUsageLiters;

    // === III. DIET ===
    private String redMeatFrequency; // E.g., "Daily", "3-5 times/week", "1-2 times/week", "Rarely/Never"
    private String poultryFrequency; // Same options as redMeatFrequency
    private String fishSeafoodFrequency; // Same options as redMeatFrequency
    private double dailyRiceServings; // Number of servings per day
    private String riceType; // E.g., "Irrigated", "Rainfed", "Unknown/Average"
    private String dietaryPattern; // E.g., "Meat-eater (Typical)", "Reduced Meat / Flexitarian", "Vegetarian", "Vegan"
    private double weeklyFoodWasteKg; // Kilograms of food waste per week
    private String localFoodSourcingFrequency; // Optional: "Often", "Sometimes", "Rarely" - for recommendations

    // === IV. TRANSPORTATION ===
    // (Fields to be added)

    // === V. WASTE MANAGEMENT ===
    // (Fields to be added)

    // === VI. GOODS CONSUMPTION ===
    // (Fields to be added)


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

    // Diet
    public String getRedMeatFrequency() { return redMeatFrequency; }
    public void setRedMeatFrequency(String redMeatFrequency) { this.redMeatFrequency = redMeatFrequency; }
    public String getPoultryFrequency() { return poultryFrequency; }
    public void setPoultryFrequency(String poultryFrequency) { this.poultryFrequency = poultryFrequency; }
    public String getFishSeafoodFrequency() { return fishSeafoodFrequency; }
    public void setFishSeafoodFrequency(String fishSeafoodFrequency) { this.fishSeafoodFrequency = fishSeafoodFrequency; }
    public double getDailyRiceServings() { return dailyRiceServings; }
    public void setDailyRiceServings(double dailyRiceServings) { this.dailyRiceServings = dailyRiceServings; }
    public String getRiceType() { return riceType; }
    public void setRiceType(String riceType) { this.riceType = riceType; }
    public String getDietaryPattern() { return dietaryPattern; }
    public void setDietaryPattern(String dietaryPattern) { this.dietaryPattern = dietaryPattern; }
    public double getWeeklyFoodWasteKg() { return weeklyFoodWasteKg; }
    public void setWeeklyFoodWasteKg(double weeklyFoodWasteKg) { this.weeklyFoodWasteKg = weeklyFoodWasteKg; }
    public String getLocalFoodSourcingFrequency() { return localFoodSourcingFrequency; }
    public void setLocalFoodSourcingFrequency(String localFoodSourcingFrequency) { this.localFoodSourcingFrequency = localFoodSourcingFrequency; }

    // TODO: Add getters and setters for Transportation, Waste, and Goods fields.
}
