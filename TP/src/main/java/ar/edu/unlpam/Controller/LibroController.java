package ar.edu.unlpam.Controller;
import ar.edu.unlpam.ing.TP.Libro;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class LibroController {

    private ArrayList<Libro> libros = new ArrayList<>();

    @GetMapping("/libros")
    public ArrayList<Libro> libros(){
        return libros;
    }

    @PostMapping("/libros")
    public ArrayList<Libro> addLibro(@RequestBody Libro libro){
        Libro nuevoLibro= new Libro(libro.getId(), libro.getTitulo(), libro.getAutor(), libro.getAnioPublicacion());
        libros.add(nuevoLibro);
        return libros;
    }

    @PutMapping("/libros/{id}")
    public Libro foundLibro(@PathVariable int id, @RequestBody Libro request) {
    
        for (Libro n : libros) {
        if (n.getId() == id) {
            return n;
            }
        }
        return null;
    }

    @DeleteMapping("/libros/{id}")
    public ArrayList<Libro> delLibro(@PathVariable int id) {
        for (Libro n : libros) {
        if (n.getId() == id) {
            libros.remove(n);
            return libros;
            }
        }
        return null;
    }

}

