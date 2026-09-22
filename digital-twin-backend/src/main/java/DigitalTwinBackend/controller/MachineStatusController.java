package DigitalTwinBackend.controller;

import DigitalTwinBackend.entity.MachineStatus;
import DigitalTwinBackend.service.MachineStatusService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/machine-status")
@CrossOrigin(origins = "*")
public class MachineStatusController {

    private final MachineStatusService machineStatusService;

    public MachineStatusController(MachineStatusService machineStatusService) {
        this.machineStatusService = machineStatusService;
    }

    // Get all machine status records
    @GetMapping
    public List<MachineStatus> getAllStatuses() {
        return machineStatusService.getAllStatuses();
    }

    // Get status by ID
    @GetMapping("/{id}")
    public Optional<MachineStatus> getStatusById(
            @PathVariable Integer id) {
        return machineStatusService.getStatusById(id);
    }

    // Get status records for a particular machine
    @GetMapping("/machine/{machineId}")
    public List<MachineStatus> getStatusesByMachineId(
            @PathVariable Integer machineId) {
        return machineStatusService.getStatusesByMachineId(machineId);
    }

    // Add a new machine status
    @PostMapping
    public MachineStatus addStatus(
            @RequestBody MachineStatus status) {
        return machineStatusService.addStatus(status);
    }

    // Delete a machine status
    @DeleteMapping("/{id}")
    public void deleteStatus(@PathVariable Integer id) {
        machineStatusService.deleteStatus(id);
    }
}