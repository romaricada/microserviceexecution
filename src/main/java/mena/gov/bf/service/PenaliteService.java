package mena.gov.bf.service;

import mena.gov.bf.domain.Penalite;
import mena.gov.bf.fileManager.FileManagerService;
import mena.gov.bf.model.ExecutionFile;
import mena.gov.bf.model.TypeDossier;
import mena.gov.bf.proxies.FileManangerRepository;
import mena.gov.bf.repository.PenaliteRepository;
import mena.gov.bf.service.dto.PenaliteDTO;
import mena.gov.bf.service.mapper.PenaliteMapper;
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

/**
 * Service Implementation for managing {@link Penalite}.
 */
@Service
@Transactional
public class PenaliteService {

    private final Logger log = LoggerFactory.getLogger(PenaliteService.class);

    private final PenaliteRepository penaliteRepository;

    private final PenaliteMapper penaliteMapper;

    @Autowired
    private FileManagerService fileManagerService;

    public PenaliteService(PenaliteRepository penaliteRepository, PenaliteMapper penaliteMapper) {
        this.penaliteRepository = penaliteRepository;
        this.penaliteMapper = penaliteMapper;
    }

    /**
     * Save a penalite.
     *
     * @param penaliteDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public PenaliteDTO save(PenaliteDTO penaliteDTO) {
        log.debug("Request to save Penalite : {}", penaliteDTO);
        Penalite penalite = penaliteMapper.toEntity(penaliteDTO);
        penalite = penaliteRepository.save(penalite);
        if (!penaliteDTO.getFiles().isEmpty()) {
            this.fileManagerService.fileUploading( penalite.getId(), TypeDossier.PENALITE, penaliteDTO.getFiles() );
        }
        return penaliteMapper.toDto(penalite);
    }

    /**
     * Get all the penalites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PenaliteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Penalites");
        return penaliteRepository.findAll(pageable)
            .map(penaliteMapper::toDto);
    }


    /**
     * Get one penalite by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PenaliteDTO> findOne(Long id) {
        log.debug("Request to get Penalite : {}", id);
        Optional<PenaliteDTO> penaliteDTO = penaliteRepository.findById(id).map(penaliteMapper::toDto);
        if(penaliteDTO.isPresent()) {
            penaliteDTO.get().setFiles( this.fileManagerService.getEntityDataFile( penaliteDTO.get().getId(), TypeDossier.PENALITE ) );
        }
        return penaliteDTO;
    }

    /**
     * Delete the penalite by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Penalite : {}", id);
        penaliteRepository.deleteById(id);
    }

    public List<PenaliteDTO> findAllPenaliteByContrat(Long contratId){
        return penaliteRepository.findAll().stream().filter(penalite -> penalite.getContratId() != null && penalite.getContratId().equals(contratId)
        && penalite.isDeleted()!=null && !penalite.isDeleted()).map(penaliteMapper::toDto).collect(Collectors.toList());
    }

    public List<PenaliteDTO> findPenalitebyLiquidation(Long liquidationId){
        return penaliteRepository.findAll().stream().filter(penalite -> penalite.getLiquidationId()!=null &&
            penalite.getLiquidationId().equals(liquidationId) && !penalite.isDeleted())
            .map(penaliteMapper::toDto).collect(Collectors.toList());
    }
}
