package DigitalTwinBackend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sensor_readings")
public class SensorReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_id")
    private Integer readingId;

    @Column(name = "machine_id", nullable = false)
    private Integer machineId;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "vibration")
    private Double vibration;

    @Column(name = "speed_rpm")
    private Integer speedRpm;

    @Column(name = "energy_consumption")
    private Double energyConsumption;

    @Column(name = "reading_time")
    private LocalDateTime readingTime;

    public SensorReading() {
    }

    public Integer getReadingId() {
        return readingId;
    }

    public void setReadingId(Integer readingId) {
        this.readingId = readingId;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
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

    public LocalDateTime getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(LocalDateTime readingTime) {
        this.readingTime = readingTime;
    }
}
