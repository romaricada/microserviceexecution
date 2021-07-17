package mena.gov.bf.service;

import mena.gov.bf.domain.OrdreCommande;
import mena.gov.bf.fileManager.FileManagerService;
import mena.gov.bf.repository.OrdreCommandeRepository;
import mena.gov.bf.service.dto.OrdreCommandeDTO;
import mena.gov.bf.service.mapper.OrdreCommandeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdreCommandeService {
    private final Logger log = LoggerFactory.getLogger(OrdreCommandeService.class);

    private final OrdreCommandeRepository ordreCommandeRepository;

    private final OrdreCommandeMapper ordreCommandeMapper;

    @Autowired
    private FileManagerService fileManagerService;

    public OrdreCommandeService(
            OrdreCommandeRepository ordreCommandeRepository,
            OrdreCommandeMapper ordreCommandeMapper) {
        this.ordreCommandeRepository = ordreCommandeRepository;
        this.ordreCommandeMapper = ordreCommandeMapper;
    }

    /**
     * Save a OrdreCommande.
     *
     * @param ordreCommandeDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public OrdreCommandeDTO save(OrdreCommandeDTO ordreCommandeDTO) {
        log.debug("Request to save OrdreCommande : {}", ordreCommandeDTO);
        OrdreCommande ordreCommande = ordreCommandeMapper.toEntity(ordreCommandeDTO);
        ordreCommande = ordreCommandeRepository.save(ordreCommande);
        return ordreCommandeMapper.toDto(ordreCommande);
    }

    /**
     * Get all the OrdreCommande.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdreCommandeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrdreCommandes");
        return ordreCommandeRepository.findAll(pageable)
                .map(ordreCommandeMapper::toDto);
    }


    /**
     * Get one OrdreCommande by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrdreCommandeDTO> findOne(Long id) {
        log.debug("Request to get OrdreCommande : {}", id);
        // ligneBudgetaireContratDTO.ifPresent(ligneBudgetaireContratDTO1 -> ligneBudgetaireContratDTO1.setFiles(this.fileManagerService.getEntityDataFile(ligneBudgetaireContratDTO1.getId(), TypeDossier.PENALITE)));
        return ordreCommandeRepository.findById(id).map(ordreCommandeMapper::toDto);
    }

    /**
     * Delete the OrdreCommande by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrdreCommande : {}", id);
        ordreCommandeRepository.deleteById(id);
    }

    public List<OrdreCommandeDTO> deleteAll(List<OrdreCommandeDTO> ordreCommandeDTOS) {
        ordreCommandeDTOS.forEach(ordreCommandeDTO -> {
            ordreCommandeDTO.setDeleted(true);
        });
        ordreCommandeRepository
            .deleteAll(ordreCommandeDTOS.stream().map(ordreCommandeMapper::toEntity).collect(Collectors.toList()));
        return ordreCommandeDTOS;
    }

    public List<OrdreCommandeDTO> findAllbyContrat(Long contratId){
        return ordreCommandeRepository.findAll().stream().filter(ordreCommande -> ordreCommande.getContrat()!=null
         && ordreCommande.getContrat().getId().equals(contratId)).map(ordreCommandeMapper::toDto).collect(Collectors.toList());
    }
}
