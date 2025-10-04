package com.example.demo;

public class SensorData {
    
    private int id;
    private double value;
    private double umbralAlto;
    private double umbralBajo;

    public SensorData() {
    }
    
    public SensorData(int id) {
        this.id = id;
        umbralAlto = 75.0;   
        umbralBajo = 25.0;
    }
    
    public int getId() {
        return id;
    }
    
    public double getValue() {
    
        java.util.Random random = new java.util.Random();
        value = random.nextDouble() * 100;
        return value;
    }
    public String getAlerta(){
        if (value > umbralAlto)
            return "ALTO";
        else if (value < umbralBajo)
            return "BAJO";
        else 
            return "OK";
    }
}
