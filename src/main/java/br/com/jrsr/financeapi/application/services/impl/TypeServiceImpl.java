package br.com.jrsr.financeapi.application.services.impl;

import br.com.jrsr.financeapi.application.dto.response.TypeResponse;
import br.com.jrsr.financeapi.application.services.TypeService;
import br.com.jrsr.financeapi.infrastructure.repositories.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    private final TypeRepository repository;

    public TypeServiceImpl(TypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TypeResponse> getAll() {

        return repository.findAll().stream()
                .map(type -> new TypeResponse(type.getId(), type.getDescription()))
                .toList();
    }
}
