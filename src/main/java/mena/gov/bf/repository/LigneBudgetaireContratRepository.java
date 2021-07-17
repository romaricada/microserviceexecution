package mena.gov.bf.repository;

import mena.gov.bf.domain.LigneBudgetaireContrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LigneBudgetaireContratRepository extends JpaRepository<LigneBudgetaireContrat, Long> {
}
