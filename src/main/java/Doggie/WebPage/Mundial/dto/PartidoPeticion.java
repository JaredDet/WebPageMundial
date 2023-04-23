package Doggie.WebPage.Mundial.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;


public record PartidoPeticion(EquipoPeticion equipoLocal, EquipoPeticion equipoVisita, String arbitro,
                              String estadio,
                              @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") Date fecha,
                              @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "GMT-5")
                              Date hora,
                              String fase, boolean tandaPenales) {
}
