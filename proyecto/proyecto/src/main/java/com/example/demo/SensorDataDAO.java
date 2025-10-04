package com.example.demo;

import java.util.List;

public interface SensorDataDAO {
    void guardar(Integer sensor, double valor);
    double[] getSensorReadings(int id);
    List<Integer> getAll();
}
