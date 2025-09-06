package ar.edu.unlpam.ing.TP;

public class Pelicula {
    private int id;
    private String titulo;
    private int anio;
    private String genero;

    public Pelicula(){}
    
    public Pelicula(int id, String titulo, int anio, String genero){
        this.id=id;
        this.titulo=titulo;
        this.anio=anio;
        this.genero=genero;
    }

    public int getId(){return id;}
    public String getTitulo(){return titulo;}
    public int getAnio(){return anio;}
    public String getGenero(){return genero;}

    public void setId(int id){this.id=id;}
    public void setTitulo(String titulo){this.titulo=titulo;}   
    public void setAnio(int anio){this.anio=anio;}
    public void setGenero(String genero){this.genero=genero;}
}
