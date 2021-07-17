package mena.gov.bf.service.mapper;

import mena.gov.bf.domain.*;
import mena.gov.bf.service.dto.OrdreServiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdreService} and its DTO {@link OrdreServiceDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContratMapper.class})
public interface OrdreServiceMapper extends EntityMapper<OrdreServiceDTO, OrdreService> {


    @Mapping(source = "contrat.id", target = "contratId")
    OrdreServiceDTO toDto(OrdreService ordreService);

    @Mapping(source = "contratId", target = "contrat")
    OrdreService toEntity(OrdreServiceDTO ordreServiceDTO);



    default OrdreService fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrdreService ordreService = new OrdreService();
        ordreService.setId(id);
        return ordreService;
    }
}
