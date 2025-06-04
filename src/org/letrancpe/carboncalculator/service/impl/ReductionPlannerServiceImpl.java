// File: org/letrancpe/carboncalculator/service/impl/ReductionPlannerServiceImpl.java
package org.letrancpe.carboncalculator.service.impl;

import org.letrancpe.carboncalculator.model.UserData;
import org.letrancpe.carboncalculator.model.dto.CalculatedFootprintData;
import org.letrancpe.carboncalculator.model.dto.Recommendation;
import org.letrancpe.carboncalculator.service.ReductionPlannerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of ReductionPlannerService.
 * This class contains logic to generate personalized recommendations based on user data
 * and their calculated carbon footprint.
 */
public class ReductionPlannerServiceImpl implements ReductionPlannerService {

    @Override
    public List<Recommendation> generateRecommendations(UserData userData, CalculatedFootprintData footprintData) {
        System.out.println("Generating recommendations..."); // Log: Recommendation generation start.
        List<Recommendation> recommendations = new ArrayList<>();

        // General recommendations that can always be added.
        recommendations.add(new Recommendation("General", "Regularly review energy bills to track consumption patterns and identify areas for savings."));
        recommendations.add(new Recommendation("General", "Consider supporting businesses and products that demonstrate strong sustainability practices."));

        if (footprintData != null && footprintData.getFootprintByCategory() != null) {
            Map<String, Double> breakdown = footprintData.getFootprintByCategory();

            // Generate recommendations for Housing & Energy.
            generateHousingAndEnergyRecommendations(userData, breakdown.getOrDefault("Housing & Energy", 0.0), recommendations);

            // TODO: Implement recommendation generation for other categories.
            // generateDietRecommendations(userData, breakdown.getOrDefault("Diet", 0.0), recommendations);
            // generateTransportationRecommendations(userData, breakdown.getOrDefault("Transport", 0.0), recommendations);
            // generateWasteRecommendations(userData, breakdown.getOrDefault("Waste Management", 0.0), recommendations);
            // generateGoodsRecommendations(userData, breakdown.getOrDefault("Goods Consumption", 0.0), recommendations);
        }

        if (recommendations.isEmpty()) {
            // Add a default recommendation if no specific ones were generated.
            recommendations.add(new Recommendation("General", "Explore various ways to reduce overall consumption and adopt more sustainable habits in daily life!"));
        }

        return recommendations;
    }

    private void generateHousingAndEnergyRecommendations(UserData userData, double housingEnergyFootprint, List<Recommendation> recommendations) {
        // Example recommendations based on overall electricity usage.
        if (userData.getMonthlyElectricityKWh() > 200) { // Example threshold for high usage.
            recommendations.add(new Recommendation("Energy", "High electricity usage noted. Consider switching to LED lighting, unplugging unused appliances, or investing in newer, energy-efficient models."));
        } else if (userData.getMonthlyElectricityKWh() > 50) { // Example threshold for moderate usage.
            recommendations.add(new Recommendation("Energy", "Look for opportunities to reduce electricity use, such as optimizing appliance settings, using natural light more often, or ensuring devices are turned off when not in use."));
        }

        // Example recommendations based on cooking fuel.
        String cookingFuel = userData.getPrimaryCookingFuelType();
        if ("Charcoal".equalsIgnoreCase(cookingFuel) || "Wood".equalsIgnoreCase(cookingFuel)) {
            recommendations.add(new Recommendation("Energy", "Using charcoal or wood for cooking can result in higher emissions. If feasible, explore cleaner alternatives like LPG, electric induction, or improved efficiency cookstoves."));
            if ("Wood".equalsIgnoreCase(cookingFuel) && !userData.isWoodSustainablySourced()) {
                recommendations.add(new Recommendation("Energy", "If using wood for cooking, attempt to source it from sustainable suppliers to lessen its environmental impact."));
            }
        }

        // Example recommendations based on dwelling type (qualitative).
        if ("Apartment / Condominium".equalsIgnoreCase(userData.getDwellingType())) {
            recommendations.add(new Recommendation("Housing", "For apartment/condo residents: Discuss potential energy-saving initiatives with building management or the homeowners' association."));
        }

        // Example recommendations based on (optional) energy efficiency description.
        String efficiencyInfo = userData.getEnergyEfficiencyRating();
        if (efficiencyInfo != null && !efficiencyInfo.isEmpty()) {
            if (!efficiencyInfo.toLowerCase().contains("insulated") && !efficiencyInfo.toLowerCase().contains("berde")) {
                recommendations.add(new Recommendation("Housing", "Consider improving home insulation (e.g., for roof or walls) if not already implemented, to help reduce heating/cooling energy needs."));
            }
            if (efficiencyInfo.toLowerCase().contains("old ac") || efficiencyInfo.toLowerCase().contains("old aircon")) {
                recommendations.add(new Recommendation("Housing", "If using older air conditioning units, consider upgrading to newer, more energy-efficient models when replacement is feasible."));
            }
        }


        // Example recommendations based on water usage.
        if (userData.getDailyHouseholdWaterUsageLiters() > 500) { // Example threshold for a household.
            recommendations.add(new Recommendation("Water", "High household water usage noted. Check for leaks, consider installing water-efficient fixtures (taps, showerheads), and practice water-saving habits."));
        }
    }
    // TODO: Add specific recommendation generator methods for Diet, Transport, Waste, and Goods categories.
}
