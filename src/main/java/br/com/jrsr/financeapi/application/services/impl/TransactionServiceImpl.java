package br.com.jrsr.financeapi.application.services.impl;

import br.com.jrsr.financeapi.application.dto.request.CreateTransactionRequest;
import br.com.jrsr.financeapi.application.dto.request.UpdateTransactionRequest;
import br.com.jrsr.financeapi.application.dto.response.QueryTransactionResponse;
import br.com.jrsr.financeapi.application.dto.response.QueryTypeResponse;
import br.com.jrsr.financeapi.application.exceptions.ResourceNotFoundException;
import br.com.jrsr.financeapi.application.services.TransactionService;
import br.com.jrsr.financeapi.domain.entities.Transaction;
import br.com.jrsr.financeapi.domain.entities.Type;
import br.com.jrsr.financeapi.infrastructure.repositories.TransactionRepository;
import br.com.jrsr.financeapi.infrastructure.repositories.TypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final TypeRepository typeRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TypeRepository typeRepository) {
        this.transactionRepository = transactionRepository;
        this.typeRepository = typeRepository;
    }


    @Override
    public QueryTransactionResponse create(CreateTransactionRequest request) {

        Transaction transaction = new Transaction();
        transaction.setName(request.name());
        transaction.setValue(BigDecimal.valueOf(request.value()));
        Type type = typeRepository.findById(request.typeId()).orElseThrow(() -> new ResourceNotFoundException("Type does not exist"));
        transaction.setType(type);

        transactionRepository.save(transaction);

        QueryTypeResponse typeResponse = new QueryTypeResponse(type.getId(), type.getDescription());

        return new QueryTransactionResponse(transaction.getId(), transaction.getName(), transaction.getDate().toString(), transaction.getValue().doubleValue(), typeResponse);
    }

    public QueryTransactionResponse update(UpdateTransactionRequest request, UUID id) {

        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction does not exist"));
        transaction.setName(request.name());
        transaction.setValue(BigDecimal.valueOf(request.value()));
        Type type = typeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Type does not exist"));
        transaction.setType(type);

        transactionRepository.save(transaction);

        QueryTypeResponse typeResponse = new QueryTypeResponse(type.getId(), type.getDescription());

        return new QueryTransactionResponse(transaction.getId(), transaction.getName(), transaction.getDate().toString(), transaction.getValue().doubleValue(), typeResponse);
    }

    @Override
    public QueryTransactionResponse delete(UUID id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction does not exist"));

        transactionRepository.delete(transaction);

        QueryTypeResponse typeResponse = new QueryTypeResponse(transaction.getType().getId(), transaction.getType().getDescription());

        return new QueryTransactionResponse(transaction.getId(), transaction.getName(), transaction.getDate().toString(), transaction.getValue().doubleValue(), typeResponse);
    }

    @Override
    public QueryTransactionResponse getByID(UUID id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction does not exist"));

        QueryTypeResponse typeResponse = new QueryTypeResponse(transaction.getType().getId(), transaction.getType().getDescription());

        return new QueryTransactionResponse(transaction.getId(), transaction.getName(), transaction.getDate().toString(), transaction.getValue().doubleValue(), typeResponse);
    }

    @Override
    public Page<QueryTransactionResponse> getTransactions(LocalDate from, LocalDate to, int page) {

        //Criando a regra de paginação, máximo 25 registros por página
        Pageable pageable = PageRequest.of(page, 25);

        Page<Transaction> transactions = transactionRepository.findByDateBetweenOrderByDateDesc(from, to, pageable);

        return transactions.map(t -> new QueryTransactionResponse(t.getId(),t.getName(), t.getDate().toString(), t.getValue().doubleValue(),
                new QueryTypeResponse(t.getType().getId(), t.getType().getDescription())));
    }
}
