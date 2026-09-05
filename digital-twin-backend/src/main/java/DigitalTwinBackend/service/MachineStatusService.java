package DigitalTwinBackend.service;

import DigitalTwinBackend.entity.MachineStatus;
import DigitalTwinBackend.repository.MachineStatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MachineStatusService {

    private final MachineStatusRepository machineStatusRepository;

    public MachineStatusService(MachineStatusRepository machineStatusRepository) {
        this.machineStatusRepository = machineStatusRepository;
    }

    // Get all machine status records
    public List<MachineStatus> getAllStatuses() {
        return machineStatusRepository.findAll();
    }

    // Get machine status by ID
    public Optional<MachineStatus> getStatusById(Integer id) {
        return machineStatusRepository.findById(id);
    }

    // Get status records for a particular machine
    public List<MachineStatus> getStatusesByMachineId(Integer machineId) {
        return machineStatusRepository.findByMachineId(machineId);
    }

    // Add a new machine status record
    public MachineStatus addStatus(MachineStatus status) {
        return machineStatusRepository.save(status);
    }

    // Delete a machine status record
    public void deleteStatus(Integer id) {
        if (!machineStatusRepository.existsById(id)) {
            throw new RuntimeException(
                    "Machine status not found with ID: " + id
            );
        }

        machineStatusRepository.deleteById(id);
    }
}