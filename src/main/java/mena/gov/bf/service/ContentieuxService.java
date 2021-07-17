package mena.gov.bf.service;

import mena.gov.bf.domain.Contentieux;
import mena.gov.bf.domain.DecisionContentieux;
import mena.gov.bf.fileManager.FileManagerService;
import mena.gov.bf.model.TypeDossier;
import mena.gov.bf.repository.ContentieuxRepository;
import mena.gov.bf.repository.DecisionContentieuxRepository;
import mena.gov.bf.service.dto.ContentieuxDTO;
import mena.gov.bf.service.dto.DecisionContentieuxDTO;
import mena.gov.bf.service.mapper.ContentieuxMapper;
import mena.gov.bf.service.mapper.DecisionContentieuxMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Contentieux}.
 */
@Service
@Transactional
public class ContentieuxService {

    private final Logger log = LoggerFactory.getLogger(ContentieuxService.class);

    private final ContentieuxRepository contentieuxRepository;

    private final ContentieuxMapper contentieuxMapper;

    @Autowired
    private FileManagerService fileManagerService;

    private final DecisionContentieuxMapper decisionContentieuxMapper;

    private final DecisionContentieuxRepository decisionContentieuxRepository;

    public ContentieuxService(ContentieuxRepository contentieuxRepository, ContentieuxMapper contentieuxMapper, DecisionContentieuxMapper decisionContentieuxMapper, DecisionContentieuxRepository decisionContentieuxRepository) {
        this.contentieuxRepository = contentieuxRepository;
        this.contentieuxMapper = contentieuxMapper;
        this.decisionContentieuxMapper = decisionContentieuxMapper;
        this.decisionContentieuxRepository = decisionContentieuxRepository;
    }

    /**
     * Save a contentieux.
     *
     * @param contentieuxDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public ContentieuxDTO save(ContentieuxDTO contentieuxDTO) {

        log.debug("Request to save Contentieux : {}", contentieuxDTO);
        Contentieux contentieux = contentieuxMapper.toEntity(contentieuxDTO);
        contentieux = contentieuxRepository.save(contentieux);

        if (!contentieuxDTO.getDecisionContentieuxes().isEmpty()) {
            List<DecisionContentieux> savList = new ArrayList<>();
            contentieuxDTO.getDecisionContentieuxes().forEach(decisionContentieuxDTO -> {
                if (decisionContentieuxDTO.getId()==null){
                    decisionContentieuxRepository.deleteAll(this.actualisationDesDecisionsContentieux(contentieuxDTO.getDecisionContentieuxes(), contentieuxDTO.getId()));
                }
            });

            for (DecisionContentieuxDTO decisionContentieuxDTO : contentieuxDTO.getDecisionContentieuxes()) {
                decisionContentieuxDTO.setContentieuxId(contentieux.getId());
                savList.add(decisionContentieuxMapper.toEntity(decisionContentieuxDTO));
            }
            decisionContentieuxRepository.saveAll(savList);
        }

        if (!contentieuxDTO.getFiles().isEmpty()) {
            this.fileManagerService.fileUploading( contentieux.getId(), TypeDossier.CONTENTIEUX, contentieuxDTO.getFiles() );
        }
        return contentieuxMapper.toDto(contentieux);
    }

    private List<DecisionContentieux> actualisationDesDecisionsContentieux(Set<DecisionContentieuxDTO> decisionContentieuxList, Long contentieuxId) {
        List<DecisionContentieux> decisionContentieuxes = decisionContentieuxRepository.findAll()
            .stream().filter(v -> v.getId()!=null && v.getId().equals(contentieuxId))
            .collect(Collectors.toList());
        List<DecisionContentieux> suppList = new ArrayList<>();

        if (!decisionContentieuxes.isEmpty()) {
            decisionContentieuxes.stream().filter(decisionContentieux -> (!decisionContentieuxList.stream().filter(v -> v.getId() != null && v.getId().equals(decisionContentieux.getId())).findFirst().isPresent()))
                .forEachOrdered(decisionContentieux -> {
                    suppList.add(decisionContentieux);
                });
        }
        return suppList;
    }

    /**
     * Get all the contentieuxes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContentieuxDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contentieuxes");
        return contentieuxRepository.findAll(pageable)
            .map(contentieuxMapper::toDto);
    }


    /**
     * Get one contentieux by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContentieuxDTO> findOne(Long id) {
        log.debug("Request to get Contentieux : {}", id);
        Optional<ContentieuxDTO> contentieuxDTO = contentieuxRepository.findById(id).map(contentieuxMapper::toDto);
        if(contentieuxDTO.isPresent()) {
            contentieuxDTO.get().setFiles( this.fileManagerService.getEntityDataFile( contentieuxDTO.get().getId(), TypeDossier.CONTENTIEUX ) );
        }
        return contentieuxDTO;
    }

    /**
     * Delete the contentieux by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Contentieux : {}", id);
        contentieuxRepository.deleteById(id);
    }

    public List<ContentieuxDTO> findAllContentieuxByContrat(Long contratId){
        return contentieuxRepository.findAll().stream().filter(contentieux -> contentieux.getContrat() != null && contentieux.getContrat().getId().equals(contratId)
            && contentieux.isDeleted()!=null && !contentieux.isDeleted()).map(contentieuxMapper::toDto).collect(Collectors.toList());
    }

}
