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
        System.out.println("Calculating footprint..."); // Log: General calculation start.
        double totalAnnualFootprintKgCO2e = 0;
        Map<String, Double> footprintByCategory = new HashMap<>();

        // Calculate footprint for each category by calling dedicated methods.
        double housingAndEnergyFootprint = calculateHousingAndEnergyFootprint(userData);
        footprintByCategory.put("Housing & Energy", housingAndEnergyFootprint);
        totalAnnualFootprintKgCO2e += housingAndEnergyFootprint;

        // TODO: Implement and call calculation methods for other categories.
        // double dietFootprint = calculateDietFootprint(userData);
        // footprintByCategory.put("Diet", dietFootprint);
        // totalAnnualFootprintKgCO2e += dietFootprint;
        // ... and so on for Transport, Waste, Goods.

        // Add placeholders for other categories until their calculations are implemented.
        footprintByCategory.putIfAbsent("Diet", 0.0);
        footprintByCategory.putIfAbsent("Transport", 0.0);
        footprintByCategory.putIfAbsent("Waste Management", 0.0);
        footprintByCategory.putIfAbsent("Goods Consumption", 0.0);


        System.out.println("Total Annual Footprint (kg CO2e): " + totalAnnualFootprintKgCO2e);
        System.out.println("Breakdown by Category: " + footprintByCategory);
        return new CalculatedFootprintData(totalAnnualFootprintKgCO2e, footprintByCategory);
    }

    /**
     * Calculates the carbon footprint from housing operational energy (electricity, cooking fuels)
     * and water consumption.
     * @param userData The user's input data.
     * @return The calculated footprint in kg CO2e for this category.
     */
    private double calculateHousingAndEnergyFootprint(UserData userData) {
        double categoryFootprint = 0;

        // 1. Electricity Consumption
        if (userData.getMonthlyElectricityKWh() > 0) {
            double annualKWh = userData.getMonthlyElectricityKWh() * 12;
            double gridFactor;
            String region = userData.getElectricityGridRegion();

            // Select appropriate grid factor. Default to national placeholder if region is unknown or not specified.
            // Note: Current regional factors are CO2 only. This should be highlighted in HelpPage or updated to CO2e.
            if ("Luzon-Visayas".equalsIgnoreCase(region)) {
                gridFactor = AppConstants.EF_GRID_ELECTRICITY_LUZON_VISAYAS_KGCO2_PER_KWH;
            } else if ("Mindanao".equalsIgnoreCase(region)) {
                gridFactor = AppConstants.EF_GRID_ELECTRICITY_MINDANAO_KGCO2_PER_KWH;
            } else {
                // Default or if user selects "National Average/Unknown"
                gridFactor = AppConstants.EF_GRID_ELECTRICITY_NATIONAL_PH_KGCO2E_PER_KWH_PLACEHOLDER;
                System.out.println("Calculation Warning: Using placeholder national average grid factor for electricity.");
            }
            categoryFootprint += annualKWh * gridFactor;
            System.out.println("Debug: Electricity footprint contribution: " + (annualKWh * gridFactor) + " kg CO2(e)");
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
            } else if ("Electricity".equalsIgnoreCase(fuelType)) {
                // Electricity for cooking is assumed to be part of the main monthlyElectricityKWh.
                // No separate calculation here unless specific appliance data for cooking is added and handled.
                System.out.println("Calculation Info: Electricity for cooking is included in overall electricity usage calculation.");
            }
            categoryFootprint += cookingFuelEmission;
            System.out.println("Debug: Cooking fuel (" + fuelType + ") footprint contribution: " + cookingFuelEmission + " kg CO2e");
        }

        // 3. Water Consumption
        if (userData.getDailyHouseholdWaterUsageLiters() > 0) {
            double annualWaterUsageM3 = (userData.getDailyHouseholdWaterUsageLiters() * 365) / 1000.0; // Convert Liters/day to cubic meters/year
            // Using Manila Water factor. This needs refinement for broader applicability or user input for water source.
            double waterEmission = annualWaterUsageM3 * AppConstants.EF_WATER_MANILA_KGCO2E_PER_M3;
            categoryFootprint += waterEmission;
            System.out.println("Debug: Water consumption footprint contribution: " + waterEmission + " kg CO2e");
        }

        System.out.println("Debug: Total Housing & Energy Footprint: " + categoryFootprint + " kg CO2e");
        return categoryFootprint;
    }

    // TODO: Implement calculateDietFootprint(UserData userData)
    // TODO: Implement calculateTransportationFootprint(UserData userData)
    // TODO: Implement calculateWasteFootprint(UserData userData)
    // TODO: Implement calculateGoodsFootprint(UserData userData)
}
