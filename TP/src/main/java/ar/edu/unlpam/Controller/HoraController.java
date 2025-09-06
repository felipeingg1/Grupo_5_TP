package ar.edu.unlpam.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ar.edu.unlpam.ing.TP.Hora;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;



@RestController
public class HoraController {

    @GetMapping("/hora")
    public Hora convertirHorario(@RequestParam String fecha, @RequestParam String origen, @RequestParam String destino) {

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm[:ss]"); //formato de fecha y hora sin segundos
            LocalDateTime fechaHora = LocalDateTime.parse(fecha, formato);  //parseo la fecha
            
            ZonedDateTime horaOrigen = fechaHora.atZone(ZoneId.of(origen.trim())); //le asigno zona horaria origen
            ZonedDateTime horaDestino = horaOrigen.withZoneSameInstant(ZoneId.of(destino.trim())); //convierto a zona horaria destino

            return new Hora(horaOrigen.toLocalDateTime().toString(), horaDestino.toLocalDateTime().toString());


    }
}