package mena.gov.bf.service.mapper;

import mena.gov.bf.domain.Engagement;
import mena.gov.bf.service.dto.EngagementDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Engagement} and its DTO {@link EngagementDTO}.
 */
@Mapper(componentModel = "spring", uses = {LigneBudgetaireContratMapper.class})
public interface EngagementMapper extends EntityMapper<EngagementDTO, Engagement> {

    @Mapping(source = "ligneBudgetaireContrat.id", target = "ligneBudgetaireContratId")
    EngagementDTO toDto(Engagement engagement);

    @Mapping(source = "ligneBudgetaireContratId", target = "ligneBudgetaireContrat")
    Engagement toEntity(EngagementDTO engagementDTO);

    default Engagement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Engagement engagement = new Engagement();
        engagement.setId(id);
        return engagement;
    }
}
