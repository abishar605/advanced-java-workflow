package DigitalTwinBackend.repository;

import DigitalTwinBackend.entity.Machine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Integer> {

    Optional<Machine> findByMachineCode(String machineCode);

}
