package DigitalTwinBackend.service;

import DigitalTwinBackend.entity.ProductionRecord;
import DigitalTwinBackend.repository.ProductionRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductionRecordService {

    private final ProductionRecordRepository productionRecordRepository;

    public ProductionRecordService(
            ProductionRecordRepository productionRecordRepository) {
        this.productionRecordRepository = productionRecordRepository;
    }

    // Get all production records
    public List<ProductionRecord> getAllProductionRecords() {
        return productionRecordRepository.findAll();
    }

    // Get production record by ID
    public Optional<ProductionRecord> getProductionRecordById(Integer id) {
        return productionRecordRepository.findById(id);
    }

    // Get production records for a particular machine
    public List<ProductionRecord> getProductionRecordsByMachineId(
            Integer machineId) {
        return productionRecordRepository.findByMachineId(machineId);
    }

    // Add a new production record
    public ProductionRecord addProductionRecord(
            ProductionRecord productionRecord) {
        return productionRecordRepository.save(productionRecord);
    }

    // Delete a production record
    public void deleteProductionRecord(Integer id) {

        if (!productionRecordRepository.existsById(id)) {
            throw new RuntimeException(
                    "Production record not found with ID: " + id
            );
        }

        productionRecordRepository.deleteById(id);
    }
}