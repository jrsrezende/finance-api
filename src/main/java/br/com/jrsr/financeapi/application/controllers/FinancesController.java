package br.com.jrsr.financeapi.application.controllers;

import br.com.jrsr.financeapi.application.dto.request.FinanceAnalysisRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/finance")
public class FinancesController {


    private final ObjectMapper mapper;

    private final RabbitTemplate rabbitTemplate;

    public FinancesController(ObjectMapper mapper, RabbitTemplate rabbitTemplate) {
        this.mapper = mapper;
        this.rabbitTemplate = rabbitTemplate;
    }


    @PostMapping("analysis")
    public ResponseEntity<String> processAnalysis(@RequestBody @Valid FinanceAnalysisRequest request) throws Exception {

        var jsonRequest = mapper.writeValueAsString(request); // Convertendo o objeto request para JSON

        rabbitTemplate.convertAndSend("finance.queue", jsonRequest);// Enviando a mensagem para a fila "finance.queue"

        return ResponseEntity.ok().body("Financial analysis request completed successfully.");
    }
}
