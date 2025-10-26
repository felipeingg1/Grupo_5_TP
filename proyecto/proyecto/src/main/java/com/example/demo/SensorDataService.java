package com.example.demo;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SensorDataService {

    private final SensorDataDAO dao;
    private final List<SensorData> sensores;

    private final SensorAPIFachada fachada;

    public SensorDataService(SensorDataDAO dao, List<SensorData> sensores, SensorAPIFachada fachada) {
        this.dao = dao;
        this.sensores = sensores;
        this.fachada = fachada;
    }

    public HashMap<String, Object> getAll() {
        HashMap<String, Object> reporte = new HashMap<>();
        fachada.getTemp();
        Map<Integer, String> sensorIds = dao.getAll();

        int cantidad = 0;
        for (Map.Entry<Integer, String> entry : sensorIds.entrySet()) {
            Integer id = entry.getKey();
            String direccion = entry.getValue();
            if (!direccion.equals("0x0")) {
                String sensorKey = "address_sensor_" + id;
                String idKey = "id_sensor_" + id;
                reporte.put(sensorKey, direccion);
                reporte.put(idKey, id);
                cantidad++;
            }
        }
        reporte.put("cantidad", cantidad);
        return reporte;
    }

    public HashMap<String, Object> idData(int id) {
        HashMap<String, Object> reporte = new HashMap<>();
        if (id == 0) {
            reporte.put("error", "ID invalido");
        }

        fachada.getTemp();

        for (SensorData sensor : sensores) {
            if (sensor.getId() == id) {
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

        fachada.getTemp();

        Map<Integer, String> sensorData = dao.getAll();
        int cantidad = 0;
        for (Map.Entry<Integer, String> entry : sensorData.entrySet()) {
            Integer sensorId = entry.getKey();
            String address = entry.getValue();
            if (!address.equals("0x0")) {
                double[] valores = dao.getSensorReadings(sensorId);

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
                String nombre = "sensor_" + sensorId;
                report.put(nombre, estadistica);
                cantidad++;
            }
        }
        report.put("totalSensores", cantidad);
        return report;
    }

    public String setUmbrales(int id, double umbralAlto, double umbralBajo) {
        for (SensorData sensor : sensores) {
            if (sensor.getId() == id) {
                sensor.setUmbrales(umbralAlto, umbralBajo);
                return "Umbrales actualizados para el sensor con ID " + id;
            }
        }
        return "Sensor con ID " + id + " no encontrado.";
    }

    public String setID_state(String addres, int id) {
        for (SensorData sensor : sensores) {
            if (sensor.getId() == id) {
                return "El sensor " + sensor.getAddress() + " ya tiene el id " + id;
            }
        }
        for (SensorData sensor : sensores) {
            if (sensor.getAddress().equals(addres)) {
                String response = fachada.saveId();
                if (!response.equals("Almacenado con exito")) {
                    sensor.setId(id);
                    dao.guardarId(id, addres);
                }
                return response;
            }
        }

        return "Sensor con direccion " + addres + " no encontrado.";
    }

}
