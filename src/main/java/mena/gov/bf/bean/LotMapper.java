package mena.gov.bf.bean;

import mena.gov.bf.service.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Lot} and its DTO {@link LotDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LotMapper extends EntityMapper<LotDTO, Lot> {


    Lot toEntity(LotDTO lotDTO);

    default Lot fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lot lot = new Lot();
        lot.setId(id);
        return lot;
    }
}
