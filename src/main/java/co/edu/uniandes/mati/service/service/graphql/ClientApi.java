package co.edu.uniandes.mati.service.service.graphql;

import co.edu.uniandes.mati.service.domain.vo.Pregunta;
import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;

@GraphQLClientApi(configKey = "questions")
public interface ClientApi {
    @Query("getPreguntas")
    List<Pregunta> getQuery(@Name("tema") String tema);
}
