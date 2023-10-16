package co.edu.uniandes.mati.service.graphql;

import co.edu.uniandes.mati.entity.Pregunta;
import co.edu.uniandes.mati.entity.PreguntaAPI;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/")
public class RequestQuestion {

    @Inject
    PreguntaAPI preguntaAPI;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Blocking
    public List<Pregunta> getQuestion() {
        return preguntaAPI.getProof("Java").getGetPreguntas();
    }

}
