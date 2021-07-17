package mena.gov.bf.service;

import mena.gov.bf.domain.TypeAvenant;
import mena.gov.bf.repository.TypeAvenantRepository;
import mena.gov.bf.service.dto.TypeAvenantDTO;
import mena.gov.bf.service.mapper.TypeAvenantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeAvenant}.
 */
@Service
@Transactional
public class TypeAvenantService {

    private final Logger log = LoggerFactory.getLogger(TypeAvenantService.class);

    private final TypeAvenantRepository typeAvenantRepository;

    private final TypeAvenantMapper typeAvenantMapper;

    public TypeAvenantService(TypeAvenantRepository typeAvenantRepository, TypeAvenantMapper typeAvenantMapper) {
        this.typeAvenantRepository = typeAvenantRepository;
        this.typeAvenantMapper = typeAvenantMapper;
    }

    /**
     * Save a typeAvenant.
     *
     * @param typeAvenantDTO the entity to save.
     * @return the persisted entity.
     */
    public TypeAvenantDTO save(TypeAvenantDTO typeAvenantDTO) {
        log.debug("Request to save TypeAvenant : {}", typeAvenantDTO);
        TypeAvenant typeAvenant = typeAvenantMapper.toEntity(typeAvenantDTO);
        typeAvenant = typeAvenantRepository.save(typeAvenant);
        return typeAvenantMapper.toDto(typeAvenant);
    }

    /**
     * Get all the typeAvenants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeAvenantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeAvenants");
        return typeAvenantRepository.findAll(pageable)
            .map(typeAvenantMapper::toDto);
    }


    /**
     * Get one typeAvenant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TypeAvenantDTO> findOne(Long id) {
        log.debug("Request to get TypeAvenant : {}", id);
        return typeAvenantRepository.findById(id)
            .map(typeAvenantMapper::toDto);
    }

    /**
     * Delete the typeAvenant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeAvenant : {}", id);
        typeAvenantRepository.deleteById(id);
    }
}
