// File: org/letrancpe/carboncalculator/model/dto/FlightEntry.java
package org.letrancpe.carboncalculator.model.dto;

/**
 * FlightEntry (Data Transfer Object - Model)
 * Represents flights taken by a person, categorized by haul type and cabin class.
 */
public class FlightEntry {
    private String haulType; // Example: "Short", "Medium", "Long"
    private String cabinClass; // Example: "Economy", "Business"
    private int totalOneWayFlightsPerYear; // Total number of one-way flight segments for this type/class combination.

    // Constructors, Getters, Setters
    public FlightEntry(String haulType, String cabinClass, int totalOneWayFlightsPerYear) {
        this.haulType = haulType;
        this.cabinClass = cabinClass;
        this.totalOneWayFlightsPerYear = totalOneWayFlightsPerYear;
    }

    public String getHaulType() { return haulType; }
    public void setHaulType(String haulType) { this.haulType = haulType; }

    public String getCabinClass() { return cabinClass; }
    public void setCabinClass(String cabinClass) { this.cabinClass = cabinClass; }

    public int getTotalOneWayFlightsPerYear() { return totalOneWayFlightsPerYear; }
    public void setTotalOneWayFlightsPerYear(int totalOneWayFlightsPerYear) { this.totalOneWayFlightsPerYear = totalOneWayFlightsPerYear; }
}
