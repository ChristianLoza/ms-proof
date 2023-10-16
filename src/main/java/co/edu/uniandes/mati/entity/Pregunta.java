package co.edu.uniandes.mati.entity;

import lombok.Data;

import java.util.List;

@Data
public class Pregunta {
    private String nombre_pregunta;
    private String tema;
    private List<Opcion> opciones;
}
