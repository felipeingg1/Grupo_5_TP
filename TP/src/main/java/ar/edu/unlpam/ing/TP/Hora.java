package ar.edu.unlpam.ing.TP;

public class Hora {

    private String origen;
    private String destino;
    
    public Hora() {}
    
    public Hora(String origen, String destino) {
        this.origen = origen;
        this.destino = destino;
    }
    
    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }
    
    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    
}
