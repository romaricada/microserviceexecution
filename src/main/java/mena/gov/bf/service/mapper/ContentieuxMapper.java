package mena.gov.bf.service.mapper;

import mena.gov.bf.domain.*;
import mena.gov.bf.service.dto.ContentieuxDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contentieux} and its DTO {@link ContentieuxDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContratMapper.class, DecisionContentieuxMapper.class})
public interface ContentieuxMapper extends EntityMapper<ContentieuxDTO, Contentieux> {

    @Mapping(source = "contrat.id", target = "contratId")
    @Mapping(source = "decisionContentieux.id", target = "decisionContentieuxId")
    ContentieuxDTO toDto(Contentieux contentieux);

    @Mapping(source = "contratId", target = "contrat")
    @Mapping(source = "decisionContentieuxId", target = "decisionContentieux")
    Contentieux toEntity(ContentieuxDTO contentieuxDTO);

    default Contentieux fromId(Long id) {
        if (id == null) {
            return null;
        }
        Contentieux contentieux = new Contentieux();
        contentieux.setId(id);
        return contentieux;
    }
}
