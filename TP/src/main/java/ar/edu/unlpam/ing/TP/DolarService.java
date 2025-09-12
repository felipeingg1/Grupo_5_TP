package ar.edu.unlpam.ing.TP;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DolarService {

    private final String URL = "https://dolarapi.com/v1/dolares/oficial";

    public Dolar obtenerCotizacion() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(URL, Dolar.class); //llama a la API y mapea la respuesta a un objeto Dolar
    }

    public String convertirPesosADolares(double pesos) {
        Dolar dolar = obtenerCotizacion();
        String dolares= String.format("%.2f", pesos / dolar.getVenta());
       return dolares; 
    }
}