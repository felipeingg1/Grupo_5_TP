package com.example.demo;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;

@Service
public class SensorDataService {
    
    private final SensorDataDAO dao;
    private final List<SensorData> sensores;
    private String token;

    public SensorDataService(SensorDataDAO dao, List<SensorData> sensores) {
        this.dao = dao;
        this.sensores = sensores;
        Login();
    }

    public HashMap<String, Object> getAll() {
        HashMap<String, Object> reporte = new HashMap<>();
        
        List<Integer> sensorIds = dao.getAll();
        int cantidad = sensorIds.size();
        
        reporte.put("cantidad", cantidad);
        
        for (int i = 0; i < sensorIds.size(); i++) {
            String sensorKey = "sensor" + (i + 1);
            reporte.put(sensorKey, sensorIds.get(i));
        }
        
        return reporte;
    }

    public HashMap<String, Object> idData(int id) {
        HashMap<String, Object> reporte = new HashMap<>();
        if(id == 0){
            reporte.put("error", "ID invalido");
        }
        
        getTemp();

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
        
        getTemp();
        
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
    /*
     * en esta funcion hacemos el login y obtenemos el token
     */
    public String Login() {
        try {
            String url = "http://192.168.1.5/checkLogin";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", "user");
            jsonBody.put("password", "27248");

            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody.toString(), StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());
                this.token = jsonResponse.getString("token");
                return this.token;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /*
     * en esta funcion obtenemos la temperatura de los sensores y enviamos el token 
     * para autenticarnos
     */
    public void getTemp() {
        if (this.token == null || this.token.isEmpty()) {
            Login();
        }

        try {
            String url = "http://192.168.1.5/getTemp";

            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();

            String cookieHeader = "Cookie=" + this.token;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .header("Cookie", cookieHeader)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());

                int contador = 0;
                for (SensorData sensor : sensores) {
                    int id = jsonResponse.optInt("n" + (contador + 1), -1);
                    double temp = jsonResponse.optDouble("t" + (contador + 1), Double.NaN);

                    sensor.setId(id);
                    sensor.setValue(temp);
                    contador++;
                }
            } 
        } catch (Exception e) {
            e.printStackTrace();
            Login();
        }
    }

}
