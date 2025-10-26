package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public SensorDataDAO sensorDataDAO() {
        return SensorDataDAOImpl.getInstance();
    }

    @Bean
    public List<SensorData> sensores(SensorDataDAO dao) {
        List<SensorData> sensores = new ArrayList<>();

        sensores.add(new SensorData(0));
        sensores.add(new SensorData(0));
        sensores.add(new SensorData(0));
        return sensores;
    }

    @Bean
    public SensorAPIFachada sensorAPIFachada(SensorDataDAO dao, List<SensorData> sensores) {
        return new SensorAPIFachada(dao, sensores);
    }

}