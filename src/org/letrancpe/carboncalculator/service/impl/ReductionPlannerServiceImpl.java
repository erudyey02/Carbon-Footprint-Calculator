// File: org/letrancpe/carboncalculator/service/impl/ReductionPlannerServiceImpl.java
package org.letrancpe.carboncalculator.service.impl;

import org.letrancpe.carboncalculator.model.UserData;
import org.letrancpe.carboncalculator.model.dto.CalculatedFootprintData;
import org.letrancpe.carboncalculator.model.dto.Recommendation;
import org.letrancpe.carboncalculator.model.dto.TransportEntry;
import org.letrancpe.carboncalculator.service.ReductionPlannerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of ReductionPlannerService.
 */
public class ReductionPlannerServiceImpl implements ReductionPlannerService {

    @Override
    public List<Recommendation> generateRecommendations(UserData userData, CalculatedFootprintData footprintData) {
        System.out.println("Generating recommendations...");
        List<Recommendation> recommendations = new ArrayList<>();

        recommendations.add(new Recommendation("General", "Regularly review energy bills to track consumption patterns and identify areas for savings."));
        recommendations.add(new Recommendation("General", "Consider supporting businesses and products that demonstrate strong sustainability practices."));

        if (footprintData != null && footprintData.getFootprintByCategory() != null) {
            Map<String, Double> breakdown = footprintData.getFootprintByCategory();

            generateHousingAndEnergyRecommendations(userData, breakdown.getOrDefault("Housing & Energy", 0.0), recommendations);
            generateDietRecommendations(userData, breakdown.getOrDefault("Diet", 0.0), recommendations);
            generateTransportationRecommendations(userData, breakdown.getOrDefault("Transport", 0.0), recommendations);
            generateWasteRecommendations(userData, breakdown.getOrDefault("Waste Management", 0.0), recommendations);
            generateGoodsRecommendations(userData, breakdown.getOrDefault("Goods Consumption", 0.0), recommendations);
        }

        if (recommendations.isEmpty()) {
            recommendations.add(new Recommendation("General", "Explore various ways to reduce overall consumption and adopt more sustainable habits in daily life!"));
        }

        return recommendations;
    }

    private void generateHousingAndEnergyRecommendations(UserData userData, double housingEnergyFootprint, List<Recommendation> recommendations) {
        if (userData.getMonthlyElectricityKWh() > 200) recommendations.add(new Recommendation("Energy", "High electricity usage noted. Consider switching to LED lighting, unplugging unused appliances, or investing in newer, energy-efficient models."));
        else if (userData.getMonthlyElectricityKWh() > 50) recommendations.add(new Recommendation("Energy", "Look for opportunities to reduce electricity use, such as optimizing appliance settings, using natural light more often, or ensuring devices are turned off when not in use."));
        String cookingFuel = userData.getPrimaryCookingFuelType();
        if ("Charcoal".equalsIgnoreCase(cookingFuel) || "Wood".equalsIgnoreCase(cookingFuel)) {
            recommendations.add(new Recommendation("Energy", "Using charcoal or wood for cooking can result in higher emissions. If feasible, explore cleaner alternatives like LPG, electric induction, or improved efficiency cookstoves."));
            if ("Wood".equalsIgnoreCase(cookingFuel) && !userData.isWoodSustainablySourced()) recommendations.add(new Recommendation("Energy", "If using wood for cooking, attempt to source it from sustainable suppliers to lessen its environmental impact."));
        }
        if ("Apartment / Condominium".equalsIgnoreCase(userData.getDwellingType())) recommendations.add(new Recommendation("Housing", "For apartment/condo residents: Discuss potential energy-saving initiatives with building management or the homeowners' association."));
        String efficiencyInfo = userData.getEnergyEfficiencyRating();
        if (efficiencyInfo != null && !efficiencyInfo.isEmpty()) {
            if (!efficiencyInfo.toLowerCase().contains("insulated") && !efficiencyInfo.toLowerCase().contains("berde")) recommendations.add(new Recommendation("Housing", "Consider improving home insulation (e.g., for roof or walls) if not already implemented, to help reduce heating/cooling energy needs."));
            if (efficiencyInfo.toLowerCase().contains("old ac") || efficiencyInfo.toLowerCase().contains("old aircon")) recommendations.add(new Recommendation("Housing", "If using older air conditioning units, consider upgrading to newer, more energy-efficient models when replacement is feasible."));
        }
        if (userData.getDailyHouseholdWaterUsageLiters() > 500) recommendations.add(new Recommendation("Water", "High household water usage noted. Check for leaks, consider installing water-efficient fixtures (taps, showerheads), and practice water-saving habits."));
    }

    private void generateDietRecommendations(UserData userData, double dietFootprint, List<Recommendation> recommendations) {
        if ("Daily".equalsIgnoreCase(userData.getRedMeatFrequency()) || "3-5 times/week".equalsIgnoreCase(userData.getRedMeatFrequency())) recommendations.add(new Recommendation("Diet", "Consider reducing red meat consumption. Even a small reduction can significantly lower dietary carbon footprint. Explore plant-based proteins or poultry/fish as alternatives."));
        if ("Meat-eater (Typical)".equalsIgnoreCase(userData.getDietaryPattern()) || "Reduced Meat / Flexitarian".equalsIgnoreCase(userData.getDietaryPattern())) recommendations.add(new Recommendation("Diet", "Explore incorporating more plant-based meals into the weekly routine. Many delicious and nutritious vegetarian or vegan options are available."));
        else if ("Vegetarian".equalsIgnoreCase(userData.getDietaryPattern())) recommendations.add(new Recommendation("Diet", "As a vegetarian, continue to explore diverse plant-based protein sources. Ensure a balanced diet for all necessary nutrients."));
        else if ("Vegan".equalsIgnoreCase(userData.getDietaryPattern())) recommendations.add(new Recommendation("Diet", "As a vegan, the diet already has a significantly lower carbon footprint. Focus on sourcing food locally and minimizing waste."));
        if (userData.getWeeklyFoodWasteKg() > 1) recommendations.add(new Recommendation("Diet", "Reduce food waste by planning meals, storing food properly, using leftovers, and composting if possible."));
        if ("Rarely".equalsIgnoreCase(userData.getLocalFoodSourcingFrequency()) || userData.getLocalFoodSourcingFrequency() == null || userData.getLocalFoodSourcingFrequency().isEmpty()) recommendations.add(new Recommendation("Diet", "Try to choose locally sourced foods when available. This reduces transportation emissions and supports local producers."));
    }

    private void generateTransportationRecommendations(UserData userData, double transportFootprint, List<Recommendation> recommendations) {
        boolean usesCar = false; boolean usesMotorcycle = false; double totalFlightDistance = 0;
        if (userData.getRegularTransportEntries() != null) {
            for (TransportEntry entry : userData.getRegularTransportEntries()) {
                if ("Car".equalsIgnoreCase(entry.getMode())) usesCar = true;
                if ("Motorcycle".equalsIgnoreCase(entry.getMode())) usesMotorcycle = true;
            }
        }
        if (userData.getAnnualNonCommuteCarKm() > 0) usesCar = true;
        if (usesCar) {
            recommendations.add(new Recommendation("Transport", "If driving a car, consider carpooling, combining trips, or using public transport, cycling, or walking for shorter distances."));
            recommendations.add(new Recommendation("Transport", "Maintain the car well (tire pressure, regular servicing) for optimal fuel efficiency."));
        }
        if (usesMotorcycle) recommendations.add(new Recommendation("Transport", "For motorcycle use, ensure good maintenance for fuel efficiency. Consider public transport for longer distances if viable."));
        if (userData.getFlightEntries() != null) {
            for (org.letrancpe.carboncalculator.model.dto.FlightEntry flight : userData.getFlightEntries()) {
                totalFlightDistance += flight.getTotalOneWayFlightsPerYear() * flight.getAverageDistancePerFlightKm();
            }
        }
        if (totalFlightDistance > 0) {
            recommendations.add(new Recommendation("Transport", "Air travel has a significant carbon footprint. For future trips, consider alternatives like train travel for shorter distances, or choose direct flights to reduce emissions."));
            if (totalFlightDistance > 5000) recommendations.add(new Recommendation("Transport", "Given the extent of air travel, explore carbon offsetting programs for unavoidable flights."));
        }
        if (!usesCar && !usesMotorcycle && totalFlightDistance == 0 && transportFootprint > 0) recommendations.add(new Recommendation("Transport", "Using public transport is a great way to reduce individual carbon emissions. Continue to support and utilize these shared services."));
        else if (transportFootprint == 0 && totalFlightDistance == 0) recommendations.add(new Recommendation("Transport", "Low transportation footprint noted. If primarily walking or cycling, keep up the great work!"));
    }

    private void generateWasteRecommendations(UserData userData, double wasteFootprint, List<Recommendation> recommendations) {
        if (userData.getWeeklyNonRecycledWasteKg() > 1.5) {
            recommendations.add(new Recommendation("Waste", "Consider a waste audit for one week to identify common items being thrown away, which can reveal opportunities for reduction."));
        }
        if (userData.getRecyclingRatePercentage() < 50) {
            recommendations.add(new Recommendation("Waste", "Increase recycling efforts. Check local guidelines for what materials are accepted and ensure they are clean and dry."));
        }
        if (wasteFootprint > 0) {
            recommendations.add(new Recommendation("Waste", "Look for opportunities to reduce packaging waste by choosing products with minimal packaging or buying in bulk."));
        }
    }

    private void generateGoodsRecommendations(UserData userData, double goodsFootprint, List<Recommendation> recommendations) {
        if (userData.getAnnualClothingSpendingPHP() > 10000) {
            recommendations.add(new Recommendation("Goods", "Consider extending the life of clothing by repairing items. Explore thrift stores or clothing swaps for sustainable fashion options."));
        }
        if (userData.getAnnualElectronicsSpendingPHP() > 15000) {
            recommendations.add(new Recommendation("Goods", "For electronics, prioritize repair over replacement where possible. When purchasing new, look for models with high energy efficiency ratings."));
        }
        if (goodsFootprint > 0) {
            recommendations.add(new Recommendation("Goods", "Before making a new purchase, consider if the item is a need or a want. Reducing overall consumption is a powerful way to lower your footprint."));
        }
    }
}
