package com.example.demo;

import java.util.List;
import java.util.Map;

public interface SensorDataDAO {
    public void guardar(Integer sensorId, double valor, String address);

    public void guardarId(Integer sensorId, String address);

    double[] getSensorReadings(int id);

    public Map<Integer, String> getAll();

}
