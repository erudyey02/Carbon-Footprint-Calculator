// File: org/letrancpe/carboncalculator/service/ReductionPlannerService.java
package org.letrancpe.carboncalculator.service;

import org.letrancpe.carboncalculator.model.UserData;
import org.letrancpe.carboncalculator.model.dto.CalculatedFootprintData;
import org.letrancpe.carboncalculator.model.dto.Recommendation;

import java.util.List;

/**
 * ReductionPlannerService (Service layer)
 * Defines the interface for generating personalized reduction recommendations.
 */
public interface ReductionPlannerService {
    /**
     * Generates a list of personalized recommendations based on user data and their calculated footprint.
     * @param userData The user's input data.
     * @param footprintData The calculated carbon footprint data.
     * @return A list of Recommendation objects.
     */
    List<Recommendation> generateRecommendations(UserData userData, CalculatedFootprintData footprintData);
}
