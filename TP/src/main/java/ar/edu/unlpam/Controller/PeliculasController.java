package ar.edu.unlpam.Controller;
import ar.edu.unlpam.ing.TP.Pelicula;


import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class PeliculasController {
    private ArrayList<Pelicula> Repositorio = new ArrayList<>();

    @GetMapping("/peliculas")
    public ArrayList<Pelicula> peliculas(){
        return Repositorio;
    }

    @GetMapping("/peliculas/buscar")
    public Pelicula peliculaNombre(@RequestParam String titulo){
        Pelicula found= null;
        for(Pelicula p: Repositorio){
            if(p.getTitulo().trim().equalsIgnoreCase(titulo.trim())){ //.trim() para evitar espacios en blanco
                                                                //.equalsIgnoreCase() para evitar mayusculas/minusculas
                found=p;   
            }
        }
        return found; 
    }

    @PostMapping("/peliculas")
    public ArrayList<Pelicula> addPelicula(@RequestBody Pelicula pelicula){
        Pelicula newPeli= new Pelicula(pelicula.getId(), pelicula.getTitulo(), pelicula.getAnio(), pelicula.getGenero());
        Repositorio.add(newPeli);
        return Repositorio;
    }


    @DeleteMapping("/peliculas/{id}")
    public ArrayList<Pelicula> delPelicula(@PathVariable int id) {
        for(Pelicula p: Repositorio){
            if(p.getId()==id){
                Repositorio.remove(p);
            }
        }
        return Repositorio;
    }



}
