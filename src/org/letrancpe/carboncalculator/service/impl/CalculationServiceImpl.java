// File: org/letrancpe/carboncalculator/service/impl/CalculationServiceImpl.java
package org.letrancpe.carboncalculator.service.impl;

import org.letrancpe.carboncalculator.model.AppConstants;
import org.letrancpe.carboncalculator.model.UserData;
import org.letrancpe.carboncalculator.model.dto.CalculatedFootprintData;
import org.letrancpe.carboncalculator.model.dto.FlightEntry;
import org.letrancpe.carboncalculator.model.dto.TransportEntry;
import org.letrancpe.carboncalculator.service.CalculationService;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of CalculationService.
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

        double dietFootprint = calculateDietFootprint(userData);
        footprintByCategory.put("Diet", dietFootprint);
        totalAnnualFootprintKgCO2e += dietFootprint;

        double transportFootprint = calculateTransportationFootprint(userData);
        footprintByCategory.put("Transport", transportFootprint);
        totalAnnualFootprintKgCO2e += transportFootprint;

        double wasteFootprint = calculateWasteFootprint(userData);
        footprintByCategory.put("Waste Management", wasteFootprint);
        totalAnnualFootprintKgCO2e += wasteFootprint;

        double goodsFootprint = calculateGoodsFootprint(userData); // Call new goods calculation
        footprintByCategory.put("Goods Consumption", goodsFootprint);
        totalAnnualFootprintKgCO2e += goodsFootprint;

        System.out.println("Total Annual Footprint (kg CO2e): " + totalAnnualFootprintKgCO2e);
        System.out.println("Breakdown by Category: " + footprintByCategory);
        return new CalculatedFootprintData(totalAnnualFootprintKgCO2e, footprintByCategory);
    }

    private double calculateHousingAndEnergyFootprint(UserData userData) {
        // This is a placeholder for the full method implementation
        return 0.0;
    }

    private double calculateDietFootprint(UserData userData) {
        // This is a placeholder for the full method implementation
        return 0.0;
    }

    private double calculateTransportationFootprint(UserData userData) {
        // This is a placeholder for the full method implementation
        return 0.0;
    }

    private double calculateWasteFootprint(UserData userData) {
        double categoryFootprint = 0;
        if (userData.getWeeklyNonRecycledWasteKg() > 0) {
            double annualWasteKg = userData.getWeeklyNonRecycledWasteKg() * 52;
            // The recyclingRatePercentage is not used to adjust this calculation directly, as the input
            // is for *non-recycled* waste. It is primarily for recommendations.
            categoryFootprint += annualWasteKg * AppConstants.EF_WASTE_MSW_LANDFILL_METRO_MANILA_KGCO2E_PER_KG;
        }
        System.out.println("Debug: Total Waste Management Footprint: " + categoryFootprint + " kg CO2e");
        return categoryFootprint;
    }

    private double calculateGoodsFootprint(UserData userData) {
        double categoryFootprint = 0;

        // 1. Clothing Consumption
        if (userData.getAnnualClothingSpendingPHP() > 0) {
            categoryFootprint += userData.getAnnualClothingSpendingPHP() * AppConstants.EF_GOODS_CLOTHING_KGCO2E_PER_PHP_PLACEHOLDER;
        }

        // 2. Electronics Consumption
        if (userData.getAnnualElectronicsSpendingPHP() > 0) {
            categoryFootprint += userData.getAnnualElectronicsSpendingPHP() * AppConstants.EF_GOODS_ELECTRONICS_KGCO2E_PER_PHP_PLACEHOLDER;
        }

        System.out.println("Debug: Total Goods Consumption Footprint: " + categoryFootprint + " kg CO2e");
        return categoryFootprint;
    }
}
