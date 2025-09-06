package ar.edu.unlpam.ing.TP;

public class Nota {
    private int ID;
    private String titulo;
    private String contenido;
    private String fechadeCreacion;
    

    public Nota(int ID, String titulo, String contenido, String fechadeCreacion) {
        this.ID = ID;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fechadeCreacion = fechadeCreacion;
    }

    public String getTitulo(){
        return titulo;
    }
    public String getContenido(){
        return contenido;
    }
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    public void setContenido(String contenido){
        this.contenido = contenido;
    }
    public String getFechadeCreacion(){
        return fechadeCreacion;
    }
    public int getID(){
        return ID;
    }


}
