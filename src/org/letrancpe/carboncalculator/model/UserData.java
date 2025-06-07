// File: org/letrancpe/carboncalculator/model/UserData.java
package org.letrancpe.carboncalculator.model;

import org.letrancpe.carboncalculator.model.dto.FlightEntry;
import org.letrancpe.carboncalculator.model.dto.TransportEntry;
import java.util.ArrayList;
import java.util.List;

/**
 * UserData (Model) encapsulates all data input by the user.
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
    private String redMeatFrequency;
    private String poultryFrequency;
    private String fishSeafoodFrequency;
    private double dailyRiceServings;
    private String riceType;
    private String dietaryPattern;
    private double weeklyFoodWasteKg;
    private String localFoodSourcingFrequency;

    // === IV. TRANSPORTATION ===
    private List<TransportEntry> regularTransportEntries;
    private List<FlightEntry> flightEntries;
    private double annualNonCommuteCarKm;
    private String annualNonCommuteCarFuelType;
    private double annualNonCommuteCarFuelEfficiencyKmL;

    // === V. WASTE MANAGEMENT ===
    private double weeklyNonRecycledWasteKg; // Estimated amount per week
    private double recyclingRatePercentage; // Approximate percentage of recyclable waste that is actually recycled

    // === VI. GOODS CONSUMPTION ===
    // (Fields to be added)

    public UserData() {
        this.regularTransportEntries = new ArrayList<>();
        this.flightEntries = new ArrayList<>();
    }

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

    // Transportation
    public List<TransportEntry> getRegularTransportEntries() { return regularTransportEntries; }
    public void setRegularTransportEntries(List<TransportEntry> regularTransportEntries) { this.regularTransportEntries = regularTransportEntries; }
    public List<FlightEntry> getFlightEntries() { return flightEntries; }
    public void setFlightEntries(List<FlightEntry> flightEntries) { this.flightEntries = flightEntries; }
    public double getAnnualNonCommuteCarKm() { return annualNonCommuteCarKm; }
    public void setAnnualNonCommuteCarKm(double annualNonCommuteCarKm) { this.annualNonCommuteCarKm = annualNonCommuteCarKm; }
    public String getAnnualNonCommuteCarFuelType() { return annualNonCommuteCarFuelType; }
    public void setAnnualNonCommuteCarFuelType(String annualNonCommuteCarFuelType) { this.annualNonCommuteCarFuelType = annualNonCommuteCarFuelType; }
    public double getAnnualNonCommuteCarFuelEfficiencyKmL() { return annualNonCommuteCarFuelEfficiencyKmL; }
    public void setAnnualNonCommuteCarFuelEfficiencyKmL(double annualNonCommuteCarFuelEfficiencyKmL) { this.annualNonCommuteCarFuelEfficiencyKmL = annualNonCommuteCarFuelEfficiencyKmL; }

    // Waste Management
    public double getWeeklyNonRecycledWasteKg() { return weeklyNonRecycledWasteKg; }
    public void setWeeklyNonRecycledWasteKg(double weeklyNonRecycledWasteKg) { this.weeklyNonRecycledWasteKg = weeklyNonRecycledWasteKg; }
    public double getRecyclingRatePercentage() { return recyclingRatePercentage; }
    public void setRecyclingRatePercentage(double recyclingRatePercentage) { this.recyclingRatePercentage = recyclingRatePercentage; }

    // TODO: Add getters and setters for Goods fields.
}
