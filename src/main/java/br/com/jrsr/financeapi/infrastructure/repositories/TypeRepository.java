package br.com.jrsr.financeapi.infrastructure.repositories;

import br.com.jrsr.financeapi.domain.entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TypeRepository extends JpaRepository<Type, UUID> {
}
