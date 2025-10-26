package com.example.demo;

public class SensorData {

    private int id;
    private double value;
    private double umbralAlto;
    private double umbralBajo;
    private String address;

    public SensorData() {
    }

    public SensorData(int id) {
        this.id = id;
        umbralAlto = 75.0;
        umbralBajo = 25.0;
    }

    public void setUmbrales(double umbralAlto, double umbralBajo) {
        this.umbralAlto = umbralAlto;
        this.umbralBajo = umbralBajo;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getAlerta() {
        if (value > umbralAlto)
            return "ALTO";
        else if (value < umbralBajo)
            return "BAJO";
        else
            return "OK";
    }
}
