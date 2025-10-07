package br.com.jrsr.financeapi.infrastructure.runners;

import br.com.jrsr.financeapi.domain.entities.Type;
import br.com.jrsr.financeapi.infrastructure.repositories.TypeRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    private final TypeRepository repository;

    public DataLoader(TypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args){
        List<String> tranactionsTypes = List.of("Expense", "Income", "Investment");

        tranactionsTypes.stream().filter(description -> repository.findByDescription(description).isEmpty())
                .map(description -> new Type(null, description))
                .forEach(repository::save);
    }
}
