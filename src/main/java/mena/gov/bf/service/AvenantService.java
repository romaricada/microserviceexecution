package mena.gov.bf.service;

import mena.gov.bf.domain.Avenant;
import mena.gov.bf.domain.TypeAvenant;
import mena.gov.bf.fileManager.FileManagerService;
import mena.gov.bf.model.TypeDossier;
import mena.gov.bf.repository.AvenantRepository;
import mena.gov.bf.repository.TypeAvenantRepository;
import mena.gov.bf.service.dto.AvenantDTO;
import mena.gov.bf.service.mapper.AvenantMapper;
import mena.gov.bf.service.mapper.TypeAvenantMapper;

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
 * Service Implementation for managing {@link Avenant}.
 */
@Service
@Transactional
public class AvenantService {

    private final Logger log = LoggerFactory.getLogger(AvenantService.class);

    private final AvenantRepository avenantRepository;

    private final TypeAvenantRepository typeAvenantRepository;

    private final AvenantMapper avenantMapper;

    private final TypeAvenantMapper typeAvenantMapper;

    @Autowired
    private FileManagerService fileManagerService;

    public AvenantService(AvenantRepository avenantRepository, AvenantMapper avenantMapper, TypeAvenantMapper typeAvenantMapper, TypeAvenantRepository typeAvenantRepository) {
        this.avenantRepository = avenantRepository;
        this.avenantMapper = avenantMapper;
        this.typeAvenantMapper = typeAvenantMapper;
        this.typeAvenantRepository = typeAvenantRepository;
    }

    /**
     * Save a avenant.
     *
     * @param avenantDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public AvenantDTO save(AvenantDTO avenantDTO) {
        log.debug("Request to save Avenant : {}", avenantDTO);
        // enregistrement du type avenant
        log.debug("=========avenantDTO.getTypeAvenantId()=============");
        log.debug("{}", avenantDTO.getTypeAvenantId());
        log.debug("======================");
        TypeAvenant typeAvenant;
        if (avenantDTO.getTypeAvenantId() == null) {
            typeAvenant = typeAvenantMapper.toEntity(avenantDTO.getTypeAvenant());
            typeAvenant = typeAvenantRepository.save(typeAvenant);
            avenantDTO.setTypeAvenantId(typeAvenant.getId());
            log.debug("======================");
            log.debug("typeavenant: {}, avenant.getTypeAvenantId(): {}", typeAvenant.getId(), avenantDTO.getTypeAvenant());
            log.debug("======================");
        }
        Avenant avenant = avenantMapper.toEntity(avenantDTO);
        avenant = avenantRepository.save(avenant);
        if (!avenantDTO.getFiles().isEmpty()) {
            this.fileManagerService.fileUploading( avenant.getId(), TypeDossier.AVENANT, avenantDTO.getFiles() );
        }
        return avenantMapper.toDto(avenant);
    }

    /**
     * Get all the avenants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AvenantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Avenants");
        return avenantRepository.findAll(pageable)
            .map(avenantMapper::toDto);
    }


    /**
     * Get one avenant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AvenantDTO> findOne(Long id) {
        log.debug("Request to get Avenant : {}", id);
        Optional<AvenantDTO> avenantDTO = avenantRepository.findById(id).map(avenantMapper::toDto);
        if(avenantDTO.isPresent()) {
            avenantDTO.get().setFiles( this.fileManagerService.getEntityDataFile( avenantDTO.get().getId(), TypeDossier.AVENANT ) );
        }
        return avenantDTO;
    }

    /**
     * Delete the avenant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Avenant : {}", id);
        avenantRepository.deleteById(id);
    }

    public List<AvenantDTO> findAllAvenantByContrat(Long contratId) {
        return avenantRepository.findAll().stream().filter(avenant -> avenant.getContrat() != null && avenant.getContrat().getId().equals(contratId)
            && avenant.isDeleted() != null && !avenant.isDeleted()).map(avenantMapper::toDto).collect(Collectors.toList());
    }
}
