package co.edu.uniandes.mati.service.graphql;


import co.edu.uniandes.mati.entity.PreguntaResponse;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
@ApplicationScoped
public class RequestQuestion {

    @Inject
    PreguntaAPI preguntaAPI;

    @Query
    @Description("List data")
    @Blocking
    public PreguntaResponse getQuestion(@Name("tema") String tema) {
       return preguntaAPI.consumeGrapQL(tema);
    }

}
