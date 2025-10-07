package br.com.jrsr.financeapi.infrastructure.repositories;

import br.com.jrsr.financeapi.domain.collections.Analysis;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnalysisRepository extends MongoRepository<Analysis, UUID> {
}
