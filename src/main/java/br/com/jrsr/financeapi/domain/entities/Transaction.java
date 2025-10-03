package br.com.jrsr.financeapi.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @PrePersist
    public void prePersist() {
        this.date = LocalDate.now();
    }

}

