package mena.gov.bf.service.mapper;

import mena.gov.bf.domain.LigneBudgetaireContrat;
import mena.gov.bf.service.dto.LigneBudgetaireContratDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link mena.gov.bf.domain.LigneBudgetaireContrat} and its DTO {@link mena.gov.bf.service.dto.LigneBudgetaireContratDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContratMapper.class})
public interface LigneBudgetaireContratMapper extends EntityMapper<LigneBudgetaireContratDTO, LigneBudgetaireContrat> {
    @Mapping(source = "contrat.id", target = "contratId")
    LigneBudgetaireContratDTO toDto(LigneBudgetaireContrat ligneBudgetaireContrat);

    @Mapping(source = "contratId", target = "contrat")
    LigneBudgetaireContrat toEntity(LigneBudgetaireContratDTO ligneBudgetaireContratDTO);
    default LigneBudgetaireContrat fromId(Long id) {
        if (id == null) {
            return null;
        }
        LigneBudgetaireContrat ligneBudgetaireContrat = new LigneBudgetaireContrat();
        ligneBudgetaireContrat.setId(id);
        return ligneBudgetaireContrat;
    }
}
