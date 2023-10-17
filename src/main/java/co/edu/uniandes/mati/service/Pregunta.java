package co.edu.uniandes.mati.service;


import java.util.List;

@lombok.Data
public class Pregunta {
    private String nombre_pregunta;
    private List<Opcion> opciones;
}
