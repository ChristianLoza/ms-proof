package co.edu.uniandes.mati.entity;

import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

@GraphQLClientApi(configKey = "questions")
public interface PreguntaAPI {
	@Query
	PreguntaResponse consumeGrapQL(@Name("tema") String tema);
}
