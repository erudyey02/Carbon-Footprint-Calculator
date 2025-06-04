// File: org/letrancpe/carboncalculator/model/dto/CalculatedFootprintData.java
package org.letrancpe.carboncalculator.model.dto;

import java.util.Map;

/**
 * CalculatedFootprintData (Data Transfer Object - Model)
 * Stores the results of the carbon footprint calculation, including the total
 * and a breakdown by category.
 */
public class CalculatedFootprintData {
    private double totalAnnualFootprintKgCO2e;
    private Map<String, Double> footprintByCategory; // Example: {"Housing & Energy": 1200.5, "Transport": 800.2}

    public CalculatedFootprintData(double totalAnnualFootprintKgCO2e, Map<String, Double> footprintByCategory) {
        this.totalAnnualFootprintKgCO2e = totalAnnualFootprintKgCO2e;
        this.footprintByCategory = footprintByCategory;
    }

    public double getTotalAnnualFootprintKgCO2e() { return totalAnnualFootprintKgCO2e; }
    public void setTotalAnnualFootprintKgCO2e(double totalAnnualFootprintKgCO2e) { this.totalAnnualFootprintKgCO2e = totalAnnualFootprintKgCO2e; }

    public Map<String, Double> getFootprintByCategory() { return footprintByCategory; }
    public void setFootprintByCategory(Map<String, Double> footprintByCategory) { this.footprintByCategory = footprintByCategory; }
}
