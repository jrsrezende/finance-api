package br.com.jrsr.financeapi.application.services.impl;

import br.com.jrsr.financeapi.application.dto.request.CreateTransactionRequest;
import br.com.jrsr.financeapi.application.dto.request.UpdateTransactionRequest;
import br.com.jrsr.financeapi.application.dto.response.TransactionResponse;
import br.com.jrsr.financeapi.application.dto.response.TypeResponse;
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
    public TransactionResponse create(CreateTransactionRequest request) {

        Transaction transaction = new Transaction();
        return getTransactionResponse(transaction, request.name(), request.value(), request.typeId());
    }

    @Override
    public TransactionResponse update(UpdateTransactionRequest request, UUID id) {

        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction does not exist"));
        return getTransactionResponse(transaction, request.name(), request.value(), request.typeId());
    }

    private TransactionResponse getTransactionResponse(Transaction transaction, String name, Double value, UUID uuid) {
        transaction.setName(name);
        transaction.setValue(BigDecimal.valueOf(value));
        Type type = typeRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Type does not exist"));
        transaction.setType(type);

        transactionRepository.save(transaction);

        TypeResponse typeResponse = new TypeResponse(type.getId(), type.getDescription());

        return new TransactionResponse(transaction.getId(), transaction.getName(), transaction.getDate(), transaction.getValue(), typeResponse);
    }

    @Override
    public String delete(UUID id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction does not exist"));

        transactionRepository.delete(transaction);

        return "Transaction successfully deleted.";
    }

    @Override
    public TransactionResponse getByID(UUID id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction does not exist"));

        TypeResponse typeResponse = new TypeResponse(transaction.getType().getId(), transaction.getType().getDescription());

        return new TransactionResponse(transaction.getId(), transaction.getName(), transaction.getDate(), transaction.getValue(), typeResponse);
    }

    @Override
    public Page<TransactionResponse> getTransactions(LocalDate from, LocalDate to, int page) {

        //Criando a regra de paginação, máximo 25 registros por página
        Pageable pageable = PageRequest.of(page, 25);

        Page<Transaction> transactions = transactionRepository.findByDateBetweenOrderByDateDesc(from, to, pageable);

        return transactions.map(t -> new TransactionResponse(t.getId(),t.getName(), t.getDate(), t.getValue(),
                new TypeResponse(t.getType().getId(), t.getType().getDescription())));
    }
}
