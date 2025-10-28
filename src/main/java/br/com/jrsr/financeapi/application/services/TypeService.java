package br.com.jrsr.financeapi.application.services;

import br.com.jrsr.financeapi.application.dto.response.TypeResponse;

import java.util.List;

public interface TypeService {

    List<TypeResponse> getAll();
}
