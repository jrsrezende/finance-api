package br.com.jrsr.financeapi.application.dto.request;

import java.util.UUID;

public record CreateTransactionRequest(String name, String date, Double value, UUID typeId) {
}
