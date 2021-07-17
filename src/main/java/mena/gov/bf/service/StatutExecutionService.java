package mena.gov.bf.service;

import mena.gov.bf.domain.StatutExecution;
import mena.gov.bf.fileManager.FileManagerService;
import mena.gov.bf.model.TypeDossier;
import mena.gov.bf.repository.StatutExecutionRepository;
import mena.gov.bf.service.dto.StatutExecutionDTO;
import mena.gov.bf.service.mapper.StatutExecutionMapper;
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
 * Service Implementation for managing {@link StatutExecution}.
 */
@Service
@Transactional
public class StatutExecutionService {

    private final Logger log = LoggerFactory.getLogger(StatutExecutionService.class);

    private final StatutExecutionRepository statutExecutionRepository;

    private final StatutExecutionMapper statutExecutionMapper;

    @Autowired
    private FileManagerService fileManagerService;

    public StatutExecutionService(StatutExecutionRepository statutExecutionRepository, StatutExecutionMapper statutExecutionMapper) {
        this.statutExecutionRepository = statutExecutionRepository;
        this.statutExecutionMapper = statutExecutionMapper;
    }

    /**
     * Save a statutExecution.
     *
     * @param statutExecutionDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public StatutExecutionDTO save(StatutExecutionDTO statutExecutionDTO) {
        log.debug("Request to save StatutExecution : {}", statutExecutionDTO);
        StatutExecution statutExecution = statutExecutionMapper.toEntity(statutExecutionDTO);
        statutExecution = statutExecutionRepository.save(statutExecution);
        if (!statutExecutionDTO.getFiles().isEmpty()) {
            this.fileManagerService.fileUploading( statutExecution.getId(), TypeDossier.STATUTEXECUTION, statutExecutionDTO.getFiles() );
        }
        return statutExecutionMapper.toDto(statutExecution);
    }

    /**
     * Get all the statutExecutions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StatutExecutionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StatutExecutions");
        return statutExecutionRepository.findAll(pageable)
            .map(statutExecutionMapper::toDto);
    }


    /**
     * Get one statutExecution by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StatutExecutionDTO> findOne(Long id) {
        log.debug("Request to get StatutExecution : {}", id);
        Optional<StatutExecutionDTO> statutExecutionDTO = statutExecutionRepository.findById(id).map(statutExecutionMapper::toDto);
        if(statutExecutionDTO.isPresent()) {
            statutExecutionDTO.get().setFiles( this.fileManagerService.getEntityDataFile( statutExecutionDTO.get().getId(), TypeDossier.STATUTEXECUTION ) );
        }
        return statutExecutionDTO;
    }

    /**
     * Delete the statutExecution by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StatutExecution : {}", id);
        statutExecutionRepository.deleteById(id);
    }
    public List<StatutExecutionDTO> findAllStatutexecutionByContrat(Long contratId) {
        return statutExecutionRepository.findAll().stream().filter(statutExecution -> statutExecution.getContrat() != null && statutExecution.getContrat().getId().equals(contratId)
            && statutExecution.isDeleted()!=null && !statutExecution.isDeleted()).map(statutExecutionMapper::toDto).collect(Collectors.toList());
    }

    public StatutExecutionDTO activateMarcheResilier(StatutExecutionDTO statutExecutionDTO) {
        StatutExecution statutExecution = statutExecutionMapper.toEntity( statutExecutionDTO );
        statutExecutionDTO.setContratResilier(true);
        statutExecution = statutExecutionRepository.save( statutExecution );
        return statutExecutionMapper.toDto( statutExecution );
    }
}
