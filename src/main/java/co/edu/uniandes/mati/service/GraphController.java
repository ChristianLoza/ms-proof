package co.edu.uniandes.mati.service;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;

import java.util.Date;
import java.util.List;

@GraphQLApi
public class GraphController {
	@Inject
	ClientApi clientApi;

	@Inject
	InputRepository inputRepository;

	@Query
	@Description("Generate Exam")
	@Blocking
	public String generateTest(Input input) {

		List<Pregunta> list = clientApi.getQuery(input.getTechnology());
		input.setAmountOfQuestion(list.size());
		input.setDate(new Date());
		inputRepository.save(input);

		return "Ok";
	}

}
