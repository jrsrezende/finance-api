package br.com.jrsr.financeapi.domain.collections;

import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "analysis")
public class Analysis {

    @Id
    private UUID id;

    private LocalDateTime date;

    private String question;

    private String answer;

    private String sendEmail;
}
