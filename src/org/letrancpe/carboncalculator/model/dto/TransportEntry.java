// File: org/letrancpe/carboncalculator/model/dto/TransportEntry.java
package org.letrancpe.carboncalculator.model.dto;

/**
 * TransportEntry (Data Transfer Object - Model)
 * Represents a single mode of regular transport used by a person.
 */
public class TransportEntry {
    private String mode;
    private double distanceKm;
    private String frequency;
    private String fuelType;
    private double fuelEfficiencyKmL;
    private int passengers;

    public TransportEntry(String mode, double distanceKm, String frequency) {
        this.mode = mode;
        this.distanceKm = distanceKm;
        this.frequency = frequency;
        this.passengers = 1;
    }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }
    public double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }
    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    public double getFuelEfficiencyKmL() { return fuelEfficiencyKmL; }
    public void setFuelEfficiencyKmL(double fuelEfficiencyKmL) { this.fuelEfficiencyKmL = fuelEfficiencyKmL; }
    public int getPassengers() { return passengers; }
    public void setPassengers(int passengers) { this.passengers = passengers; }
}
