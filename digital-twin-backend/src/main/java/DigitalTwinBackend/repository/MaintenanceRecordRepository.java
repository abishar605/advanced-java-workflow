package DigitalTwinBackend.repository;

import DigitalTwinBackend.entity.MaintenanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceRecordRepository
        extends JpaRepository<MaintenanceRecord, Integer> {

    List<MaintenanceRecord> findByMachineId(Integer machineId);
}