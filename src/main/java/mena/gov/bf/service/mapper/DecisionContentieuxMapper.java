package mena.gov.bf.service.mapper;

import mena.gov.bf.domain.*;
import mena.gov.bf.service.dto.DecisionContentieuxDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DecisionContentieux} and its DTO {@link DecisionContentieuxDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DecisionContentieuxMapper extends EntityMapper<DecisionContentieuxDTO, DecisionContentieux> {


    @Mapping(target = "contentieuxs", ignore = true)
    DecisionContentieux toEntity(DecisionContentieuxDTO decisionContentieuxDTO);

    default DecisionContentieux fromId(Long id) {
        if (id == null) {
            return null;
        }
        DecisionContentieux decisionContentieux = new DecisionContentieux();
        decisionContentieux.setId(id);
        return decisionContentieux;
    }
}
