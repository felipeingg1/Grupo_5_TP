package ar.edu.unlpam.Controller;

import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlpam.ing.TP.Estadisticas;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class EstadisticasController {

@PostMapping("/estadisticas")
public Map<String, Object> estadisticas(@RequestBody Estadisticas request){
    double[] cadena = request.getNumeros();
    Estadisticas e = new Estadisticas();
    
    double promedio = e.promedio(cadena);
    double max = e.max(cadena);
    double min = e.min(cadena);
    int cantidad = e.cantidad(cadena);
    
    Map<String, Object> resultado = new HashMap<>();
    resultado.put("promedio", promedio);
    resultado.put("maximo", max);
    resultado.put("minimo", min);
    resultado.put("cantidad", cantidad);
    
    return resultado;
}
}
