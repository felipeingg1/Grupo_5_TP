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
    public void setId(int id) {
        this.id = id;
    }
    public void setValue(double value) {
        this.value = value;
    }
    public double getValue() {
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
