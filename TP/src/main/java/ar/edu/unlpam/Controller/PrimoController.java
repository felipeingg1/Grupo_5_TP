package ar.edu.unlpam.Controller;

import org.springframework.web.bind.annotation.RestController;
import ar.edu.unlpam.ing.TP.Primo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class PrimoController {

@GetMapping("/esPrimo/{numero}")
    public Map<String, Object> Primo(@PathVariable int numero){
        Primo p = new Primo();
        boolean flag = p.esPrimo(numero);

        Map<String, Object> response = new HashMap<>();
        response.put("numero", numero);
        response.put("esPrimo", flag);
        
        return response;
    }

}
