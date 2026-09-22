package DigitalTwinBackend.controller;

import DigitalTwinBackend.entity.ProductionRecord;
import DigitalTwinBackend.service.ProductionRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/production")
@CrossOrigin(origins = "*")
public class ProductionRecordController {

    private final ProductionRecordService productionRecordService;

    public ProductionRecordController(
            ProductionRecordService productionRecordService) {
        this.productionRecordService = productionRecordService;
    }

    // Get all production records
    @GetMapping
    public List<ProductionRecord> getAllProductionRecords() {
        return productionRecordService.getAllProductionRecords();
    }

    // Get production record by ID
    @GetMapping("/{id}")
    public Optional<ProductionRecord> getProductionRecordById(
            @PathVariable Integer id) {
        return productionRecordService.getProductionRecordById(id);
    }

    // Get production records for a particular machine
    @GetMapping("/machine/{machineId}")
    public List<ProductionRecord> getProductionRecordsByMachineId(
            @PathVariable Integer machineId) {
        return productionRecordService
                .getProductionRecordsByMachineId(machineId);
    }

    // Add a new production record
    @PostMapping
    public ProductionRecord addProductionRecord(
            @RequestBody ProductionRecord productionRecord) {
        return productionRecordService
                .addProductionRecord(productionRecord);
    }

    // Delete a production record
    @DeleteMapping("/{id}")
    public void deleteProductionRecord(@PathVariable Integer id) {
        productionRecordService.deleteProductionRecord(id);
    }
}