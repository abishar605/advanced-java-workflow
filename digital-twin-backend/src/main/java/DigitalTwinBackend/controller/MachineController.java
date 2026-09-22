package DigitalTwinBackend.controller;

import DigitalTwinBackend.entity.Machine;
import DigitalTwinBackend.service.MachineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/machines")
@CrossOrigin(origins = "*")
public class MachineController {

    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping
    public List<Machine> getAllMachines() {
        return machineService.getAllMachines();
    }

    @GetMapping("/{id}")
    public Optional<Machine> getMachineById(@PathVariable Integer id) {
        return machineService.getMachineById(id);
    }
}