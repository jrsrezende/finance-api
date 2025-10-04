package br.com.jrsr.financeapi.application.services;

import br.com.jrsr.financeapi.application.dto.request.CreateTransactionRequest;
import br.com.jrsr.financeapi.application.dto.request.UpdateTransactionRequest;
import br.com.jrsr.financeapi.application.dto.response.QueryTransactionResponse;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.UUID;

public interface TransactionService {

    QueryTransactionResponse create(CreateTransactionRequest request);

    QueryTransactionResponse update(UpdateTransactionRequest request, UUID id);

    QueryTransactionResponse delete(UUID id);

    QueryTransactionResponse getByID(UUID id);

    Page<QueryTransactionResponse> getTransactions(LocalDate from, LocalDate to, int page);
}
