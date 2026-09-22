package DigitalTwinBackend.repository;

import DigitalTwinBackend.entity.ProductionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductionRecordRepository
        extends JpaRepository<ProductionRecord, Integer> {

    List<ProductionRecord> findByMachineId(Integer machineId);
}