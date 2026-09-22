package DigitalTwinBackend.controller;

import DigitalTwinBackend.dto.MachineDashboardDTO;
import DigitalTwinBackend.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public List<MachineDashboardDTO> getDashboardData() {
        return dashboardService.getDashboardData();
    }

    @GetMapping("/{machineId}")
    public MachineDashboardDTO getDashboardByMachineId(@PathVariable Integer machineId) {
        return dashboardService.getDashboardData()
                .stream()
                .filter(machine -> machine.getMachineId().equals(machineId))
                .findFirst()
                .orElse(null);
    }
    @GetMapping("/alerts")
    public List<MachineDashboardDTO> getAlertMachines() {
        return dashboardService.getAlertMachines();
    }
}