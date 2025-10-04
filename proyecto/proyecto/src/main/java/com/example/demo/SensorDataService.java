package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SensorDataService {

    private final SensorDataDAO dao;
    private final List<SensorData> sensores;

    public SensorDataService(SensorDataDAO dao, List<SensorData> sensores) { //inyeccion de los sesores creados y el dao
        this.dao = dao;
        this.sensores = sensores;
        
    }

    public List<Integer> getAll() {
        return dao.getAll();
    }

    public HashMap<String, Object> idData(int id) {
        HashMap<String, Object> reporte = new HashMap<>();
        for(SensorData sensor : sensores) {
            if(sensor.getId() == id) {
                dao.guardar(sensor.getId(), sensor.getValue());
                reporte.put("id", sensor.getId());
                reporte.put("valor", sensor.getValue());
                reporte.put("alerta", sensor.getAlerta());
                return reporte;
            }
        }
        reporte.put("error", "Sensor no encontrado");
        
        return reporte;
    }

    public HashMap<String, Object> historicReport() {
        
        HashMap<String, Object> report = new HashMap<>();
        
        if (sensores == null || sensores.isEmpty()) {
            report.put("error", "No hay sensores disponibles para generar el reporte");
            return report;
        }

        for (SensorData sensor : sensores) {
            dao.guardar(sensor.getId(), sensor.getValue());
            double[] valores = dao.getSensorReadings(sensor.getId());

            double promedio = 0;
            double max = valores[0];
            double min = valores[0];

            for (double val : valores) {
                if (val > max)
                    max = val;
                if (val < min)
                    min = val;
                promedio += val;
            }
            promedio = promedio / valores.length;

            HashMap<String, Object> estadistica = new HashMap<>();
            estadistica.put("promedio", promedio);
            estadistica.put("maximo", max);
            estadistica.put("minimo", min);
            String nombre = "sensor_" + sensor.getId();
            report.put(nombre, estadistica);

        }
        report.put("totalSensores", sensores.size());

        return report;
    }

}