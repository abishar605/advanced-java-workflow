package DigitalTwinBackend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "production_data")
public class ProductionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "production_id")
    private Long productionId;

    @Column(name = "machine_id", nullable = false)
    private Integer machineId;

    @Column(name = "units_produced")
    private Integer unitsProduced;

    @Column(name = "defective_units")
    private Integer defectiveUnits;

    @Column(name = "production_rate")
    private Double productionRate;

    @Column(name = "production_time")
    private LocalDateTime productionTime;

    public ProductionRecord() {
    }

    public Long getProductionId() {
        return productionId;
    }

    public void setProductionId(Long productionId) {
        this.productionId = productionId;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public Integer getUnitsProduced() {
        return unitsProduced;
    }

    public void setUnitsProduced(Integer unitsProduced) {
        this.unitsProduced = unitsProduced;
    }

    public Integer getDefectiveUnits() {
        return defectiveUnits;
    }

    public void setDefectiveUnits(Integer defectiveUnits) {
        this.defectiveUnits = defectiveUnits;
    }

    public Double getProductionRate() {
        return productionRate;
    }

    public void setProductionRate(Double productionRate) {
        this.productionRate = productionRate;
    }

    public LocalDateTime getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(LocalDateTime productionTime) {
        this.productionTime = productionTime;
    }
}