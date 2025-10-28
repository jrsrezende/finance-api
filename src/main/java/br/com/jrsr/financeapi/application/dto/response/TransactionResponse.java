package br.com.jrsr.financeapi.application.dto.response;

import java.util.UUID;

public record TransactionResponse(UUID id, String name, String date, Double value, TypeResponse type) {
}
