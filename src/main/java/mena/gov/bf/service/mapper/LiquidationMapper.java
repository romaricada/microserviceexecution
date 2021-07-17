package mena.gov.bf.service.mapper;

import mena.gov.bf.domain.*;
import mena.gov.bf.service.dto.LiquidationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Liquidation} and its DTO {@link LiquidationDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContratMapper.class, EngagementMapper.class})
public interface LiquidationMapper extends EntityMapper<LiquidationDTO, Liquidation> {

    @Mapping(source = "contrat.id", target = "contratId")
    @Mapping(source = "engagement.id", target = "engagementId")
    LiquidationDTO toDto(Liquidation liquidation);

    @Mapping(source = "contratId", target = "contrat")
    @Mapping(source = "engagementId", target = "engagement")
    Liquidation toEntity(LiquidationDTO liquidationDTO);

    default Liquidation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Liquidation liquidation = new Liquidation();
        liquidation.setId(id);
        return liquidation;
    }
}
