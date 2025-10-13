package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class SensorDataController {

    private final SensorDataService sensorDataService;

    public SensorDataController(SensorDataService sensorDataService) {
        this.sensorDataService = sensorDataService;
    }

    @GetMapping("/sensors/all")
    public HashMap<String, Object> getAllSensors() {
        return sensorDataService.getAll();
    }

    @GetMapping("/sensors/{id}")
    public HashMap<String, Object> getSensor(@PathVariable int id) {
        return sensorDataService.idData(id);
    }

    @GetMapping("/sensors/historic-report")
    public HashMap<String, Object> getHistoricReport() {
        return sensorDataService.historicReport();
    }
}
