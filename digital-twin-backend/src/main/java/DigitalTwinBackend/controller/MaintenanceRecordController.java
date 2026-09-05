package DigitalTwinBackend.controller;

import DigitalTwinBackend.entity.MaintenanceRecord;
import DigitalTwinBackend.service.MaintenanceRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/maintenance")
@CrossOrigin(origins = "*")
public class MaintenanceRecordController {

    private final MaintenanceRecordService maintenanceRecordService;

    public MaintenanceRecordController(
            MaintenanceRecordService maintenanceRecordService) {
        this.maintenanceRecordService = maintenanceRecordService;
    }

    // Get all maintenance records
    @GetMapping
    public List<MaintenanceRecord> getAllMaintenanceRecords() {
        return maintenanceRecordService.getAllMaintenanceRecords();
    }

    // Get maintenance record by ID
    @GetMapping("/{id}")
    public Optional<MaintenanceRecord> getMaintenanceRecordById(
            @PathVariable Integer id) {
        return maintenanceRecordService.getMaintenanceRecordById(id);
    }

    // Get maintenance records for a particular machine
    @GetMapping("/machine/{machineId}")
    public List<MaintenanceRecord> getMaintenanceRecordsByMachineId(
            @PathVariable Integer machineId) {
        return maintenanceRecordService
                .getMaintenanceRecordsByMachineId(machineId);
    }

    // Add a new maintenance record
    @PostMapping
    public MaintenanceRecord addMaintenanceRecord(
            @RequestBody MaintenanceRecord maintenanceRecord) {
        return maintenanceRecordService
                .addMaintenanceRecord(maintenanceRecord);
    }

    // Delete a maintenance record
    @DeleteMapping("/{id}")
    public void deleteMaintenanceRecord(@PathVariable Integer id) {
        maintenanceRecordService.deleteMaintenanceRecord(id);
    }
}