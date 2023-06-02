package Doggie.WebPage.Mundial.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record DatosExtrasPartido(@JsonFormat(pattern = "d 'de' MMMM 'de' yyyy") Date fecha, Date hora, String estadio,
                                 DatosArbitro arbitro, String fase) {

}
