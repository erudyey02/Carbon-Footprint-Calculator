// File: org/letrancpe/carboncalculator/service/CalculationService.java
package org.letrancpe.carboncalculator.service;

import org.letrancpe.carboncalculator.model.UserData;
import org.letrancpe.carboncalculator.model.dto.CalculatedFootprintData;

/**
 * CalculationService (Service layer - part of Model or Controller interaction logic)
 * Defines the interface for the carbon footprint calculation logic.
 */
public interface CalculationService {
    /**
     * Calculates the total carbon footprint based on the provided user data.
     * @param userData The user's input data.
     * @return A CalculatedFootprintData object containing the total footprint and breakdown by category.
     */
    CalculatedFootprintData calculateFootprint(UserData userData);
}
