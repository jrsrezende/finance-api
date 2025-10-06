package br.com.jrsr.financeapi.application.controllers;

import br.com.jrsr.financeapi.application.dto.request.CreateTransactionRequest;
import br.com.jrsr.financeapi.application.dto.request.UpdateTransactionRequest;
import br.com.jrsr.financeapi.application.dto.response.QueryTransactionResponse;
import br.com.jrsr.financeapi.application.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping
    public ResponseEntity<QueryTransactionResponse> createTransaction(
            @RequestBody @Valid CreateTransactionRequest request) {
        QueryTransactionResponse response = service.create(request);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Update an existing transaction by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction updated successfully"),
            @ApiResponse(responseCode = "404", description = "Transaction not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PutMapping("/{id}")
    public ResponseEntity<QueryTransactionResponse> updateTransaction(
            @RequestBody @Valid UpdateTransactionRequest request,
            @Parameter(description = "ID of the transaction to update") @PathVariable UUID id) {
        QueryTransactionResponse response = service.update(request, id);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "Delete a transaction by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<QueryTransactionResponse> deleteTransaction(
            @Parameter(description = "ID of the transaction to delete") @PathVariable UUID id) {
        QueryTransactionResponse response = service.delete(id);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "Get a transaction by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction found"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<QueryTransactionResponse> getTransactionById(
            @Parameter(description = "ID of the transaction to retrieve") @PathVariable UUID id) {
        QueryTransactionResponse response = service.getByID(id);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "List transactions within a date range, paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of transactions returned successfully"),
    })
    @GetMapping("{from}/{to}/{page}")
    public ResponseEntity<Page<QueryTransactionResponse>> getTransactions(
            @Parameter(description = "Start date (yyyy-MM-dd)") @PathVariable LocalDate from,
            @Parameter(description = "End date (yyyy-MM-dd)") @PathVariable LocalDate to,
            @Parameter(description = "Page number (0-based)") @PathVariable int page) {
        Page<QueryTransactionResponse> responses = service.getTransactions(from, to, page);
        return ResponseEntity.status(200).body(responses);
    }
}
