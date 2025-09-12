package ar.edu.unlpam.ing.TP;

public class CatFact {
    private String fact;
    private int length;
    private boolean esLargo;

    public CatFact(String fact, int length, boolean esLargo) {
        this.fact = fact;
        this.length = length;
        this.esLargo = esLargo;
    }

    public String getFact() {
        return fact;
    }
    public int getLength() {
        return length;
    }

    public boolean getesLargo() {
        return esLargo;
    }
}
