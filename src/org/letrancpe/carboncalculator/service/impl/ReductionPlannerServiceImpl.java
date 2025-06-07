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
            generateGoodsRecommendations(userData, breakdown.getOrDefault("Goods Consumption", 0.0), recommendations); // Call new goods recommendations
        }

        if (recommendations.isEmpty()) {
            recommendations.add(new Recommendation("General", "Explore various ways to reduce overall consumption and adopt more sustainable habits in daily life!"));
        }

        return recommendations;
    }

    private void generateHousingAndEnergyRecommendations(UserData userData, double housingEnergyFootprint, List<Recommendation> recommendations) {
        // This is a placeholder for the full method implementation
    }

    private void generateDietRecommendations(UserData userData, double dietFootprint, List<Recommendation> recommendations) {
        // This is a placeholder for the full method implementation
    }

    private void generateTransportationRecommendations(UserData userData, double transportFootprint, List<Recommendation> recommendations) {
        // This is a placeholder for the full method implementation
    }

    private void generateWasteRecommendations(UserData userData, double wasteFootprint, List<Recommendation> recommendations) {
        // This is a placeholder for the full method implementation
    }

    private void generateGoodsRecommendations(UserData userData, double goodsFootprint, List<Recommendation> recommendations) {
        if (userData.getAnnualClothingSpendingPHP() > 10000) { // Example threshold
            recommendations.add(new Recommendation("Goods", "Consider extending the life of clothing by repairing items. Explore thrift stores or clothing swaps for sustainable fashion options."));
        }
        if (userData.getAnnualElectronicsSpendingPHP() > 15000) { // Example threshold
            recommendations.add(new Recommendation("Goods", "For electronics, prioritize repair over replacement where possible. When purchasing new, look for models with high energy efficiency ratings."));
        }
        if (goodsFootprint > 0) {
            recommendations.add(new Recommendation("Goods", "Before making a new purchase, consider if the item is a need or a want. Reducing overall consumption is a powerful way to lower your footprint."));
        }
    }
}
