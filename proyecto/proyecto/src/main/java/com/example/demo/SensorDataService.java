package com.example.demo;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SensorDataService {
    private static final Logger registralog = LoggerFactory.getLogger(SensorDataService.class);

    private final SensorDataDAO dao;
    private final List<SensorData> sensores;

    private final SensorAPIFachada fachada;

    public SensorDataService(SensorDataDAO dao, List<SensorData> sensores, SensorAPIFachada fachada) {
        this.dao = dao;
        this.sensores = sensores;
        this.fachada = fachada;
    }

    public HashMap<String, Object> getAll() {
        registralog.info("Obteniendo datos de todos los sensores");
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
        registralog.debug("Total de sensores activos: {}", cantidad);
        return reporte;
    }

    public HashMap<String, Object> idData(int id) {
        registralog.info("Consultando datos del sensor ID: {}", id);
        HashMap<String, Object> reporte = new HashMap<>();
        if (id == 0) {
            registralog.warn("ID inválido recibido: {}", id);
            reporte.put("error", "ID invalido");
            return reporte;
        }

        fachada.getTemp();

        for (SensorData sensor : sensores) {
            if (sensor.getId() == id) {
                registralog.debug("Sensor encontrado - ID: {}, Valor: {}, Alerta: {}",
                        sensor.getId(), sensor.getValue(), sensor.getAlerta());
                reporte.put("id", sensor.getId());
                reporte.put("valor", sensor.getValue());
                reporte.put("alerta", sensor.getAlerta());
                return reporte;
            }
        }
        registralog.warn("Sensor no encontrado con ID: {}", id);
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
