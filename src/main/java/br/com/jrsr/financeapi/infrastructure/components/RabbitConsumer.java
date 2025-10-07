package br.com.jrsr.financeapi.infrastructure.components;

import br.com.jrsr.financeapi.application.dto.request.FinanceAnalysisRequest;
import br.com.jrsr.financeapi.domain.collections.Analysis;
import br.com.jrsr.financeapi.domain.entities.Transaction;
import br.com.jrsr.financeapi.infrastructure.repositories.AnalysisRepository;
import br.com.jrsr.financeapi.infrastructure.repositories.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class RabbitConsumer {

    private final OpenAIComponent openAIComponent;

    private final ObjectMapper mapper;

    private final TransactionRepository transactionRepository;

    private final AnalysisRepository analysisRepository;

    private final JavaMailSender mailSender;

    @RabbitListener(queues = "finance.queue")
    public void receive(@Payload String message) {

        try {
            // Desserializa a mensagem JSON para o objeto FinanceAnalysisRequest
            FinanceAnalysisRequest request = mapper.readValue(message, FinanceAnalysisRequest.class);

            List<Transaction> transactions = transactionRepository.findAll();

            String jsonTransactions = mapper.writeValueAsString(transactions);

            String prompt = "You specialize in performing financial analyses. Below is a JSON file containing account transactions obtained from the system. Perform the analysis as requested (do not generate responses related to anything other than the financial analysis).";

            String text = prompt + "\n" + jsonTransactions + "\n" + request.analysisQuestion();

            String answer = openAIComponent.send(text);

            Analysis analysis = new Analysis();
            analysis.setId(UUID.randomUUID());
            analysis.setDate(LocalDateTime.now());
            analysis.setQuestion(request.analysisQuestion());
            analysis.setAnswer(answer);

            analysisRepository.save(analysis);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("no-reply@jrsr.com.br");
            mailMessage.setTo(request.notificationEmail());
            mailMessage.setSubject("Financial Analysis generated on: " + LocalDateTime.now());
            mailMessage.setText(answer);

            mailSender.send(mailMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON message", e);
        }
    }
}
