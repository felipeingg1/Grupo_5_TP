package ar.edu.unlpam.ing.TP;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ConversorController {

    @GetMapping("/CTF")
    public double CTF(@RequestParam double C){
        Conversor c = new Conversor();
        return c.CTF(C);
    }

    @GetMapping("/FTC")
    public double FTC(@RequestParam double F) {
        Conversor c = new Conversor();
        return c.FTC(F);
    }

    

}
