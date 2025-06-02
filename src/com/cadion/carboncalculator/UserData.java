package com.cadion.carboncalculator;

public class UserData {
    private double electricityKwh;
    private double transportKm;
    private double wasteKg;
    // For alternative electricity calculation
    private int numAcUnits;
    private double hoursAcUsage;
    private boolean useAlternativeElectricity = false;


    // Getters and Setters
    public double getElectricityKwh() { return electricityKwh; }
    public void setElectricityKwh(double electricityKwh) { this.electricityKwh = electricityKwh; }

    public double getTransportKm() { return transportKm; }
    public void setTransportKm(double transportKm) { this.transportKm = transportKm; }

    public double getWasteKg() { return wasteKg; }
    public void setWasteKg(double wasteKg) { this.wasteKg = wasteKg; }

    public int getNumAcUnits() { return numAcUnits; }
    public void setNumAcUnits(int numAcUnits) { this.numAcUnits = numAcUnits; }

    public double getHoursAcUsage() { return hoursAcUsage; }
    public void setHoursAcUsage(double hoursAcUsage) { this.hoursAcUsage = hoursAcUsage; }

    public boolean isUseAlternativeElectricity() { return useAlternativeElectricity; }
    public void setUseAlternativeElectricity(boolean useAlternativeElectricity) { this.useAlternativeElectricity = useAlternativeElectricity;}
}