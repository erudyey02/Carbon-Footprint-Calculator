// File: org/letrancpe/carboncalculator/service/impl/CalculationServiceImpl.java
package org.letrancpe.carboncalculator.service.impl;

import org.letrancpe.carboncalculator.model.AppConstants;
import org.letrancpe.carboncalculator.model.UserData;
import org.letrancpe.carboncalculator.model.dto.CalculatedFootprintData;
import org.letrancpe.carboncalculator.service.CalculationService;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of CalculationService.
 * This class houses the logic to calculate the carbon footprint based on user-provided data
 * and emission factors defined in AppConstants.
 */
public class CalculationServiceImpl implements CalculationService {

    @Override
    public CalculatedFootprintData calculateFootprint(UserData userData) {
        System.out.println("Calculating footprint...");
        double totalAnnualFootprintKgCO2e = 0;
        Map<String, Double> footprintByCategory = new HashMap<>();

        double housingAndEnergyFootprint = calculateHousingAndEnergyFootprint(userData);
        footprintByCategory.put("Housing & Energy", housingAndEnergyFootprint);
        totalAnnualFootprintKgCO2e += housingAndEnergyFootprint;

        double dietFootprint = calculateDietFootprint(userData); // Call new diet calculation method
        footprintByCategory.put("Diet", dietFootprint);
        totalAnnualFootprintKgCO2e += dietFootprint;

        // TODO: Implement and call calculation methods for other categories.
        footprintByCategory.putIfAbsent("Transport", 0.0);
        footprintByCategory.putIfAbsent("Waste Management", 0.0);
        footprintByCategory.putIfAbsent("Goods Consumption", 0.0);


        System.out.println("Total Annual Footprint (kg CO2e): " + totalAnnualFootprintKgCO2e);
        System.out.println("Breakdown by Category: " + footprintByCategory);
        return new CalculatedFootprintData(totalAnnualFootprintKgCO2e, footprintByCategory);
    }

    private double calculateHousingAndEnergyFootprint(UserData userData) {
        double categoryFootprint = 0;

        // 1. Electricity Consumption
        if (userData.getMonthlyElectricityKWh() > 0) {
            double annualKWh = userData.getMonthlyElectricityKWh() * 12;
            double gridFactor;
            String region = userData.getElectricityGridRegion();
            if ("Luzon-Visayas".equalsIgnoreCase(region)) {
                gridFactor = AppConstants.EF_GRID_ELECTRICITY_LUZON_VISAYAS_KGCO2_PER_KWH;
            } else if ("Mindanao".equalsIgnoreCase(region)) {
                gridFactor = AppConstants.EF_GRID_ELECTRICITY_MINDANAO_KGCO2_PER_KWH;
            } else {
                gridFactor = AppConstants.EF_GRID_ELECTRICITY_NATIONAL_PH_KGCO2E_PER_KWH_PLACEHOLDER;
                System.out.println("Calculation Warning: Using placeholder national average grid factor for electricity.");
            }
            categoryFootprint += annualKWh * gridFactor;
        }

        // 2. Cooking Fuel Consumption
        if (userData.getPrimaryCookingFuelType() != null && !userData.getPrimaryCookingFuelType().isEmpty() && !"None".equalsIgnoreCase(userData.getPrimaryCookingFuelType()) && userData.getCookingFuelMonthlyUsage() > 0) {
            String fuelType = userData.getPrimaryCookingFuelType();
            double annualUsage = userData.getCookingFuelMonthlyUsage() * 12;
            double cookingFuelEmission = 0;
            if ("LPG".equalsIgnoreCase(fuelType)) {
                cookingFuelEmission = annualUsage * AppConstants.EF_COOKING_LPG_KGCO2E_PER_KG;
            } else if ("Kerosene".equalsIgnoreCase(fuelType)) {
                cookingFuelEmission = annualUsage * AppConstants.EF_COOKING_KEROSENE_KGCO2E_PER_L;
            } else if ("Charcoal".equalsIgnoreCase(fuelType)) {
                cookingFuelEmission = annualUsage * AppConstants.EF_COOKING_CHARCOAL_KGCO2E_PER_KG;
            } else if ("Wood".equalsIgnoreCase(fuelType)) {
                if (userData.isWoodSustainablySourced()) {
                    cookingFuelEmission = annualUsage * AppConstants.EF_COOKING_WOOD_SUSTAINABLE_KGCO2E_PER_KG;
                } else {
                    cookingFuelEmission = annualUsage * AppConstants.EF_COOKING_WOOD_NON_SUSTAINABLE_KGCO2E_PER_KG;
                }
            }
            categoryFootprint += cookingFuelEmission;
        }

        // 3. Water Consumption
        if (userData.getDailyHouseholdWaterUsageLiters() > 0) {
            double annualWaterUsageM3 = (userData.getDailyHouseholdWaterUsageLiters() * 365) / 1000.0;
            double waterEmission = annualWaterUsageM3 * AppConstants.EF_WATER_MANILA_KGCO2E_PER_M3;
            categoryFootprint += waterEmission;
        }

        return categoryFootprint;
    }

    private double calculateDietFootprint(UserData userData) {
        double categoryFootprint = 0;
        double annualServings;

        // 1. Red Meat
        annualServings = getAnnualServingsFromFrequency(userData.getRedMeatFrequency());
        if (annualServings > 0) {
            categoryFootprint += annualServings * AppConstants.AVG_SERVING_SIZE_MEAT_KG * AppConstants.EF_DIET_RED_MEAT_AVG_PH_KGCO2E_PER_KG;
        }

        // 2. Poultry
        annualServings = getAnnualServingsFromFrequency(userData.getPoultryFrequency());
        if (annualServings > 0) {
            categoryFootprint += annualServings * AppConstants.AVG_SERVING_SIZE_MEAT_KG * AppConstants.EF_DIET_POULTRY_CHICKEN_PH_LCA_KGCO2E_PER_KG_CW;
        }

        // 3. Fish/Seafood
        annualServings = getAnnualServingsFromFrequency(userData.getFishSeafoodFrequency());
        if (annualServings > 0) {
            // Using placeholder average fish factor with caution
            categoryFootprint += annualServings * AppConstants.AVG_SERVING_SIZE_MEAT_KG * AppConstants.EF_DIET_FISH_AVG_GLOBAL_PROXY_KGCO2E_PER_KG_CAUTION;
        }

        // 4. Rice
        if (userData.getDailyRiceServings() > 0) {
            double annualRiceKg = userData.getDailyRiceServings() * AppConstants.RICE_SERVING_SIZE_KG * 365;
            double riceFactor = AppConstants.EF_DIET_RICE_IRRIGATED_PH_KGCO2E_PER_KG; // Default to irrigated
            if ("Rainfed".equalsIgnoreCase(userData.getRiceType())) {
                riceFactor = AppConstants.EF_DIET_RICE_RAINFED_PH_KGCO2E_PER_KG;
            }
            // TODO: Add UI option for user to specify rice type or use a weighted average if "Unknown/Average"
            categoryFootprint += annualRiceKg * riceFactor;
        }

        // 5. Food Waste
        if (userData.getWeeklyFoodWasteKg() > 0) {
            double annualFoodWasteKg = userData.getWeeklyFoodWasteKg() * 52;
            categoryFootprint += annualFoodWasteKg * AppConstants.EF_DIET_FOOD_WASTE_LANDFILL_MM_KGCO2E_PER_KG;
        }

        // Note: Dietary pattern (Vegan, Vegetarian) currently influences recommendations.
        // For calculation, if "Vegan" or "Vegetarian", meat/poultry/fish inputs would ideally be 0 or disabled in UI.
        // The "plant-based staples" factor is available but estimating its quantity is complex without more inputs.
        // For now, the diet footprint is primarily from meat, fish, rice, and food waste as per direct inputs.

        System.out.println("Debug: Total Diet Footprint: " + categoryFootprint + " kg CO2e");
        return categoryFootprint;
    }

    private int getAnnualServingsFromFrequency(String frequency) {
        if (frequency == null) return 0;
        switch (frequency) {
            case "Daily": return AppConstants.SERVINGS_PER_YEAR_DAILY;
            case "3-5 times/week": return AppConstants.SERVINGS_PER_YEAR_FREQ;
            case "1-2 times/week": return AppConstants.SERVINGS_PER_YEAR_OCCAS;
            case "Rarely/Never": return AppConstants.SERVINGS_PER_YEAR_RARELY; // Or 0 for "Never"
            default: return 0;
        }
    }

    // TODO: Implement calculateTransportationFootprint(UserData userData)
    // TODO: Implement calculateWasteFootprint(UserData userData)
    // TODO: Implement calculateGoodsFootprint(UserData userData)
}
