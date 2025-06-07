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

        double goodsFootprint = calculateGoodsFootprint(userData);
        footprintByCategory.put("Goods Consumption", goodsFootprint);
        totalAnnualFootprintKgCO2e += goodsFootprint;

        System.out.println("Total Annual Footprint (kg CO2e): " + totalAnnualFootprintKgCO2e);
        System.out.println("Breakdown by Category: " + footprintByCategory);
        return new CalculatedFootprintData(totalAnnualFootprintKgCO2e, footprintByCategory);
    }

    private double calculateHousingAndEnergyFootprint(UserData userData) {
        double categoryFootprint = 0;
        if (userData.getMonthlyElectricityKWh() > 0) {
            double annualKWh = userData.getMonthlyElectricityKWh() * 12;
            double gridFactor; String region = userData.getElectricityGridRegion();
            if ("Luzon-Visayas".equalsIgnoreCase(region)) gridFactor = AppConstants.EF_GRID_ELECTRICITY_LUZON_VISAYAS_KGCO2_PER_KWH;
            else if ("Mindanao".equalsIgnoreCase(region)) gridFactor = AppConstants.EF_GRID_ELECTRICITY_MINDANAO_KGCO2_PER_KWH;
            else gridFactor = AppConstants.EF_GRID_ELECTRICITY_NATIONAL_PH_KGCO2E_PER_KWH_PLACEHOLDER;
            categoryFootprint += annualKWh * gridFactor;
        }
        if (userData.getPrimaryCookingFuelType() != null && !userData.getPrimaryCookingFuelType().isEmpty() && !"None".equalsIgnoreCase(userData.getPrimaryCookingFuelType()) && userData.getCookingFuelMonthlyUsage() > 0) {
            String fuelType = userData.getPrimaryCookingFuelType(); double annualUsage = userData.getCookingFuelMonthlyUsage() * 12; double cookingFuelEmission = 0;
            if ("LPG".equalsIgnoreCase(fuelType)) cookingFuelEmission = annualUsage * AppConstants.EF_COOKING_LPG_KGCO2E_PER_KG;
            else if ("Kerosene".equalsIgnoreCase(fuelType)) cookingFuelEmission = annualUsage * AppConstants.EF_COOKING_KEROSENE_KGCO2E_PER_L;
            else if ("Charcoal".equalsIgnoreCase(fuelType)) cookingFuelEmission = annualUsage * AppConstants.EF_COOKING_CHARCOAL_KGCO2E_PER_KG;
            else if ("Wood".equalsIgnoreCase(fuelType)) {
                if (userData.isWoodSustainablySourced()) cookingFuelEmission = annualUsage * AppConstants.EF_COOKING_WOOD_SUSTAINABLE_KGCO2E_PER_KG;
                else cookingFuelEmission = annualUsage * AppConstants.EF_COOKING_WOOD_NON_SUSTAINABLE_KGCO2E_PER_KG;
            } categoryFootprint += cookingFuelEmission;
        }
        if (userData.getDailyHouseholdWaterUsageLiters() > 0) {
            double annualWaterUsageM3 = (userData.getDailyHouseholdWaterUsageLiters() * 365) / 1000.0;
            categoryFootprint += annualWaterUsageM3 * AppConstants.EF_WATER_MANILA_KGCO2E_PER_M3;
        }
        return categoryFootprint;
    }

    private double calculateDietFootprint(UserData userData) {
        double categoryFootprint = 0; double annualServings;
        annualServings = getAnnualServingsFromFrequency(userData.getRedMeatFrequency());
        if (annualServings > 0) categoryFootprint += annualServings * AppConstants.AVG_SERVING_SIZE_MEAT_KG * AppConstants.EF_DIET_RED_MEAT_AVG_PH_KGCO2E_PER_KG;
        annualServings = getAnnualServingsFromFrequency(userData.getPoultryFrequency());
        if (annualServings > 0) categoryFootprint += annualServings * AppConstants.AVG_SERVING_SIZE_MEAT_KG * AppConstants.EF_DIET_POULTRY_CHICKEN_PH_LCA_KGCO2E_PER_KG_CW;
        annualServings = getAnnualServingsFromFrequency(userData.getFishSeafoodFrequency());
        if (annualServings > 0) categoryFootprint += annualServings * AppConstants.AVG_SERVING_SIZE_MEAT_KG * AppConstants.EF_DIET_FISH_AVG_GLOBAL_PROXY_KGCO2E_PER_KG_CAUTION;
        if (userData.getDailyRiceServings() > 0) {
            double annualRiceKg = userData.getDailyRiceServings() * AppConstants.RICE_SERVING_SIZE_KG * 365;
            double riceFactor = AppConstants.EF_DIET_RICE_IRRIGATED_PH_KGCO2E_PER_KG;
            if ("Rainfed".equalsIgnoreCase(userData.getRiceType())) riceFactor = AppConstants.EF_DIET_RICE_RAINFED_PH_KGCO2E_PER_KG;
            categoryFootprint += annualRiceKg * riceFactor;
        }
        if (userData.getWeeklyFoodWasteKg() > 0) {
            double annualFoodWasteKg = userData.getWeeklyFoodWasteKg() * 52;
            categoryFootprint += annualFoodWasteKg * AppConstants.EF_DIET_FOOD_WASTE_LANDFILL_MM_KGCO2E_PER_KG;
        }
        return categoryFootprint;
    }

    private int getAnnualServingsFromFrequency(String frequency) {
        if (frequency == null) return 0;
        return switch (frequency) {
            case "Daily" -> AppConstants.SERVINGS_PER_YEAR_DAILY;
            case "3-5 times/week" -> AppConstants.SERVINGS_PER_YEAR_FREQ;
            case "1-2 times/week" -> AppConstants.SERVINGS_PER_YEAR_OCCAS;
            case "Rarely/Never" -> AppConstants.SERVINGS_PER_YEAR_RARELY;
            default -> 0;
        };
    }

    private double calculateTransportationFootprint(UserData userData) {
        double categoryFootprint = 0;

        // 1. Regular Transport Entries (Car, Motorcycle, Public Transport)
        if (userData.getRegularTransportEntries() != null) {
            for (TransportEntry entry : userData.getRegularTransportEntries()) {
                // Correctly annualize distance based on frequency
                double annualDistanceKm = convertToAnnualDistance(entry.getDistanceKm(), entry.getFrequency());

                if (annualDistanceKm == 0) continue;

                String mode = entry.getMode();
                if ("Car".equalsIgnoreCase(mode)) {
                    double fuelEfficiency = entry.getFuelEfficiencyKmL();
                    if (fuelEfficiency > 0) {
                        double annualFuelLiters = annualDistanceKm / fuelEfficiency;
                        if ("Gasoline".equalsIgnoreCase(entry.getFuelType())) {
                            categoryFootprint += annualFuelLiters * AppConstants.EF_TRANSPORT_CAR_GASOLINE_COMBUSTION_KGCO2_PER_L;
                        } else if ("Diesel".equalsIgnoreCase(entry.getFuelType())) {
                            categoryFootprint += annualFuelLiters * AppConstants.EF_TRANSPORT_CAR_DIESEL_COMBUSTION_KGCO2_PER_L;
                        }
                    }
                } else if ("Motorcycle".equalsIgnoreCase(mode)) {
                    double fuelEfficiency = entry.getFuelEfficiencyKmL();
                    if (fuelEfficiency > 0 && "Gasoline".equalsIgnoreCase(entry.getFuelType())) {
                        double annualFuelLiters = annualDistanceKm / fuelEfficiency;
                        categoryFootprint += annualFuelLiters * AppConstants.EF_TRANSPORT_CAR_GASOLINE_COMBUSTION_KGCO2_PER_L;
                    } else {
                        categoryFootprint += annualDistanceKm * AppConstants.EF_TRANSPORT_MOTORCYCLE_DERIVED_KGCO2_PER_KM;
                    }
                } else if ("Tricycle".equalsIgnoreCase(mode)) {
                    categoryFootprint += annualDistanceKm * AppConstants.EF_TRANSPORT_TRICYCLE_PASSENGER_DERIVED_KGCO2_PER_PKM;
                } else if ("Jeepney".equalsIgnoreCase(mode)) {
                    categoryFootprint += annualDistanceKm * AppConstants.EF_TRANSPORT_JEEPNEY_PASSENGER_KGCO2E_PER_PKM_CAUTION;
                } else if ("Bus".equalsIgnoreCase(mode)) {
                    categoryFootprint += annualDistanceKm * AppConstants.EF_TRANSPORT_BUS_PASSENGER_KGCO2E_PER_PKM_CAUTION;
                } else if ("Train".equalsIgnoreCase(mode) || "MRT".equalsIgnoreCase(mode) || "LRT".equalsIgnoreCase(mode)) {
                    categoryFootprint += annualDistanceKm * AppConstants.EF_TRANSPORT_TRAIN_MANILA_ESTIMATE_KGCO2E_PER_PKM;
                }
            }
        }

        if (userData.getAnnualNonCommuteCarKm() > 0 && userData.getAnnualNonCommuteCarFuelEfficiencyKmL() > 0) {
            double annualFuelLiters = userData.getAnnualNonCommuteCarKm() / userData.getAnnualNonCommuteCarFuelEfficiencyKmL();
            if ("Gasoline".equalsIgnoreCase(userData.getAnnualNonCommuteCarFuelType())) {
                categoryFootprint += annualFuelLiters * AppConstants.EF_TRANSPORT_CAR_GASOLINE_COMBUSTION_KGCO2_PER_L;
            } else if ("Diesel".equalsIgnoreCase(userData.getAnnualNonCommuteCarFuelType())) {
                categoryFootprint += annualFuelLiters * AppConstants.EF_TRANSPORT_CAR_DIESEL_COMBUSTION_KGCO2_PER_L;
            }
        }

        if (userData.getFlightEntries() != null) {
            for (FlightEntry flight : userData.getFlightEntries()) {
                double totalPassengerKmForFlightType = flight.getTotalOneWayFlightsPerYear() * flight.getAverageDistancePerFlightKm();
                double flightFactor = 0;
                String haulTypeSimple = flight.getHaulType();

                if ("Short".equalsIgnoreCase(haulTypeSimple)) {
                    flightFactor = AppConstants.EF_TRANSPORT_FLIGHT_SHORT_HAUL_ECONOMY_RFI3_KGCO2E_PER_PKM;
                } else if ("Medium".equalsIgnoreCase(haulTypeSimple)) {
                    flightFactor = AppConstants.EF_TRANSPORT_FLIGHT_MEDIUM_HAUL_ECONOMY_RFI3_KGCO2E_PER_PKM;
                } else if ("Long".equalsIgnoreCase(haulTypeSimple)) {
                    flightFactor = AppConstants.EF_TRANSPORT_FLIGHT_LONG_HAUL_ECONOMY_RFI3_KGCO2E_PER_PKM;
                }

                if ("Business".equalsIgnoreCase(flight.getCabinClass())) {
                    flightFactor *= AppConstants.FLIGHT_BUSINESS_CLASS_MULTIPLIER;
                }
                categoryFootprint += totalPassengerKmForFlightType * flightFactor;
            }
        }

        System.out.println("Debug: Total Transportation Footprint: " + categoryFootprint + " kg CO2e");
        return categoryFootprint;
    }

    private double convertToAnnualDistance(double distance, String frequency) {
        if (frequency == null) return 0;
        return switch (frequency) {
            case "Per Day" -> distance * 365;
            case "Per Week" -> distance * 52;
            case "Per Month" -> distance * 12;
            case "Per Year (Total)" -> distance;
            default -> 0;
        };
    }

    private double calculateWasteFootprint(UserData userData) {
        double categoryFootprint = 0;
        if (userData.getWeeklyNonRecycledWasteKg() > 0) {
            double annualWasteKg = userData.getWeeklyNonRecycledWasteKg() * 52;
            categoryFootprint += annualWasteKg * AppConstants.EF_WASTE_MSW_LANDFILL_METRO_MANILA_KGCO2E_PER_KG;
        }
        return categoryFootprint;
    }

    private double calculateGoodsFootprint(UserData userData) {
        double categoryFootprint = 0;
        if (userData.getAnnualClothingSpendingPHP() > 0) {
            categoryFootprint += userData.getAnnualClothingSpendingPHP() * AppConstants.EF_GOODS_CLOTHING_KGCO2E_PER_PHP_PLACEHOLDER;
        }
        if (userData.getAnnualElectronicsSpendingPHP() > 0) {
            categoryFootprint += userData.getAnnualElectronicsSpendingPHP() * AppConstants.EF_GOODS_ELECTRONICS_KGCO2E_PER_PHP_PLACEHOLDER;
        }
        return categoryFootprint;
    }
}
