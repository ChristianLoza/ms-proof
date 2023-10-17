package co.edu.uniandes.mati.service;

import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;

@GraphQLClientApi(configKey = "questions")
public interface ClientApi {
    @Query("getPreguntas")
    List<Pregunta> getQuery(@Name("tema") String tema);
}
