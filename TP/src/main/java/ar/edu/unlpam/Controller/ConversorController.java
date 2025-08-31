package ar.edu.unlpam.Controller;

import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlpam.ing.TP.Conversor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class ConversorController {

    @GetMapping("/CTF/{C}")
    public Map<String, Double> CTF(@PathVariable double C){
        Conversor c = new Conversor();
        double fahrenheit = c.CTF(C);

        Map<String, Double> response = new HashMap<>();
        response.put("celsius", C);
        response.put("fahrenheit", fahrenheit);
        
        return response;
    }

    @GetMapping("/FTC/{F}")
    public Map<String, Double> FTC(@PathVariable double F) {
        Conversor c = new Conversor();
        double celsius = c.FTC(F);

        Map<String, Double> response = new HashMap<>();
        response.put("fahrenheit", F);
        response.put("celsius", celsius);

        return response;
    }

    

}
