package com.example.demo;

import java.util.*;

public class SensorDataDAOImpl implements SensorDataDAO {

    private static SensorDataDAOImpl instance;

    private final Map<Integer, double[]> lecturas = new HashMap<>();

    private SensorDataDAOImpl() {
        
    }

    public static synchronized SensorDataDAOImpl getInstance() {
        if (instance == null) {
            instance = new SensorDataDAOImpl();
        }
        return instance;
    }
    
    @Override
    public void guardar(Integer sensorId, double valor) {
        double[] lecturasActuales = lecturas.get(sensorId);
        
        if (lecturasActuales == null) {
            lecturasActuales = new double[]{valor};
        } else {
            double[] nuevasLecturas = new double[lecturasActuales.length + 1];
            System.arraycopy(lecturasActuales, 0, nuevasLecturas, 0, lecturasActuales.length);
            nuevasLecturas[lecturasActuales.length] = valor;
            
            lecturasActuales = nuevasLecturas;
        }
    
        lecturas.put(sensorId, lecturasActuales);
    }
    
    @Override
    public double[] getSensorReadings(int id) {
        return lecturas.get(id);
    }
    
    @Override
    public List<Integer> getAll() {
        return new ArrayList<>(lecturas.keySet());
    }
         
}
