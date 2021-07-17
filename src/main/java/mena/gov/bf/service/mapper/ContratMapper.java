package mena.gov.bf.service.mapper;

import mena.gov.bf.domain.*;
import mena.gov.bf.service.dto.ContratDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contrat} and its DTO {@link ContratDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContratMapper extends EntityMapper<ContratDTO, Contrat> {


    @Mapping(target = "statutExecutions", ignore = true)
    @Mapping(target = "contentieuxs", ignore = true)
    @Mapping(target = "avenants", ignore = true)
    @Mapping(target = "etapeExecutions", ignore = true)
    /*@Mapping(target = "liquidations", ignore = true)*/
    Contrat toEntity(ContratDTO contratDTO);

    default Contrat fromId(Long id) {
        if (id == null) {
            return null;
        }
        Contrat contrat = new Contrat();
        contrat.setId(id);
        return contrat;
    }
}
