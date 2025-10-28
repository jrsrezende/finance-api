package br.com.jrsr.financeapi.application.services;

import br.com.jrsr.financeapi.application.dto.request.CreateTransactionRequest;
import br.com.jrsr.financeapi.application.dto.request.UpdateTransactionRequest;
import br.com.jrsr.financeapi.application.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.UUID;

public interface TransactionService {

    TransactionResponse create(CreateTransactionRequest request);

    TransactionResponse update(UpdateTransactionRequest request, UUID id);

    String delete(UUID id);

    TransactionResponse getByID(UUID id);

    Page<TransactionResponse> getTransactions(LocalDate from, LocalDate to, int page);
}
