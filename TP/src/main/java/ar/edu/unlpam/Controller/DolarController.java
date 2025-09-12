package ar.edu.unlpam.Controller;

import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlpam.ing.TP.Dolar;
import ar.edu.unlpam.ing.TP.DolarService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class DolarController {
    private final DolarService dolarService;

    public DolarController(DolarService dolarService) {     //inyeccion de dependencia
        this.dolarService = dolarService;
    }

    @GetMapping("/dolar")
    public Dolar getDolar() {
        return dolarService.obtenerCotizacion();
    }

    @GetMapping("/pesoadolar/{pesos}")
    public String convertir(@PathVariable double pesos) {
        return dolarService.convertirPesosADolares(pesos);
    }
} 

