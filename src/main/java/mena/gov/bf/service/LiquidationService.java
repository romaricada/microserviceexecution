package mena.gov.bf.service;

import mena.gov.bf.domain.Engagement;
import mena.gov.bf.domain.Liquidation;
import mena.gov.bf.domain.Penalite;
import mena.gov.bf.fileManager.FileManagerService;
import mena.gov.bf.model.TypeDossier;
import mena.gov.bf.repository.EngagementRepository;
import mena.gov.bf.repository.LiquidationRepository;
import mena.gov.bf.repository.PenaliteRepository;
import mena.gov.bf.service.dto.LiquidationDTO;
import mena.gov.bf.service.dto.PenaliteDTO;
import mena.gov.bf.service.mapper.LiquidationMapper;
import mena.gov.bf.service.mapper.PenaliteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Liquidation}.
 */
@Service
@Transactional
public class LiquidationService {

    private final Logger log = LoggerFactory.getLogger(LiquidationService.class);

    private final LiquidationRepository liquidationRepository;

    private final PenaliteRepository penaliteRepository;

    private final EngagementRepository engagementRepository;

    private final LiquidationMapper liquidationMapper;

    private final PenaliteMapper penaliteMapper;

    @Autowired
    private FileManagerService fileManagerService;

    public LiquidationService(LiquidationRepository liquidationRepository, PenaliteRepository penaliteRepository, EngagementRepository engagementRepository, LiquidationMapper liquidationMapper, PenaliteMapper penaliteMapper) {
        this.liquidationRepository = liquidationRepository;
        this.penaliteRepository = penaliteRepository;
        this.engagementRepository = engagementRepository;
        this.liquidationMapper = liquidationMapper;
        this.penaliteMapper = penaliteMapper;
    }

    /**
     * Save a liquidation.
     *
     * @param liquidationDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public LiquidationDTO save(LiquidationDTO liquidationDTO) {
        log.debug("Request to save Liquidation : {}", liquidationDTO);
        Liquidation liquidation = liquidationMapper.toEntity(liquidationDTO);
        liquidation = liquidationRepository.save(liquidation);
/*
        List<Liquidation> liquidationList = liquidationRepository.findByEngagementId(liquidationDTO.getEngagementId());
        Optional<Engagement> engagement = engagementRepository.findById(liquidation.getEngagement().getId());
        if (!liquidationList.isEmpty()){
           Double resultat = liquidationList.stream().filter(liquidation1 -> liquidation1.getEngagement()!=null &&
               liquidation1.getEngagement().getId().equals(liquidationDTO.getEngagementId()))
               .map(Liquidation::getMontant)
               .mapToDouble(Double::doubleValue).sum();
           if ((resultat + liquidationDTO.getMontant()) > engagement.get().getMontantEngage() ){
               throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le montant est supérieur au montant de l'engagement");
           }
        }*/
       System.out.println("==========liquidation=========");
       System.out.println(liquidation);
        System.out.println("==========liquidation=========");

        if (!liquidationDTO.getPenalites().isEmpty()) {
            List<Penalite> savList = new ArrayList<>();

            penaliteRepository.deleteAll(this.actualisationDesPenalites(liquidationDTO.getPenalites(), liquidationDTO.getId()));
            for (PenaliteDTO penaliteDTO : liquidationDTO.getPenalites()) {
                penaliteDTO.setLiquidationId(liquidation.getId());
                savList.add(penaliteMapper.toEntity(penaliteDTO));
            }
            this.penaliteRepository.saveAll(savList);
        }
        if (!liquidationDTO.getFiles().isEmpty()) {
            this.fileManagerService.fileUploading( liquidation.getId(), TypeDossier.LIQUIDATION, liquidationDTO.getFiles() );
        }
        return liquidationMapper.toDto(liquidation);
    }

    /**
     * Get all the liquidations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LiquidationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Liquidations");
        return liquidationRepository.findAll(pageable)
            .map(liquidationMapper::toDto);
    }


    /**
     * Get one liquidation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LiquidationDTO> findOne(Long id) {
        log.debug("Request to get Liquidation : {}", id);
        Optional<LiquidationDTO> liquidationDTO = liquidationRepository.findById(id).map(liquidationMapper::toDto);
        if(liquidationDTO.isPresent()) {
            liquidationDTO.get().setFiles( this.fileManagerService.getEntityDataFile( liquidationDTO.get().getId(), TypeDossier.LIQUIDATION ) );
        }
        return liquidationDTO;
    }

    /**
     * Delete the liquidation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Liquidation : {}", id);
        liquidationRepository.deleteById(id);
    }
    public List<LiquidationDTO> findAllLiquidationtionByContrat(Long contratId){
        return liquidationRepository.findAll().stream().filter(liquidation -> liquidation.getContrat() != null && liquidation.getContrat().getId().equals(contratId)
            && liquidation.isDeleted()!=null && !liquidation.isDeleted()).map(liquidationMapper::toDto).collect(Collectors.toList());
    }

    public List<LiquidationDTO> findAllLiquidationtionByEngagement(Long engagementId){
        return liquidationRepository.findAll().stream().filter(liquidation -> liquidation.getEngagement() != null && liquidation.getEngagement().getId().equals(engagementId)
        && !liquidation.isDeleted()).map(liquidationMapper::toDto).collect(Collectors.toList());
    }

//    public List<LiquidationDTO> findAllLiquidationtionByLots(Long lotId){
//        return liquidationRepository.findAll().stream().distinct().filter(liquidation -> liquidation.get() != null && liquidation.getContrat().getId().equals(contratId)
//            && liquidation.isDeleted()!=null && !liquidation.isDeleted()).map(liquidationMapper::toDto).collect(Collectors.toList());
//    }

   /* public Double someDesLiquidationsParActivite(Long activiteId){
        List<Liquidation> liquidationList = liquidationRepository.findAll().stream().filter(liquidation -> liquidation.getActiviteId()!=null && liquidation.getActiviteId().equals(activiteId)).collect(Collectors.toList());
        double someDesLiquidationsParActivite = liquidationList.stream().mapToDouble(Liquidation::getMontant).sum();
        log.debug("=====someDesLiquidationsParActivite===={}",someDesLiquidationsParActivite);
        return someDesLiquidationsParActivite;
    }*/

    private List<Penalite> actualisationDesPenalites(Set<PenaliteDTO> penaliteList, Long liquidationId) {
        List<Penalite> penalites = penaliteRepository.findAll()
            .stream().filter(v -> v.getLiquidationId()!=null && v.getLiquidationId().equals(liquidationId))
            .collect(Collectors.toList());
        List<Penalite> suppList = new ArrayList<>();
        if (!penalites.isEmpty()) {
            penalites.stream().filter(penalite -> (!penaliteList.stream().filter(v -> v.getId() != null && v.getId().equals(penalite.getId())).findFirst().isPresent()))
                .forEachOrdered(penalite -> {
                    suppList.add(penalite);
                });
        }
        return suppList;
    }

    public List<LiquidationDTO> deleteAll(List<LiquidationDTO> liquidationDTOS) {
        liquidationDTOS.forEach(liquidationDTO -> {
            liquidationDTO.setDeleted(true);
        });
        liquidationRepository
            .deleteAll(liquidationDTOS.stream().map(liquidationMapper::toEntity).collect(Collectors.toList()));
        return liquidationDTOS;
    }
}
