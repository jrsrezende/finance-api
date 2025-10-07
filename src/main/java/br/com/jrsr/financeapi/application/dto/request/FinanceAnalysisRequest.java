package br.com.jrsr.financeapi.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FinanceAnalysisRequest(

        @NotBlank(message = "Analysis question cannot be blank.")
        @Size(min = 10, max = 500, message = "Analysis question must be between 10 and 500 characters.")
        String analysisQuestion,

        @NotBlank(message = "Notification email is required.")
        @Email(message = "Invalid email format.")
        String notificationEmail
) {
}
