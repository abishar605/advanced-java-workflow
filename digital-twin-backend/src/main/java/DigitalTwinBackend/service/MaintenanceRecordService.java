package DigitalTwinBackend.service;

import DigitalTwinBackend.entity.MaintenanceRecord;
import DigitalTwinBackend.repository.MaintenanceRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceRecordService {

    private final MaintenanceRecordRepository maintenanceRecordRepository;

    public MaintenanceRecordService(
            MaintenanceRecordRepository maintenanceRecordRepository) {
        this.maintenanceRecordRepository = maintenanceRecordRepository;
    }

    // Get all maintenance records
    public List<MaintenanceRecord> getAllMaintenanceRecords() {
        return maintenanceRecordRepository.findAll();
    }

    // Get maintenance record by ID
    public Optional<MaintenanceRecord> getMaintenanceRecordById(Integer id) {
        return maintenanceRecordRepository.findById(id);
    }

    // Get maintenance records for a particular machine
    public List<MaintenanceRecord> getMaintenanceRecordsByMachineId(
            Integer machineId) {
        return maintenanceRecordRepository.findByMachineId(machineId);
    }

    // Add a new maintenance record
    public MaintenanceRecord addMaintenanceRecord(
            MaintenanceRecord maintenanceRecord) {
        return maintenanceRecordRepository.save(maintenanceRecord);
    }

    // Delete a maintenance record
    public void deleteMaintenanceRecord(Integer id) {

        if (!maintenanceRecordRepository.existsById(id)) {
            throw new RuntimeException(
                    "Maintenance record not found with ID: " + id
            );
        }

        maintenanceRecordRepository.deleteById(id);
    }
}