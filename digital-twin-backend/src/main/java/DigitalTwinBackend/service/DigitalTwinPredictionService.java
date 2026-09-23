package DigitalTwinBackend.service;

import DigitalTwinBackend.entity.Machine;
import DigitalTwinBackend.entity.MaintenanceRecord;
import DigitalTwinBackend.repository.MachineRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DigitalTwinPredictionService {

    private static final Random random = new Random();

    /*
     * Tracks the previous health score for each machine.
     * This is used to calculate Adaptive Degradation Velocity (ADV).
     */
    private final Map<String, Double> previousHealthScores =
            new ConcurrentHashMap<>();

    /*
     * Prevents the same machine from creating a new maintenance
     * record on every dashboard refresh while it remains CRITICAL.
     */
    private final Map<String, String> previousStatuses =
            new ConcurrentHashMap<>();

    private final MachineRepository machineRepository;
    private final MaintenanceRecordService maintenanceRecordService;

    public DigitalTwinPredictionService(
            MachineRepository machineRepository,
            MaintenanceRecordService maintenanceRecordService) {

        this.machineRepository = machineRepository;
        this.maintenanceRecordService = maintenanceRecordService;
    }

    public DigitalTwinPrediction evaluateMachine(String machineCode) {

        Optional<Machine> machineOptional =
                machineRepository.findByMachineCode(machineCode);

        if (machineOptional.isEmpty()) {
            throw new RuntimeException(
                    "Machine not found with code: " + machineCode
            );
        }

        Machine machine = machineOptional.get();

        /*
         * 1. SIMULATED IoT SENSOR TELEMETRY
         *
         * Same ranges used by Member 2's DigitalTwinEngine.
         */
        double temperature =
                65.0 + (random.nextDouble() * 40.0);

        double vibration =
                1.2 + (random.nextDouble() * 4.5);

        int rpm =
                1200 + random.nextInt(600);

        /*
         * 2. BASE DEGRADATION PENALTIES
         */
        double tempPenalty =
                (temperature > 85.0)
                        ? (temperature - 85.0) * 1.8
                        : 0.0;

        double vibPenalty =
                (vibration > 3.0)
                        ? (vibration - 3.0) * 12.0
                        : 0.0;

        /*
         * 3. PATENT NOVELTY:
         * Dynamic Multi-Harmonic Thermal-Vibration Coupling (DM-TVC)
         *
         * When both temperature and vibration are high,
         * the combined penalty increases.
         */
        double harmonicCouplingFactor = 0.0;

        if (temperature > 85.0 && vibration > 3.0) {

            harmonicCouplingFactor =
                    ((temperature - 85.0) / 10.0)
                    * ((vibration - 3.0) / 1.0)
                    * 5.5;
        }

        double rawHealth =
                100.0
                - (tempPenalty
                + vibPenalty
                + harmonicCouplingFactor);

        double currentHealth =
                Math.max(
                        5.0,
                        Math.min(100.0, rawHealth)
                );

        /*
         * 4. ADAPTIVE DEGRADATION VELOCITY (ADV)
         */
        double previousHealth =
                previousHealthScores.getOrDefault(
                        machineCode,
                        currentHealth
                );

        double degradationVelocity =
                Math.max(
                        0.0,
                        previousHealth - currentHealth
                );

        previousHealthScores.put(
                machineCode,
                currentHealth
        );

        /*
         * 5. REMAINING USEFUL LIFE (RUL) ESTIMATION
         *
         * Critical failure threshold = 20% health.
         */
        int estimatedCyclesToFailure;

        if (degradationVelocity > 0.5) {

            estimatedCyclesToFailure =
                    (int) Math.max(
                            1,
                            (currentHealth - 20.0)
                                    / degradationVelocity
                    );

        } else {

            estimatedCyclesToFailure = 999;
        }

        /*
         * 6. MACHINE HEALTH CLASSIFICATION
         */
        String status;

        if (currentHealth < 50.0
                || estimatedCyclesToFailure <= 5) {

            status = "CRITICAL";

        } else if (currentHealth < 75.0
                || estimatedCyclesToFailure <= 15) {

            status = "WARNING";

        } else {

            status = "OPTIMAL";
        }

        /*
         * 7. PREDICTIVE MAINTENANCE
         *
         * Create a maintenance record only when the machine
         * enters CRITICAL state, rather than on every refresh.
         */
        String previousStatus =
                previousStatuses.put(
                        machineCode,
                        status
                );

        if ("CRITICAL".equals(status)
                && !"CRITICAL".equals(previousStatus)) {

            String recommendation =
                    String.format(
                            "[DM-TVC Predictive Maintenance] "
                            + "Coupling Factor: %.2f | "
                            + "Degradation Velocity: -%.1f%% | "
                            + "Estimated Cycles to Failure: %d | "
                            + "Temperature: %.1f deg C | "
                            + "Vibration: %.2f mm/s | "
                            + "RPM: %d",
                            harmonicCouplingFactor,
                            degradationVelocity,
                            estimatedCyclesToFailure,
                            temperature,
                            vibration,
                            rpm
                    );

            MaintenanceRecord maintenanceRecord =
                    new MaintenanceRecord();

            maintenanceRecord.setMachineId(
                    machine.getMachineId()
            );

            maintenanceRecord.setMaintenanceType(
                    "PREDICTIVE"
            );

            maintenanceRecord.setDescription(
                    recommendation
            );

            maintenanceRecord.setMaintenanceDate(
                    LocalDate.now()
            );

            maintenanceRecord.setTechnician(
                    "AI Digital Twin Engine"
            );

            maintenanceRecordService.addMaintenanceRecord(
                    maintenanceRecord
            );
        }

        /*
         * 8. RETURN DIGITAL TWIN PREDICTION
         */
        return new DigitalTwinPrediction(
                machine.getMachineId(),
                machine.getMachineCode(),
                machine.getMachineName(),
                Math.round(temperature * 10.0) / 10.0,
                Math.round(vibration * 100.0) / 100.0,
                rpm,
                Math.round(currentHealth * 10.0) / 10.0,
                status,
                Math.round(harmonicCouplingFactor * 100.0) / 100.0,
                Math.round(degradationVelocity * 100.0) / 100.0,
                estimatedCyclesToFailure
        );
    }

    /*
     * DTO returned by the Digital Twin prediction service.
     */
    public static class DigitalTwinPrediction {

        private final Integer machineId;
        private final String machineCode;
        private final String machineName;
        private final double temperature;
        private final double vibration;
        private final int rpm;
        private final double healthScore;
        private final String status;
        private final double harmonicCouplingFactor;
        private final double degradationVelocity;
        private final int estimatedCyclesToFailure;

        public DigitalTwinPrediction(
                Integer machineId,
                String machineCode,
                String machineName,
                double temperature,
                double vibration,
                int rpm,
                double healthScore,
                String status,
                double harmonicCouplingFactor,
                double degradationVelocity,
                int estimatedCyclesToFailure) {

            this.machineId = machineId;
            this.machineCode = machineCode;
            this.machineName = machineName;
            this.temperature = temperature;
            this.vibration = vibration;
            this.rpm = rpm;
            this.healthScore = healthScore;
            this.status = status;
            this.harmonicCouplingFactor =
                    harmonicCouplingFactor;
            this.degradationVelocity =
                    degradationVelocity;
            this.estimatedCyclesToFailure =
                    estimatedCyclesToFailure;
        }

        public Integer getMachineId() {
            return machineId;
        }

        public String getMachineCode() {
            return machineCode;
        }

        public String getMachineName() {
            return machineName;
        }

        public double getTemperature() {
            return temperature;
        }

        public double getVibration() {
            return vibration;
        }

        public int getRpm() {
            return rpm;
        }

        public double getHealthScore() {
            return healthScore;
        }

        public String getStatus() {
            return status;
        }

        public double getHarmonicCouplingFactor() {
            return harmonicCouplingFactor;
        }

        public double getDegradationVelocity() {
            return degradationVelocity;
        }

        public int getEstimatedCyclesToFailure() {
            return estimatedCyclesToFailure;
        }
    }
}
