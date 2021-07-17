package mena.gov.bf.service;

import mena.gov.bf.domain.OrdreService;
import mena.gov.bf.fileManager.FileManagerService;
import mena.gov.bf.model.TypeDossier;
import mena.gov.bf.repository.OrdreServiceRepository;
import mena.gov.bf.service.dto.ContratDTO;
import mena.gov.bf.service.dto.OrdreServiceDTO;
import mena.gov.bf.service.mapper.ContratMapper;
import mena.gov.bf.service.mapper.OrdreServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link OrdreService}.
 */
@Service
@Transactional
public class OrdreServiceService {

    private final Logger log = LoggerFactory.getLogger(OrdreServiceService.class);

    private final OrdreServiceRepository ordreServiceRepository;

    private final OrdreServiceMapper ordreServiceMapper;

    @Autowired
    private FileManagerService fileManagerService;

    @Autowired
    ContratMapper contratMapper;


    public OrdreServiceService(OrdreServiceRepository ordreServiceRepository, OrdreServiceMapper ordreServiceMapper) {
        this.ordreServiceRepository = ordreServiceRepository;
        this.ordreServiceMapper = ordreServiceMapper;
    }

    /**
     * Save a ordreService.
     *
     * @param ordreServiceDTO the entity to save.
     * @return the persisted entity.
     */
    public OrdreServiceDTO save(OrdreServiceDTO ordreServiceDTO) {
        log.debug("Request to save OrdreService : {}", ordreServiceDTO);
        ContratDTO contratDTO = new ContratDTO();
        ordreServiceDTO.setNumeroOs(ordreServiceDTO.getNumeroOs1());
        OrdreService ordreService = ordreServiceMapper.toEntity(ordreServiceDTO);
        ordreService.setNumeroOs(ordreServiceDTO.getNumeroOs1());


        OrdreService ordreService1 = ordreServiceRepository.save(ordreService);

        if (contratDTO.getId() != null){
            ordreServiceDTO.setContratId(contratDTO.getId());
        }


        return ordreServiceMapper.toDto(ordreService1);
    }

    /**
     * Get all the ordreServices.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrdreServiceDTO> findAll() {
        log.debug("Request to get all OrdreServices");
        List<OrdreServiceDTO> ordreServiceDTOS = ordreServiceRepository.findAll().stream()
            .map(ordreServiceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        ordreServiceDTOS.forEach(ordreServiceDTO -> {
            ordreServiceDTO.setNumeroOs1(ordreServiceDTO.getNumeroOs());
        });
        return ordreServiceDTOS;
    }


    /**
     * Get one ordreService by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrdreServiceDTO> findOne(Long id) {
        log.debug("Request to get OrdreService : {}", id);
        Optional<OrdreServiceDTO> ordreServiceDTO = ordreServiceRepository.findById(id).map(ordreServiceMapper::toDto);
        if (ordreServiceDTO.isPresent()) {
            ordreServiceDTO.get().setFiles(this.fileManagerService.getEntityDataFile(ordreServiceDTO.get().getId(), TypeDossier.ORDRESERVICE));
        }
        return ordreServiceDTO;
    }

    /**
     * Delete the ordreService by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrdreService : {}", id);
        ordreServiceRepository.deleteById(id);
    }

    public List<OrdreServiceDTO> findAllOrdreServiceByContrat (Long contratId){
        List<OrdreServiceDTO> ordreServiceDTOS = ordreServiceRepository.findAll().stream().filter(ordreService -> ordreService.getContrat() != null && ordreService.getContrat().getId().equals(contratId)
            && ordreService.isDeleted() != null && !ordreService.isDeleted()).map(ordreServiceMapper::toDto).collect(Collectors.toList());

        ordreServiceDTOS.forEach(ordreServiceDTO -> {
            ordreServiceDTO.setNumeroOs1(ordreServiceDTO.getNumeroOs());
        });

        return ordreServiceDTOS;
    }


    public List<ContratDTO> findContratByOrdreService(Long id) {
        System.out.println("valeur========================" + id);

        List<ContratDTO> contratDTOS = new ArrayList<>();
/*
        List<OrdreService> ordreService =ordreServiceRepository.findAll().stream()
            .filter(ordreServiceDTO -> ordreServiceDTO.getDeleted()!=null && !ordreServiceDTO.getDeleted() && ordreServiceDTO.getId().equals(id))
            .collect(Collectors.toList());
        ordreService.forEach(ordreService1 -> {
            contratDTOS.add(contratMapper.toDto(ordreService1.getContrat()));
        });*/

        // return contratDTOS;

        return null;
    }
}
