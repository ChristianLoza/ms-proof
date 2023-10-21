package co.edu.uniandes.mati.service.service.graphql;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import io.smallrye.mutiny.Uni;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.uniandes.mati.service.domain.entity.Input;
import co.edu.uniandes.mati.service.domain.entity.PaymentCheck;
import co.edu.uniandes.mati.service.domain.vo.GeneratePayment;
import co.edu.uniandes.mati.service.domain.vo.MailAlert;
import co.edu.uniandes.mati.service.domain.vo.Pregunta;
import co.edu.uniandes.mati.service.infrastructure.repository.InputRepository;
import co.edu.uniandes.mati.service.infrastructure.repository.PaymentRepository;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.SneakyThrows;

@GraphQLApi
@ApplicationScoped
@Blocking
public class GraphController {


	@Inject
	Logger log;

	@Inject
	ClientApi clientApi;
	@Inject
	InputRepository inputRepository;
	@Channel("TOPIC_PROOF_GENERATE")
	Emitter<String> emitter;
	@Channel("TOPIC_MAIL")
	Emitter<String> emitterMail;
	@Inject
	ObjectMapper objectMapper;
	Input result = new Input();
	@Query
	public Input generateTest(Input input) {
		input.setAmountOfQuestion(getListQuestionFromApi(input.getTechnology()).size());
		input.setDate(new Date());
		result =  inputRepository.save(input);
		log.info("### Created test" + result);
		GeneratePayment generatePayment = new GeneratePayment();
		generatePayment.setEmail(result.getRequestdBy());
		generatePayment.setIdTest(result.getId());
		sendKafka(generatePayment);
		return result;
	}


	@SneakyThrows
	@Incoming("uniandes-payment")
	public void consumeKafkaPayment(String paymentCheck) {
		log.info("### consumeKafkaPayment" + paymentCheck);
		PaymentCheck getPaymentCheck = objectMapper.readValue(paymentCheck, PaymentCheck.class);
		if (!getPaymentCheck.getStatus().equals(null)) {
			MailAlert mailAlert = new MailAlert(result.getRequestType(),getPaymentCheck.getEmail(), new Random().nextInt(100));

			emitterMail.send(objectMapper.writeValueAsString(mailAlert));
		}
	}

	@SneakyThrows
	private void sendKafka(GeneratePayment generatePayment) {
		String jsonMessage = objectMapper.writeValueAsString(generatePayment);
		log.info("### Send Kafka Json" + jsonMessage);
		emitter.send(jsonMessage);
	}

	private List<Pregunta> getListQuestionFromApi(String item) {
		return clientApi.getQuery(item);
	}

}
