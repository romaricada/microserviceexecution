package mena.gov.bf.service.mapper;

import mena.gov.bf.domain.*;
import mena.gov.bf.service.dto.TypeAvenantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeAvenant} and its DTO {@link TypeAvenantDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeAvenantMapper extends EntityMapper<TypeAvenantDTO, TypeAvenant> {


    @Mapping(target = "avenants", ignore = true)
    TypeAvenant toEntity(TypeAvenantDTO typeAvenantDTO);

    default TypeAvenant fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeAvenant typeAvenant = new TypeAvenant();
        typeAvenant.setId(id);
        return typeAvenant;
    }
}
