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
        }

        if (recommendations.isEmpty()) {
            recommendations.add(new Recommendation("General", "Explore various ways to reduce overall consumption and adopt more sustainable habits in daily life!"));
        }

        return recommendations;
    }

    private void generateHousingAndEnergyRecommendations(UserData userData, double housingEnergyFootprint, List<Recommendation> recommendations) {
        // ... (implementation from previous step) ...
    }

    private void generateDietRecommendations(UserData userData, double dietFootprint, List<Recommendation> recommendations) {
        // ... (implementation from previous step) ...
    }

    private void generateTransportationRecommendations(UserData userData, double transportFootprint, List<Recommendation> recommendations) {
        // ... (implementation from previous step) ...
    }

    private void generateWasteRecommendations(UserData userData, double wasteFootprint, List<Recommendation> recommendations) {
        if (userData.getWeeklyNonRecycledWasteKg() > 1.5) { // Example threshold
            recommendations.add(new Recommendation("Waste", "Consider a waste audit for one week to identify common items being thrown away, which can reveal opportunities for reduction."));
        }

        if (userData.getRecyclingRatePercentage() < 50) { // Example threshold
            recommendations.add(new Recommendation("Waste", "Increase recycling efforts. Check local guidelines for what materials are accepted and ensure they are clean and dry."));
        }

        if (wasteFootprint > 0) {
            recommendations.add(new Recommendation("Waste", "Look for opportunities to reduce packaging waste by choosing products with minimal packaging or buying in bulk."));
        }
    }
    // TODO: Add generateGoodsRecommendations
}
