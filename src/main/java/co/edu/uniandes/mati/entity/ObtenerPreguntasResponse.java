package co.edu.uniandes.mati.entity;

import lombok.Data;

import java.util.List;

@Data
public class ObtenerPreguntasResponse {
    private List<Pregunta> obtenerPreguntas;
}
