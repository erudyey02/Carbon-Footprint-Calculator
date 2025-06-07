// File: org/letrancpe/carboncalculator/model/dto/FlightEntry.java
package org.letrancpe.carboncalculator.model.dto;

/**
 * FlightEntry (Data Transfer Object - Model)
 * Represents flights taken by a person, categorized by haul type and cabin class.
 */
public class FlightEntry {
    private String haulType;
    private String cabinClass;
    private int totalOneWayFlightsPerYear;
    private double averageDistancePerFlightKm;

    public FlightEntry(String haulType, String cabinClass, int totalOneWayFlightsPerYear, double averageDistancePerFlightKm) {
        this.haulType = haulType;
        this.cabinClass = cabinClass;
        this.totalOneWayFlightsPerYear = totalOneWayFlightsPerYear;
        this.averageDistancePerFlightKm = averageDistancePerFlightKm;
    }

    public String getHaulType() { return haulType; }
    public void setHaulType(String haulType) { this.haulType = haulType; }
    public String getCabinClass() { return cabinClass; }
    public void setCabinClass(String cabinClass) { this.cabinClass = cabinClass; }
    public int getTotalOneWayFlightsPerYear() { return totalOneWayFlightsPerYear; }
    public void setTotalOneWayFlightsPerYear(int totalOneWayFlightsPerYear) { this.totalOneWayFlightsPerYear = totalOneWayFlightsPerYear; }
    public double getAverageDistancePerFlightKm() { return averageDistancePerFlightKm; }
    public void setAverageDistancePerFlightKm(double averageDistancePerFlightKm) { this.averageDistancePerFlightKm = averageDistancePerFlightKm; }
}
