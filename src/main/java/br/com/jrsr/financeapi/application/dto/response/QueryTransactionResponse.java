package br.com.jrsr.financeapi.application.dto.response;

import java.util.UUID;

public record QueryTransactionResponse(UUID id, String name, String date, Double value, QueryTypeResponse type) {
}
