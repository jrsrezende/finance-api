package br.com.jrsr.financeapi.application.controllers;

import br.com.jrsr.financeapi.application.dto.request.FinanceAnalysisRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/finances")
@Tag(name = "Financial Analysis", description = "Operations to start the asynchronous processing of financial analyses and communications.")
public class FinancesController {


    private final ObjectMapper mapper;

    private final RabbitTemplate rabbitTemplate;

    public FinancesController(ObjectMapper mapper, RabbitTemplate rabbitTemplate) {
        this.mapper = mapper;
        this.rabbitTemplate = rabbitTemplate;
    }


    @PostMapping("analysis")
    @Operation(summary = "Request Asynchronous Financial Analysis")
    @ApiResponse(responseCode = "200", description = "Requisição de análise financeira concluída com sucesso e enviada para processamento.")
    @ApiResponse(responseCode = "400", description = "Invalid Request")
    public ResponseEntity<String> processAnalysis(@RequestBody @Valid FinanceAnalysisRequest request) throws Exception {

        String jsonRequest = mapper.writeValueAsString(request);

        rabbitTemplate.convertAndSend("finance.queue", jsonRequest);

        return ResponseEntity.ok().body("Financial analysis request completed successfully.");
    }
}