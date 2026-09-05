package DigitalTwinBackend.dto;

public class MachineDashboardDTO {

    private Integer machineId;
    private String machineCode;
    private String machineName;
    private String machineType;
    private String location;
    private String operationalStatus;

    private String status;
    private Double healthScore;
    private Boolean anomalyDetected;

    private Double temperature;
    private Double vibration;
    private Integer speedRpm;
    private Double energyConsumption;

    private Integer unitsProduced;
    private Integer defectiveUnits;
    private Double productionRate;

    private String maintenanceType;
    private String maintenanceDescription;
    private String technician;

    public MachineDashboardDTO() {
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOperationalStatus() {
        return operationalStatus;
    }

    public void setOperationalStatus(String operationalStatus) {
        this.operationalStatus = operationalStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getHealthScore() {
        return healthScore;
    }

    public void setHealthScore(Double healthScore) {
        this.healthScore = healthScore;
    }

    public Boolean getAnomalyDetected() {
        return anomalyDetected;
    }

    public void setAnomalyDetected(Boolean anomalyDetected) {
        this.anomalyDetected = anomalyDetected;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getVibration() {
        return vibration;
    }

    public void setVibration(Double vibration) {
        this.vibration = vibration;
    }

    public Integer getSpeedRpm() {
        return speedRpm;
    }

    public void setSpeedRpm(Integer speedRpm) {
        this.speedRpm = speedRpm;
    }

    public Double getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(Double energyConsumption) {
        this.energyConsumption = energyConsumption;
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

    public String getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(String maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public String getMaintenanceDescription() {
        return maintenanceDescription;
    }

    public void setMaintenanceDescription(String maintenanceDescription) {
        this.maintenanceDescription = maintenanceDescription;
    }

    public String getTechnician() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
    }
}