package mena.gov.bf.service;

import mena.gov.bf.domain.EtapeExecution;
import mena.gov.bf.repository.EtapeExecutionRepository;
import mena.gov.bf.service.dto.EtapeExecutionDTO;
import mena.gov.bf.service.mapper.EtapeExecutionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link EtapeExecution}.
 */
@Service
@Transactional
public class EtapeExecutionService {

    private final Logger log = LoggerFactory.getLogger(EtapeExecutionService.class);

    private final EtapeExecutionRepository etapeExecutionRepository;

    private final EtapeExecutionMapper etapeExecutionMapper;

    public EtapeExecutionService(EtapeExecutionRepository etapeExecutionRepository, EtapeExecutionMapper etapeExecutionMapper) {
        this.etapeExecutionRepository = etapeExecutionRepository;
        this.etapeExecutionMapper = etapeExecutionMapper;
    }

    /**
     * Save a etapeExecution.
     *
     * @param etapeExecutionDTO the entity to save.
     * @return the persisted entity.
     */
    public EtapeExecutionDTO save(EtapeExecutionDTO etapeExecutionDTO) {
        log.debug("Request to save EtapeExecution : {}", etapeExecutionDTO);
        EtapeExecution etapeExecution = etapeExecutionMapper.toEntity(etapeExecutionDTO);
        etapeExecution = etapeExecutionRepository.save(etapeExecution);
        return etapeExecutionMapper.toDto(etapeExecution);
    }

    /**
     * Get all the etapeExecutions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EtapeExecutionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EtapeExecutions");
        return etapeExecutionRepository.findAll(pageable)
            .map(etapeExecutionMapper::toDto);
    }


    /**
     * Get one etapeExecution by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EtapeExecutionDTO> findOne(Long id) {
        log.debug("Request to get EtapeExecution : {}", id);
        return etapeExecutionRepository.findById(id)
            .map(etapeExecutionMapper::toDto);
    }

    /**
     * Delete the etapeExecution by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EtapeExecution : {}", id);
        etapeExecutionRepository.deleteById(id);
    }

    public List<EtapeExecutionDTO> getAllEtapeExecution() {
        return etapeExecutionMapper.toDto(etapeExecutionRepository.findAllByDeletedIsFalse());
    }
}
