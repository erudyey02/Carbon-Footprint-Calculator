// File: org/letrancpe/carboncalculator/model/dto/TransportEntry.java
package org.letrancpe.carboncalculator.model.dto;

/**
 * TransportEntry (Data Transfer Object - Model)
 * Represents a single mode of regular transport used by a person.
 * This structure may be expanded based on detailed transportation input requirements.
 */
public class TransportEntry {
    private String mode; // Example: Car, Motorcycle, Jeepney, Bus, Train
    private double distancePerWeekKm; // For regular commutes or travel.

    // Fields for private vehicles like Car, Motorcycle
    private String fuelType; // Example: Gasoline, Diesel. Not applicable for public transport using per passenger-km factors.
    private double fuelEfficiencyKmL; // Kilometers per liter, if known.
    private int typicalOccupancy; // For carpooling factor adjustments if applicable.

    // For public transport using per passenger-km factors,
    // distancePerWeekKm would represent total passenger-kilometers for this mode.

    // Constructors, Getters, Setters
    public TransportEntry(String mode, double distancePerWeekKm) {
        this.mode = mode;
        this.distancePerWeekKm = distancePerWeekKm;
    }

    // Additional constructors can be added as needed.

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public double getDistancePerWeekKm() { return distancePerWeekKm; }
    public void setDistancePerWeekKm(double distancePerWeekKm) { this.distancePerWeekKm = distancePerWeekKm; }

    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }

    public double getFuelEfficiencyKmL() { return fuelEfficiencyKmL; }
    public void setFuelEfficiencyKmL(double fuelEfficiencyKmL) { this.fuelEfficiencyKmL = fuelEfficiencyKmL; }

    public int getTypicalOccupancy() { return typicalOccupancy; }
    public void setTypicalOccupancy(int typicalOccupancy) { this.typicalOccupancy = typicalOccupancy; }
}
