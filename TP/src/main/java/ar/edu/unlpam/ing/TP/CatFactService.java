package ar.edu.unlpam.ing.TP;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CatFactService {

    private final String URL = "https://catfact.ninja/fact";
    

    public CatFact obtenerFactTransformado() {

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(URL, Map.class);

        String fact = (String) response.get("fact");
        int length = fact.length();
        boolean esLargo = length > 80;

        // lo retornamos con lógica añadida
        return new CatFact(fact, length, esLargo);
    }
}
