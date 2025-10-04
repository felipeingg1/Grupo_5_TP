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
        
        sensores.add(new SensorData(1));
        dao.guardar(1, 50);
        
        sensores.add(new SensorData(2));
        dao.guardar(2, 50);
        
        sensores.add(new SensorData(3));
        dao.guardar(3, 50);
        
        sensores.add(new SensorData(4));
        dao.guardar(4, 50);
        
        sensores.add(new SensorData(5));
        dao.guardar(5, 50);
        
        return sensores;
    }
    
}