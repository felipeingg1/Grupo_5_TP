package ar.edu.unlpam.Controller;
import ar.edu.unlpam.ing.TP.Nota;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class NotaControllador {

    private ArrayList<Nota> notas = new ArrayList<>();

    @GetMapping("/notas")
    public ArrayList<Nota> notas(){
        return notas;
    }

    @PostMapping("/notas")
    public ArrayList<Nota> addNota(@RequestBody Nota nota){
        String titulo=nota.getTitulo();
        titulo= titulo.isEmpty() ? "Sin Titulo" : titulo;

        Nota nuevaNota = new Nota((notas.size()+1), titulo, nota.getContenido(), LocalDateTime.now().toString());
        notas.add(nuevaNota);
        return notas;
    }

    @PutMapping("/notas/{id}")
    public Nota updateNota(@PathVariable int id, @RequestBody Nota request) {
    
        for (Nota n : notas) {
        if (n.getID() == id) {
            if (request.getTitulo() != null) n.setTitulo(request.getTitulo());
            if (request.getContenido() != null) n.setContenido(request.getContenido());
            return n;
            }
        }
        return null;
    }

    @DeleteMapping("/notas/{id}")
    public ArrayList<Nota> delNota(@PathVariable int id) {
        notas.remove(notas.get(id-1));
        return notas;
    }

}