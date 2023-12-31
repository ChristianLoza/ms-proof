package co.edu.uniandes.mati.service.service.graphql;

import java.util.Date;
import java.util.List;
import java.util.Random;

import co.edu.uniandes.mati.service.domain.vo.Alert;
import co.edu.uniandes.mati.service.domain.vo.Payment;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.uniandes.mati.service.domain.entity.Input;
import co.edu.uniandes.mati.service.domain.entity.PaymentCheck;
import co.edu.uniandes.mati.service.domain.vo.GeneratePayment;
import co.edu.uniandes.mati.service.domain.vo.MailAlert;
import co.edu.uniandes.mati.service.domain.vo.Pregunta;
import co.edu.uniandes.mati.service.infrastructure.repository.InputRepository;
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

	@Channel("TOPIC_BLOCKCHAIN")
	Emitter<String> emitterBlockChain;

	@Channel("TOPIC_PROOF_LIST")
	Emitter<String> emitterList;
	@Inject
	ObjectMapper objectMapper;
	Input result = new Input();
	@SneakyThrows
	@Query
	@Description("Generate new proof")
	public Input generateTest(Input input) {
		input.setAmountOfQuestion(getListQuestionFromApi(input.getTechnology()).size());
		input.setDate(new Date());
		result = inputRepository.save(input);
		log.info("### Created test" + result);
		GeneratePayment generatePayment = new GeneratePayment();
		generatePayment.setEmail(result.getRequestdBy());
		generatePayment.setIdTest(result.getId());
		sendKafka(generatePayment);
		emitterList.send(objectMapper.writeValueAsString(inputRepository.findAll()));
		return result;
	}

	@Query
	@Description("Get All Questions from GrahpQL Service")
	public List<Pregunta> getAllQuestionByItem(String item) {
		return getListQuestionFromApi(item);
	}


	@SneakyThrows
	@Incoming("uniandes-payment")
	public void consumeKafkaPayment(String paymentCheck) {
		log.info("### consumeKafkaPayment" + paymentCheck);
		PaymentCheck getPaymentCheck = objectMapper.readValue(paymentCheck, PaymentCheck.class);
		if (!getPaymentCheck.getStatus().equals(null) || getPaymentCheck.getStatus().equals("")) {
			Integer randomCalification = new Random().nextInt(100);
			MailAlert mailAlert = new MailAlert(result.getRequestType(), getPaymentCheck.getEmail(),
					randomCalification);
			Alert alert = new Alert(getPaymentCheck.getEmail(),randomCalification);

			emitterMail.send(objectMapper.writeValueAsString(mailAlert));
			emitterBlockChain.send(objectMapper.writeValueAsString(alert));
		}
	}


	@SneakyThrows
	@Incoming("uniandes-post-payment")
	public void consumeKafkaPaymentPostPayment(String paymentCheck) {
		log.info("### postpayment " + paymentCheck);
		Payment getPaymentCheck = objectMapper.readValue(paymentCheck, Payment.class);

		Integer randomCalification = new Random().nextInt(100);

		MailAlert mailAlert = new MailAlert(result.getRequestType(), getPaymentCheck.getUser().getEmail(),
				randomCalification);
		Alert alert = new Alert(getPaymentCheck.getUser().getEmail(),randomCalification);

		emitterMail.send(objectMapper.writeValueAsString(mailAlert));
		emitterBlockChain.send(objectMapper.writeValueAsString(alert));

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
