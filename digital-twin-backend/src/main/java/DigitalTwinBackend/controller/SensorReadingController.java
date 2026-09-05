package DigitalTwinBackend.controller;

import DigitalTwinBackend.entity.SensorReading;
import DigitalTwinBackend.service.SensorReadingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sensor-readings")
@CrossOrigin(origins = "*")
public class SensorReadingController {

    private final SensorReadingService sensorReadingService;

    public SensorReadingController(SensorReadingService sensorReadingService) {
        this.sensorReadingService = sensorReadingService;
    }

    // Get all sensor readings
    @GetMapping
    public List<SensorReading> getAllReadings() {
        return sensorReadingService.getAllSensorReadings();
    }

    // Get sensor reading by ID
    @GetMapping("/{id}")
    public Optional<SensorReading> getReadingById(@PathVariable Integer id) {
        return sensorReadingService.getSensorReadingById(id);
    }

    // Get sensor readings for a particular machine
    @GetMapping("/machine/{machineId}")
    public List<SensorReading> getReadingsByMachineId(
            @PathVariable Integer machineId) {
        return sensorReadingService.getSensorReadingsByMachineId(machineId);
    }

    // Add a new sensor reading
    @PostMapping
    public SensorReading addSensorReading(
            @RequestBody SensorReading sensorReading) {
        return sensorReadingService.addSensorReading(sensorReading);
    }

    // Update an existing sensor reading
    @PutMapping("/{id}")
    public SensorReading updateSensorReading(
            @PathVariable Integer id,
            @RequestBody SensorReading sensorReading) {
        return sensorReadingService.updateSensorReading(
                id,
                sensorReading
        );
    }

    // Delete a sensor reading
    @DeleteMapping("/{id}")
    public void deleteSensorReading(@PathVariable Integer id) {
        sensorReadingService.deleteSensorReading(id);
    }
}