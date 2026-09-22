package DigitalTwinBackend.service;

import DigitalTwinBackend.dto.MachineDashboardDTO;
import DigitalTwinBackend.entity.Machine;
import DigitalTwinBackend.entity.MachineStatus;
import DigitalTwinBackend.entity.MaintenanceRecord;
import DigitalTwinBackend.entity.ProductionRecord;
import DigitalTwinBackend.entity.SensorReading;
import DigitalTwinBackend.repository.MachineRepository;
import DigitalTwinBackend.repository.MachineStatusRepository;
import DigitalTwinBackend.repository.MaintenanceRecordRepository;
import DigitalTwinBackend.repository.ProductionRecordRepository;
import DigitalTwinBackend.repository.SensorReadingRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class DashboardService {

    private final MachineRepository machineRepository;
    private final MachineStatusRepository machineStatusRepository;
    private final SensorReadingRepository sensorReadingRepository;
    private final ProductionRecordRepository productionRecordRepository;
    private final MaintenanceRecordRepository maintenanceRecordRepository;

    public DashboardService(
            MachineRepository machineRepository,
            MachineStatusRepository machineStatusRepository,
            SensorReadingRepository sensorReadingRepository,
            ProductionRecordRepository productionRecordRepository,
            MaintenanceRecordRepository maintenanceRecordRepository) {

        this.machineRepository = machineRepository;
        this.machineStatusRepository = machineStatusRepository;
        this.sensorReadingRepository = sensorReadingRepository;
        this.productionRecordRepository = productionRecordRepository;
        this.maintenanceRecordRepository = maintenanceRecordRepository;
    }

    public List<MachineDashboardDTO> getDashboardData() {

        List<Machine> machines = machineRepository.findAll();

        return machines.stream()
                .map(this::buildDashboardDTO)
                .toList();
    }

    private MachineDashboardDTO buildDashboardDTO(Machine machine) {

        MachineDashboardDTO dto = new MachineDashboardDTO();

        Integer machineId = machine.getMachineId();

        // Machine information
        dto.setMachineId(machineId);
        dto.setMachineCode(machine.getMachineCode());
        dto.setMachineName(machine.getMachineName());
        dto.setMachineType(machine.getMachineType());
        dto.setLocation(machine.getLocation());
        dto.setOperationalStatus(machine.getOperationalStatus());

        // Machine status
        List<MachineStatus> statuses =
                machineStatusRepository.findByMachineId(machineId);

        if (!statuses.isEmpty()) {
            MachineStatus status = statuses.get(0);
            setStatusData(dto, status);
        }

        // Latest sensor reading
        List<SensorReading> readings =
                sensorReadingRepository.findByMachineId(machineId);

        readings.stream()
                .max(Comparator.comparing(SensorReading::getReadingTime))
                .ifPresent(reading -> setSensorData(dto, reading));

        // Production information
        List<ProductionRecord> productionRecords =
                productionRecordRepository.findByMachineId(machineId);

        if (!productionRecords.isEmpty()) {
            ProductionRecord production = productionRecords.get(0);
            setProductionData(dto, production);
        }

        // Maintenance information
        List<MaintenanceRecord> maintenanceRecords =
                maintenanceRecordRepository.findByMachineId(machineId);

        if (!maintenanceRecords.isEmpty()) {
            MaintenanceRecord maintenance = maintenanceRecords.get(0);
            setMaintenanceData(dto, maintenance);
        }

        return dto;
    }

    private void setStatusData(
            MachineDashboardDTO dto,
            MachineStatus status) {

        dto.setStatus(status.getStatus());
        dto.setHealthScore(status.getHealthScore());
        dto.setAnomalyDetected(status.getAnomalyDetected());
    }

    private void setSensorData(
            MachineDashboardDTO dto,
            SensorReading reading) {

        dto.setTemperature(reading.getTemperature());
        dto.setVibration(reading.getVibration());
        dto.setSpeedRpm(reading.getSpeedRpm());
        dto.setEnergyConsumption(reading.getEnergyConsumption());
    }

    private void setProductionData(
            MachineDashboardDTO dto,
            ProductionRecord production) {

        dto.setUnitsProduced(production.getUnitsProduced());
        dto.setDefectiveUnits(production.getDefectiveUnits());
        dto.setProductionRate(production.getProductionRate());
    }

    private void setMaintenanceData(
            MachineDashboardDTO dto,
            MaintenanceRecord maintenance) {

        dto.setMaintenanceType(maintenance.getMaintenanceType());
        dto.setMaintenanceDescription(maintenance.getDescription());
        dto.setTechnician(maintenance.getTechnician());
    }
    public MachineDashboardDTO getDashboardByMachineId(Integer machineId) {
        return machineRepository.findById(machineId)
                .map(this::buildDashboardDTO)
                .orElse(null);
    }
    public List<MachineDashboardDTO> getAlertMachines() {
        return getDashboardData()
                .stream()
                .filter(machine -> Boolean.TRUE.equals(machine.getAnomalyDetected()))
                .toList();
    }
}