package DigitalTwinBackend.service;

import DigitalTwinBackend.entity.Machine;
import DigitalTwinBackend.repository.MachineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MachineService {

    private final MachineRepository machineRepository;

    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    // Get all machines
    public List<Machine> getAllMachines() {
        return machineRepository.findAll();
    }

    // Get machine by ID
    public Optional<Machine> getMachineById(Integer machineId) {
        return machineRepository.findById(machineId);
    }

    // Create a new machine
    public Machine createMachine(Machine machine) {
        return machineRepository.save(machine);
    }

    // Update an existing machine
    public Machine updateMachine(Integer machineId, Machine machineDetails) {

        Machine existingMachine = machineRepository.findById(machineId)
                .orElseThrow(() -> new RuntimeException(
                        "Machine not found with ID: " + machineId
                ));

        existingMachine.setMachineCode(machineDetails.getMachineCode());
        existingMachine.setMachineName(machineDetails.getMachineName());
        existingMachine.setMachineType(machineDetails.getMachineType());
        existingMachine.setLocation(machineDetails.getLocation());
        existingMachine.setInstallationDate(machineDetails.getInstallationDate());
        existingMachine.setOperationalStatus(machineDetails.getOperationalStatus());

        return machineRepository.save(existingMachine);
    }

    // Delete a machine
    public void deleteMachine(Integer machineId) {

        if (!machineRepository.existsById(machineId)) {
            throw new RuntimeException(
                    "Machine not found with ID: " + machineId
            );
        }

        machineRepository.deleteById(machineId);
    }
}