package br.com.jrsr.financeapi.application.services;

import br.com.jrsr.financeapi.application.dto.response.QueryTypeResponse;

import java.util.List;

public interface TypeService {

    List<QueryTypeResponse> getAll();
}
