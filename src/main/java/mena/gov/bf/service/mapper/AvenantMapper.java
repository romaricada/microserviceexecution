package mena.gov.bf.service.mapper;

import mena.gov.bf.domain.*;
import mena.gov.bf.service.dto.AvenantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Avenant} and its DTO {@link AvenantDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContratMapper.class, TypeAvenantMapper.class})
public interface AvenantMapper extends EntityMapper<AvenantDTO, Avenant> {

    @Mapping(source = "contrat.id", target = "contratId")
    @Mapping(source = "typeAvenant.id", target = "typeAvenantId")
    @Mapping(source = "typeAvenant", target = "typeAvenant")
    AvenantDTO toDto(Avenant avenant);

    @Mapping(source = "contratId", target = "contrat")
    @Mapping(source = "typeAvenantId", target = "typeAvenant")
    Avenant toEntity(AvenantDTO avenantDTO);

    default Avenant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Avenant avenant = new Avenant();
        avenant.setId(id);
        return avenant;
    }
}
