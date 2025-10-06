package br.com.jrsr.financeapi.application.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.UUID;

public record CreateTransactionRequest(
        @NotBlank(message = "Transaction name is required")
        @Size(max = 100, message = "Transaction name must be at most 100 characters")
        String name,

        @NotNull(message = "Transaction date is required")
        LocalDate date,

        @NotNull(message = "Transaction value is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Transaction value must be greater than zero")
        Double value,

        @NotNull(message = "Transaction type is required")
        UUID typeId
) {}
