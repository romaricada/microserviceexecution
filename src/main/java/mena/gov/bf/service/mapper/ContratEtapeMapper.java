/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mena.gov.bf.service.mapper;

import mena.gov.bf.domain.EtapeTraitement;
import mena.gov.bf.service.dto.EtapeTraitementDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author nafolo
 */
@Mapper(componentModel = "spring", uses = {ContratMapper.class, EtapeMapper.class})
public interface ContratEtapeMapper extends EntityMapper<EtapeTraitementDTO, EtapeTraitement>{
  
    @Mapping(source = "contrat.id", target = "contratId")
    @Mapping(source = "contrat", target = "contrat")
    @Mapping(source = "etape.id", target = "etapeId")
    @Mapping(source = "etape", target = "etape")
    EtapeTraitementDTO toDto(EtapeTraitement contratEtape);

    @Mapping(source = "contratId", target = "contrat.id")
    @Mapping(source = "etapeId", target = "etape.id")
    EtapeTraitement toEntity(EtapeTraitementDTO contratDTO);

    default EtapeTraitement fromId(Long id) {
        if (id == null) {
            return null;
        }
        EtapeTraitement contratEtape = new EtapeTraitement();
        contratEtape.setId(id);
        return contratEtape;
    }
}
