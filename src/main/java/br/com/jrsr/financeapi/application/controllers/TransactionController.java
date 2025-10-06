package br.com.jrsr.financeapi.application.controllers;

import br.com.jrsr.financeapi.application.dto.request.CreateTransactionRequest;
import br.com.jrsr.financeapi.application.dto.request.UpdateTransactionRequest;
import br.com.jrsr.financeapi.application.dto.response.QueryTransactionResponse;
import br.com.jrsr.financeapi.application.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<QueryTransactionResponse> createTransaction(@RequestBody @Valid CreateTransactionRequest request) {
        QueryTransactionResponse response = service.create(request);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QueryTransactionResponse> updateTransaction(@RequestBody @Valid UpdateTransactionRequest request, @PathVariable UUID id) {
        QueryTransactionResponse response = service.update(request, id);
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<QueryTransactionResponse> deleteTransaction(@PathVariable UUID id) {
        QueryTransactionResponse response = service.delete(id);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QueryTransactionResponse> getTransactionById(@PathVariable UUID id) {
        QueryTransactionResponse response = service.getByID(id);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("{from}/{to}/{page}")
    public ResponseEntity<Page<QueryTransactionResponse>> getTransactions(@PathVariable LocalDate from, @PathVariable LocalDate to, @PathVariable int page) {
        Page<QueryTransactionResponse> responses = service.getTransactions(from, to , page);
        return ResponseEntity.status(200).body(responses);
    }
}
