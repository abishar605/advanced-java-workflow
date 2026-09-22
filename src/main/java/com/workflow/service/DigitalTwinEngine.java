package com.workflow.service;

import com.workflow.dao.TaskDAO;
import com.workflow.model.MachineTelemetry;
import com.workflow.model.Task;

import java.sql.SQLException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PATENT NOVELTY: Dynamic Multi-Harmonic Thermal-Vibration Coupling (DM-TVC)
 * with Adaptive Degradation Velocity (ADV) for Real-Time Remaining Useful Life (RUL) Estimation.
 */
public class DigitalTwinEngine {
    private static final Random random = new Random();
    private final TaskDAO taskDAO = new TaskDAO();

    // Memory cache to track degradation velocity between consecutive telemetry cycles
    private static final Map<String, Double> previousHealthScores = new ConcurrentHashMap<>();

    public MachineTelemetry evaluateAsset(String machineId) {
        // 1. Simulate high-frequency IoT sensor telemetry
        double temperature = 65.0 + (random.nextDouble() * 40.0); // 65°C to 105°C
        double vibration = 1.2 + (random.nextDouble() * 4.5);      // 1.2 to 5.7 mm/s
        int rpm = 1200 + random.nextInt(600);

        // 2. Base isolated degradation penalties
        double tempPenalty = (temperature > 85.0) ? (temperature - 85.0) * 1.8 : 0.0;
        double vibPenalty = (vibration > 3.0) ? (vibration - 3.0) * 12.0 : 0.0;

        // 3. PATENT INNOVATION: Multi-Harmonic Synergistic Penalty
        // Failure likelihood accelerates exponentially when high temperature and high vibration co-occur
        double harmonicCouplingFactor = 0.0;
        if (temperature > 85.0 && vibration > 3.0) {
            harmonicCouplingFactor = ((temperature - 85.0) / 10.0) * ((vibration - 3.0) / 1.0) * 5.5;
        }

        double rawHealth = 100.0 - (tempPenalty + vibPenalty + harmonicCouplingFactor);
        double currentHealth = Math.max(5.0, Math.min(100.0, rawHealth));

        // 4. PATENT INNOVATION: Degradation Velocity & Predictive RUL Forecast
        double prevHealth = previousHealthScores.getOrDefault(machineId, currentHealth);
        double degradationVelocity = Math.max(0.0, prevHealth - currentHealth); // Drop per poll cycle
        previousHealthScores.put(machineId, currentHealth);

        // Estimated cycles to failure (assuming critical failure threshold at 20% health)
        int estimatedCyclesToFailure = (degradationVelocity > 0.5) 
            ? (int) Math.max(1, (currentHealth - 20.0) / degradationVelocity)
            : 999;

        // 5. Classification
        String status;
        if (currentHealth < 50.0 || estimatedCyclesToFailure <= 5) {
            status = "CRITICAL";
        } else if (currentHealth < 75.0 || estimatedCyclesToFailure <= 15) {
            status = "WARNING";
        } else {
            status = "OPTIMAL";
        }

        // 6. Automated Prescriptive Maintenance Generation
        if ("CRITICAL".equals(status)) {
            try {
                String recommendation = String.format(
                    "[PATENT DM-TVC ALERT] Coupling Factor: %.2f | Velocity: -%.1f%%/s | Est. Cycles to Fail: %d | Temp: %.1f deg C, Vib: %.2f mm/s",
                    harmonicCouplingFactor, degradationVelocity, estimatedCyclesToFailure, temperature, vibration
                );
                taskDAO.addTask(new Task(
                    0,
                    "Novel Predictive Maintenance: " + machineId,
                    recommendation,
                    "ACTION_REQUIRED"
                ));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return new MachineTelemetry(
            machineId,
            Math.round(temperature * 10.0) / 10.0,
            Math.round(vibration * 100.0) / 100.0,
            rpm,
            Math.round(currentHealth * 10.0) / 10.0,
            status
        );
    }
}