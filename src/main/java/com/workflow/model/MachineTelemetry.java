package com.workflow.model;

public class MachineTelemetry {
    private String machineId;
    private double temperature;
    private double vibration;
    private int rpm;
    private double healthScore;
    private String status;

    public MachineTelemetry(String machineId, double temperature, double vibration, int rpm, double healthScore, String status) {
        this.machineId = machineId;
        this.temperature = temperature;
        this.vibration = vibration;
        this.rpm = rpm;
        this.healthScore = healthScore;
        this.status = status;
    }

    public String getMachineId() { return machineId; }
    public double getTemperature() { return temperature; }
    public double getVibration() { return vibration; }
    public int getRpm() { return rpm; }
    public double getHealthScore() { return healthScore; }
    public String getStatus() { return status; }
}