package DigitalTwinBackend.repository;

import DigitalTwinBackend.entity.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorReadingRepository
        extends JpaRepository<SensorReading, Integer> {

    List<SensorReading> findByMachineId(Integer machineId);
}