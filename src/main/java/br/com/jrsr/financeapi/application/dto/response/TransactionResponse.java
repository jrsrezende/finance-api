package br.com.jrsr.financeapi.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionResponse(UUID id, String name, LocalDate date, BigDecimal value, TypeResponse type) {
}
