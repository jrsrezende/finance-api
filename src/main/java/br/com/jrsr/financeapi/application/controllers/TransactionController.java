package br.com.jrsr.financeapi.application.controllers;

import br.com.jrsr.financeapi.application.dto.request.CreateTransactionRequest;
import br.com.jrsr.financeapi.application.dto.request.UpdateTransactionRequest;
import br.com.jrsr.financeapi.application.dto.response.TransactionResponse;
import br.com.jrsr.financeapi.application.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Transactions", description = "Operations related to financial transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new transaction")
    @ApiResponse(responseCode = "201", description = "Transaction created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody @Valid CreateTransactionRequest request) {
        TransactionResponse response = service.create(request);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Update an existing transaction by ID")
    @ApiResponse(responseCode = "200", description = "Transaction updated successfully")
    @ApiResponse(responseCode = "404", description = "Transaction not found")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(@RequestBody @Valid UpdateTransactionRequest request, @PathVariable UUID id) {
        TransactionResponse response = service.update(request, id);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "Delete a transaction by ID")
    @ApiResponse(responseCode = "200", description = "Transaction deleted successfully")
    @ApiResponse(responseCode = "404", description = "Transaction not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable UUID id) {
        String response = service.delete(id);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "Get a transaction by ID")
    @ApiResponse(responseCode = "200", description = "Transaction found")
    @ApiResponse(responseCode = "404", description = "Transaction not found")
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(
            @Parameter(description = "ID of the transaction to retrieve") @PathVariable UUID id) {
        TransactionResponse response = service.getByID(id);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "List transactions within a date range, paginated")
    @ApiResponse(responseCode = "200", description = "List of transactions returned successfully")
    @GetMapping("{from}/{to}/{page}")
    public ResponseEntity<Page<TransactionResponse>> getTransactions(@PathVariable LocalDate from, @PathVariable LocalDate to, @PathVariable int page) {
        Page<TransactionResponse> responses = service.getTransactions(from, to, page);
        return ResponseEntity.status(200).body(responses);
    }
}
