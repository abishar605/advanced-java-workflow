package DigitalTwinBackend.repository;

import DigitalTwinBackend.entity.MachineStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MachineStatusRepository
        extends JpaRepository<MachineStatus, Integer> {

    List<MachineStatus> findByMachineId(Integer machineId);
}