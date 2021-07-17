/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mena.gov.bf.service.mapper;

import mena.gov.bf.domain.Etape;
import mena.gov.bf.service.dto.EtapeDTO;
import org.mapstruct.Mapper;

/**
 *
 * @author nafolo
 */
@Mapper(componentModel = "spring", uses = {})
public interface EtapeMapper extends EntityMapper<EtapeDTO, Etape> {

    Etape toEntity(EtapeDTO contratDTO);

    default Etape fromId(String id) {
        if (id == null) {
            return null;
        }
        Etape etape = new Etape();
        etape.setId(id);
        return etape;
    }
}
