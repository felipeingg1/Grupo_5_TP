package ar.edu.unlpam.ing.TP;

public class Estadisticas {
    private double[] numeros;
    
    public double[] getNumeros() { return numeros;}

    public double promedio(double[] numeros){
        double suma=0;
        for(int i=0; i<numeros.length; i++){
            suma+=numeros[i];
        }
        return (suma)/(numeros.length);
    }

    public double max(double[] numeros){
        double max=numeros[0];
        for(int i=1; i<numeros.length; i++){
            if(max<numeros[i]){
                max=numeros[i];
            }
        }
        return max;
    }

    public double min(double[] numeros){
        double min=numeros[0];
        for(int i=1; i<numeros.length; i++){
            if(numeros[i]<min){
                min=numeros[i];
            }
        }

        return min;
    }


    public int cantidad(double[] numeros){
        return numeros.length;
    }


}
