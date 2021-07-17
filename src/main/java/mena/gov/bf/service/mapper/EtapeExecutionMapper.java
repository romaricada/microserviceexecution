package mena.gov.bf.service.mapper;

import mena.gov.bf.domain.*;
import mena.gov.bf.service.dto.EtapeExecutionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EtapeExecution} and its DTO {@link EtapeExecutionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContratMapper.class})
public interface EtapeExecutionMapper extends EntityMapper<EtapeExecutionDTO, EtapeExecution> {

    @Mapping(source = "contrat.id", target = "contratId")
    EtapeExecutionDTO toDto(EtapeExecution etapeExecution);

    @Mapping(source = "contratId", target = "contrat")
    EtapeExecution toEntity(EtapeExecutionDTO etapeExecutionDTO);

    default EtapeExecution fromId(Long id) {
        if (id == null) {
            return null;
        }
        EtapeExecution etapeExecution = new EtapeExecution();
        etapeExecution.setId(id);
        return etapeExecution;
    }
}
