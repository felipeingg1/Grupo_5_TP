package ar.edu.unlpam.Controller;

import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlpam.ing.TP.CatFactService;
import ar.edu.unlpam.ing.TP.CatFact;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class CatController {
    private final CatFactService catFactService ;

    public CatController(CatFactService catFactService) {
        this.catFactService = catFactService;
    }

    @GetMapping("/catfact")
    public CatFact getCatFact() {
        return catFactService.obtenerFactTransformado();
    }
}
