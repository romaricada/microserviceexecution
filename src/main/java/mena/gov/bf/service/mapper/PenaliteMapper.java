package mena.gov.bf.service.mapper;

import mena.gov.bf.domain.*;
import mena.gov.bf.service.dto.PenaliteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Penalite} and its DTO {@link PenaliteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PenaliteMapper extends EntityMapper<PenaliteDTO, Penalite> {

    PenaliteDTO toDto(Penalite penalite);

    Penalite toEntity(PenaliteDTO penaliteDTO);

    default Penalite fromId(Long id) {
        if (id == null) {
            return null;
        }
        Penalite penalite = new Penalite();
        penalite.setId(id);
        return penalite;
    }
}
