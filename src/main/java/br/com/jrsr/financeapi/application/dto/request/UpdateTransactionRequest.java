package br.com.jrsr.financeapi.application.dto.request;

import java.util.UUID;

public record UpdateTransactionRequest(String name, String date, Double value, UUID typeId) {
}
