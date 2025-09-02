package ar.edu.unlpam.ing.TP;

public class CadenaInvertida {

public String invertir(String cadena){

    String invertida= "";
    for(int i=cadena.length()-1;i>=0;i--){
        invertida+=cadena.charAt(i);
    }
    return invertida;
}

}
