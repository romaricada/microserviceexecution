package mena.gov.bf.service;

import mena.gov.bf.domain.DecisionContentieux;
import mena.gov.bf.repository.DecisionContentieuxRepository;
import mena.gov.bf.service.dto.DecisionContentieuxDTO;
import mena.gov.bf.service.mapper.DecisionContentieuxMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DecisionContentieux}.
 */
@Service
@Transactional
public class DecisionContentieuxService {

    private final Logger log = LoggerFactory.getLogger(DecisionContentieuxService.class);

    private final DecisionContentieuxRepository decisionContentieuxRepository;

    private final DecisionContentieuxMapper decisionContentieuxMapper;

    public DecisionContentieuxService(DecisionContentieuxRepository decisionContentieuxRepository, DecisionContentieuxMapper decisionContentieuxMapper) {
        this.decisionContentieuxRepository = decisionContentieuxRepository;
        this.decisionContentieuxMapper = decisionContentieuxMapper;
    }

    /**
     * Save a decisionContentieux.
     *
     * @param decisionContentieuxDTO the entity to save.
     * @return the persisted entity.
     */
    public DecisionContentieuxDTO save(DecisionContentieuxDTO decisionContentieuxDTO) {
        log.debug("Request to save DecisionContentieux : {}", decisionContentieuxDTO);
        DecisionContentieux decisionContentieux = decisionContentieuxMapper.toEntity(decisionContentieuxDTO);
        decisionContentieux = decisionContentieuxRepository.save(decisionContentieux);
        return decisionContentieuxMapper.toDto(decisionContentieux);
    }

    /**
     * Get all the decisionContentieuxes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DecisionContentieuxDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DecisionContentieuxes");
        return decisionContentieuxRepository.findAll(pageable)
            .map(decisionContentieuxMapper::toDto);
    }


    /**
     * Get one decisionContentieux by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DecisionContentieuxDTO> findOne(Long id) {
        log.debug("Request to get DecisionContentieux : {}", id);
        return decisionContentieuxRepository.findById(id)
            .map(decisionContentieuxMapper::toDto);
    }

    /**
     * Delete the decisionContentieux by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DecisionContentieux : {}", id);
        decisionContentieuxRepository.deleteById(id);
    }

    public List<DecisionContentieuxDTO> findDecisionContentieuxbyContentieux(Long contentieuxId){
        return decisionContentieuxRepository.findAll().stream().filter(decisionContentieux -> decisionContentieux.getContentieuxId()!=null &&
            decisionContentieux.getContentieuxId().equals(contentieuxId) && !decisionContentieux.isDeleted())
            .map(decisionContentieuxMapper::toDto).collect(Collectors.toList());
    }

}
