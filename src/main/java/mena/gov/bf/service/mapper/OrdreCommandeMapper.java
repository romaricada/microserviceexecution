package mena.gov.bf.service.mapper;

import mena.gov.bf.domain.OrdreCommande;
import mena.gov.bf.service.dto.OrdreCommandeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring", uses = {ContratMapper.class, AvenantMapper.class})
public interface OrdreCommandeMapper extends EntityMapper<OrdreCommandeDTO, OrdreCommande> {
    @Mapping(source = "contrat.id", target = "contratId")
    @Mapping(source = "avenant.id", target = "avenantId")
    OrdreCommandeDTO toDto(OrdreCommande ordreCommande);

    @Mapping(source = "contratId", target = "contrat")
    @Mapping(source = "avenantId", target = "avenant")
    OrdreCommande toEntity(OrdreCommandeDTO ordreCommandeDTO);
    default OrdreCommande fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrdreCommande ordreCommande = new OrdreCommande();
        ordreCommande.setId(id);
        return ordreCommande;
    }
}
