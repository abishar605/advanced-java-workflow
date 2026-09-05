package DigitalTwinBackend.service;

import DigitalTwinBackend.entity.SensorReading;
import DigitalTwinBackend.repository.SensorReadingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensorReadingService {

    private final SensorReadingRepository sensorReadingRepository;

    public SensorReadingService(SensorReadingRepository sensorReadingRepository) {
        this.sensorReadingRepository = sensorReadingRepository;
    }

    // Get all sensor readings
    public List<SensorReading> getAllSensorReadings() {
        return sensorReadingRepository.findAll();
    }

    // Get sensor reading by ID
    public Optional<SensorReading> getSensorReadingById(Integer id) {
        return sensorReadingRepository.findById(id);
    }

    // Get sensor readings for a particular machine
    public List<SensorReading> getSensorReadingsByMachineId(Integer machineId) {
        return sensorReadingRepository.findByMachineId(machineId);
    }

    // Add a new sensor reading
    public SensorReading addSensorReading(SensorReading sensorReading) {
        return sensorReadingRepository.save(sensorReading);
    }

    // Update an existing sensor reading
    public SensorReading updateSensorReading(
            Integer readingId,
            SensorReading sensorReadingDetails) {

        SensorReading existingReading =
                sensorReadingRepository.findById(readingId)
                        .orElseThrow(() -> new RuntimeException(
                                "Sensor reading not found with ID: " + readingId
                        ));

        existingReading.setMachineId(sensorReadingDetails.getMachineId());
        existingReading.setTemperature(sensorReadingDetails.getTemperature());
        existingReading.setVibration(sensorReadingDetails.getVibration());
        existingReading.setSpeedRpm(sensorReadingDetails.getSpeedRpm());
        existingReading.setEnergyConsumption(
                sensorReadingDetails.getEnergyConsumption()
        );
        existingReading.setReadingTime(
                sensorReadingDetails.getReadingTime()
        );

        return sensorReadingRepository.save(existingReading);
    }

    // Delete a sensor reading
    public void deleteSensorReading(Integer readingId) {

        if (!sensorReadingRepository.existsById(readingId)) {
            throw new RuntimeException(
                    "Sensor reading not found with ID: " + readingId
            );
        }

        sensorReadingRepository.deleteById(readingId);
    }
}