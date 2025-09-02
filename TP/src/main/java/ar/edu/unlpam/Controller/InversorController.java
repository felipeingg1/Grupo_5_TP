package ar.edu.unlpam.Controller;

import ar.edu.unlpam.ing.TP.CadenaInvertida;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class InversorController {

    @GetMapping("/invertir/{cadena}")
    public Map<String, String> CTF(@PathVariable String cadena){
        CadenaInvertida x = new CadenaInvertida();
        String invertida = x.invertir(cadena);

        Map<String, String> response = new HashMap<>();
        response.put("original", cadena);
        response.put("invertida", invertida);
        
        return response;
    }
}
