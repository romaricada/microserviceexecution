package mena.gov.bf.service.mapper;

import mena.gov.bf.domain.LigneBudgetaireEngagement;
import mena.gov.bf.service.dto.LigneBudgetaireEngagementDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link mena.gov.bf.domain.LigneBudgetaireEngagement} and its DTO {@link mena.gov.bf.service.dto.LigneBudgetaireEngagementDTO}.
 */
@Mapper(componentModel = "spring", uses = {EngagementMapper.class})
public interface LigneBudgetaireEngagementMapper {

    @Mapping(source = "engagement.id", target = "engagementId")
    LigneBudgetaireEngagementDTO toDto(LigneBudgetaireEngagement ligneBudgetaireEngagement);

    @Mapping(source = "engagementId", target = "engagement")
    LigneBudgetaireEngagement toEntity(LigneBudgetaireEngagementDTO ligneBudgetaireEngagementDTO);

    default LigneBudgetaireEngagement fromId(Long id) {
        if (id == null) {
            return null;
        }
        LigneBudgetaireEngagement ligneBudgetaireEngagement = new LigneBudgetaireEngagement();
        ligneBudgetaireEngagement.setId(id);
        return ligneBudgetaireEngagement;
    }
}
