package com.example.demo;

import java.util.*;

public class SensorDataDAOImpl implements SensorDataDAO {

    private static SensorDataDAOImpl instance;

    private final Map<Integer, double[]> lecturas = new HashMap<>();
    private final Map<Integer, String> direcciones = new HashMap<>();

    private SensorDataDAOImpl() {

    }

    public static synchronized SensorDataDAOImpl getInstance() {
        if (instance == null) {
            instance = new SensorDataDAOImpl();
        }
        return instance;
    }

    @Override
    public void guardar(Integer sensorId, double valor, String address) {
        double[] lecturasActuales = lecturas.get(sensorId);

        if (lecturasActuales == null) {
            lecturasActuales = new double[] { valor };
        } else {
            double[] nuevasLecturas = new double[lecturasActuales.length + 1];
            System.arraycopy(lecturasActuales, 0, nuevasLecturas, 0, lecturasActuales.length);
            nuevasLecturas[lecturasActuales.length] = valor;

            lecturasActuales = nuevasLecturas;
        }
        direcciones.put(sensorId, address);
        lecturas.put(sensorId, lecturasActuales);
    }

    @Override
    public void guardarId(Integer sensorId, String address) {
        direcciones.put(sensorId, address);

    }

    @Override
    public double[] getSensorReadings(int id) {
        return lecturas.get(id);
    }

    @Override
    public Map<Integer, String> getAll() {
        return direcciones;
    }

}
