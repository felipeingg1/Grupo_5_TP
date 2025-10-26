package com.example.demo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.json.JSONObject;

public class SensorAPIFachada {
    private String token;
    private final SensorDataDAO dao;
    private final List<SensorData> sensores;

    public SensorAPIFachada(SensorDataDAO dao, List<SensorData> sensores) {
        this.dao = dao;
        this.sensores = sensores;
        Login();
    }

    public void getTemp() {
        if (this.token == null || this.token.isEmpty()) {
            Login();
        }

        try {
            String url = "http://mofi4016.local/getTemp";

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
                    String addressHex = jsonResponse.optString("dir" + (contador + 1), "0x0");
                    int id = jsonResponse.optInt("n" + (contador + 1), -1);
                    double temp = jsonResponse.optDouble("t" + (contador + 1), Double.NaN);
                    sensor.setAddress(addressHex);
                    sensor.setId(id);
                    sensor.setValue(temp);
                    dao.guardar(id, temp, addressHex);
                    contador++;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Login();
        }
    }

    public String saveId() {
        try {
            String url = "http://mofi4016.local/saveconfiguracionDS18";

            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();

            JSONObject jsonBody = new JSONObject();
            for (int i = 0; i < sensores.size(); i++) {
                SensorData sensor = sensores.get(i);
                jsonBody.put("addr" + (i + 1), sensor.getId());
                jsonBody.put("sensor" + (i + 1), sensor.getAddress());
            }

            String cookieHeader = "Cookie=" + this.token;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .header("Accept", "application/json")
                    .header("Cookie", cookieHeader)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody.toString(), StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return "Almacenado con exito";
            } else {
                return "Error al almacenar";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al almacenar: " + e.getMessage();
        }
    }

    public String Login() {
        try {
            String url = "http://mofi4016.local/checkLogin";
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
}
