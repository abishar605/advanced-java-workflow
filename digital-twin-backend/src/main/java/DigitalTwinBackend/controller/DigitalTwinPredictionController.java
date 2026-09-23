package DigitalTwinBackend.controller;

import DigitalTwinBackend.service.DigitalTwinPredictionService;
import DigitalTwinBackend.service.DigitalTwinPredictionService.DigitalTwinPrediction;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/digital-twin")
@CrossOrigin(origins = "*")
public class DigitalTwinPredictionController {

    private final DigitalTwinPredictionService digitalTwinPredictionService;

    public DigitalTwinPredictionController(
            DigitalTwinPredictionService digitalTwinPredictionService) {

        this.digitalTwinPredictionService =
                digitalTwinPredictionService;
    }

    @GetMapping("/predict/{machineCode}")
    public DigitalTwinPrediction predictMachine(
            @PathVariable String machineCode) {

        return digitalTwinPredictionService
                .evaluateMachine(machineCode);
    }
}
