package mena.gov.bf.service.mapper;

import mena.gov.bf.domain.*;
import mena.gov.bf.service.dto.StatutExecutionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StatutExecution} and its DTO {@link StatutExecutionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContratMapper.class})
public interface StatutExecutionMapper extends EntityMapper<StatutExecutionDTO, StatutExecution> {

    @Mapping(source = "contrat.id", target = "contratId")
    StatutExecutionDTO toDto(StatutExecution statutExecution);

    @Mapping(source = "contratId", target = "contrat")
    StatutExecution toEntity(StatutExecutionDTO statutExecutionDTO);

    default StatutExecution fromId(Long id) {
        if (id == null) {
            return null;
        }
        StatutExecution statutExecution = new StatutExecution();
        statutExecution.setId(id);
        return statutExecution;
    }
}
