package co.edu.uniandes.mati.entity;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class PreguntaResponse {
    private List<Pregunta> getPreguntas;
}
