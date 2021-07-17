package mena.gov.bf.service;

import mena.gov.bf.bean.LotDTO;
import mena.gov.bf.domain.*;
import mena.gov.bf.domain.enumeration.WordFlow;
import mena.gov.bf.proxies.BesoinLigneBudgetaireRepository;
import mena.gov.bf.proxies.LotRepository;
import mena.gov.bf.repository.*;
import mena.gov.bf.service.dto.EngagementDTO;
import mena.gov.bf.service.mapper.EngagementMapper;
import mena.gov.bf.service.mapper.LigneBudgetaireEngagementMapper;
import mena.gov.bf.utils.Exception.ApiRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service Implementation for managing {@link Engagement}.
 */
@SuppressWarnings("ALL")
@Service
@Transactional
public class EngagementService {

    private final Logger log = LoggerFactory.getLogger(EngagementService.class);

    private final EngagementRepository engagementRepository;

    private final LiquidationService liquidationService;

    private final EngagementMapper engagementMapper;

    private final BesoinLigneBudgetaireRepository besoinLigneBudgetaireRepository;

    private final ContratService contratService;

    private final ContratRepository contratRepository;

    private final LotRepository lotRepository;

    private final LigneBudgetaireEngagementRepository ligneBudgetaireEngagementRepository;

    private final LigneBudgetaireEngagementMapper ligneBudgetaireEngagementMapper;

    private final EtapeTraitementRepository etapeTraitementRepository;

    private final EtapeRepository etapeRepository;

    public EngagementService(EngagementRepository engagementRepository,
                             LiquidationService liquidationService,
                             EngagementMapper engagementMapper,
                             BesoinLigneBudgetaireRepository besoinLigneBudgetaireRepository,
                             ContratService contratService, ContratRepository contratRepository, LotRepository lotRepository,
                             LigneBudgetaireEngagementRepository ligneBudgetaireEngagementRepository,
                             LigneBudgetaireEngagementMapper ligneBudgetaireEngagementMapper, EtapeTraitementRepository etapeTraitementRepository, EtapeRepository etapeRepository) {
        this.engagementRepository = engagementRepository;
        this.liquidationService = liquidationService;
        this.engagementMapper = engagementMapper;
        this.besoinLigneBudgetaireRepository = besoinLigneBudgetaireRepository;
        this.contratService = contratService;
        this.contratRepository = contratRepository;
        this.lotRepository = lotRepository;
        this.ligneBudgetaireEngagementRepository = ligneBudgetaireEngagementRepository;
        this.ligneBudgetaireEngagementMapper = ligneBudgetaireEngagementMapper;
        this.etapeTraitementRepository = etapeTraitementRepository;
        this.etapeRepository = etapeRepository;
    }

    /**
     * Save a engagement.
     *
     * @param engagementDTO the entity to save.
     * @return the persisted entity.
     */
    public EngagementDTO save(EngagementDTO engagementDTO) {
        log.debug("Request to save Engagement : {}", engagementDTO);
        Engagement engagement = engagementMapper.toEntity(engagementDTO);
        List<Engagement> engagementList = engagementRepository.findByContratId(engagement.getContratId());
        Optional<Contrat> contrat = contratRepository.findById(engagement.getContratId());
        if (contrat.isPresent()) {
            Double somme = engagementList.stream()
                    .filter(v -> !v.getLigneBudgetaireContrat().getId()
                            .equals(engagementDTO.getLigneBudgetaireContratId()))
                    .map(Engagement::getMontantEngage)
                    .mapToDouble(Double::doubleValue).sum();
            if ((somme + engagement.getMontantEngage()) > contrat.get().getMontant()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le montant est supérieur au montant du contrat");
            }
            if (engagement.getId() != null) {
                if (!engagementList.stream().anyMatch(v -> v.getLigneBudgetaireContrat().getId()
                        .equals(engagementDTO.getLigneBudgetaireContratId()))) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "La modification est impossible sur une ligne différente");
                } else {
                    log.debug("--Le traitement continue--");
                }
            } else {
                if (engagementList.stream().anyMatch(v -> v.getLigneBudgetaireContrat().getId()
                        .equals(engagementDTO.getLigneBudgetaireContratId()))) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Cette ligne est déjà indexé");
                } else {
                    log.debug("--Le traitement continue--");
                }
            }
            engagement = engagementRepository.save(engagement);
            this.saveWordFlowContrat(engagementDTO.getWordFlow(), engagement);
            return engagementMapper.toDto(engagement);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun contrat associé à l'engagement");
        }
    }

    /**
     * Get all the engagements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EngagementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Engagement");

        List<EngagementDTO> engagementDTOList = engagementRepository.findAll()
                .stream()
                .map(engagementMapper::toDto)
                .filter(engagementDTO -> engagementDTO.getDeleted() != null && !engagementDTO.getDeleted())
                .collect(Collectors.toList());
        engagementDTOList.forEach(engagementDTO -> engagementDTO.setContrat(contratService.findAllContrat(engagementDTO.getContratId())));
        engagementDTOList.forEach(engagementDTO -> engagementDTO.setLot(lotRepository.findAllLot(engagementDTO.getLotId())));
        engagementDTOList.forEach(engagementDTO -> engagementDTO.setBesoinLigneBudgetaire(besoinLigneBudgetaireRepository.findAllBesoinLigneBudgetaireByActivite(engagementDTO.getAvisDacId())));

        engagementDTOList.forEach(engagementDTO -> {
            engagementDTO.setContratEn(contratService.findOneContrat(engagementDTO.getContratId()).get(0));
        });
        engagementDTOList.forEach(engagementDTO -> {
            LotDTO lot = lotRepository.findAllInOneLot(engagementDTO.getLotId()).get(0);
            engagementDTO.setLotEn(lotRepository.findAllInOneLot(engagementDTO.getLotId()).get(0));
        });
        return new PageImpl<>(engagementDTOList, pageable, engagementDTOList.size());
    }


    /**
     * Get one engagement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EngagementDTO> findOne(Long id) {
        log.debug("Request to get Engagement : {}", id);
        return engagementRepository.findById(id)
                .map(engagementMapper::toDto);
    }

    /**
     * Delete the engagement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Engagement : {}", id);
        engagementRepository.deleteById(id);
    }

    public Double someMontantEngagementParActivite(Long activiteId) {
        /*log.debug("===activiteId===={}",activiteId);
         List<Engagement> engagementDTOS = engagementRepository.findAll().stream().filter(engagement -> engagement.getActiviteId()!=null && engagement.getActiviteId().equals(activiteId)).collect(Collectors.toList());
         double someMontantEngage = engagementDTOS.stream().mapToDouble(Engagement::getMontantEngage).sum();
         log.debug("====engagementDTOS======={}",engagementDTOS);
         log.debug("====someMontantEngage======={}",someMontantEngage);
         return someMontantEngage;*/
        return null;
    }
//
//    public Double montantAEngagePourUnReportDActivite(Long activiteId) {
//        double montant = (this.someMontantEngagementParActivite(activiteId) - liquidationService.someDesLiquidationsParActivite(activiteId));
//        log.debug("====montant======={}", montant);
//        return montant;
//    }

    public List<EngagementDTO> finAllbyEngagement(Long activiteId) {
        /*List<EngagementDTO> engagementDTOList = engagementRepository.findAll().stream().filter(engagement ->
            engagement.getActiviteId()!=null && engagement.getActiviteId().equals(activiteId)).map(engagementMapper::toDto).
            collect(Collectors.toList());
        log.debug("====engagements======", engagementDTOList);

        return engagementDTOList;*/
        return null;
    }

    public List<EngagementDTO> findAllByAvisDac(Long avisDacId) {
        List<EngagementDTO> engagementDTOS = engagementRepository.findAll().stream().filter(engagement ->
                engagement.getAvisDacId() != null && engagement.getAvisDacId().equals(avisDacId)).map(engagementMapper::toDto).collect(Collectors.toList());
        return engagementDTOS;
    }

    /* public Double DiffEntreMontantEstimeEtMontantEngager (BesoinLigneBudgetaireDTO blb, EngagementDTO e) {
        if (blb.getMontantEstime()  <  e.getMontantEngage()) {
            return null;
        }
        return blb.getMontantEstime() - e.getMontantEngage();


    }
    */
    public Double DiffEntreMontantEstimeEtMontantEngager1(Double blb, Double e) {
        if (blb < e) {
            return null;
        }
        return blb - e;

    }

    public List<EngagementDTO> findEngagementByLigneBudgetaire(Long ligneBudgetaireId) {
        return engagementRepository.findAll().stream().filter(engagement -> engagement.getLigneBudgetaireId() != null
                && engagement.getLigneBudgetaireId().equals(ligneBudgetaireId)).map(engagementMapper::toDto).collect(Collectors.toList());
    }

    public List<EngagementDTO> findEngagementByContrat(final Long idContrat) {
        return engagementRepository.findAll().stream().filter(v -> v.getLigneBudgetaireContrat() != null &&
                v.getLigneBudgetaireContrat().getContrat().getId().equals(idContrat))
                .map(engagementMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Enregistrement de liste d'engagement.
     *
     * @param engagementDTOList
     * @return boolean
     */
    public boolean saveList(List<EngagementDTO> engagementDTOList) {
        engagementDTOList.forEach(v -> {
            Engagement engagement = engagementRepository.save(engagementMapper.toEntity(v));
            this.saveWordFlowContrat(v.getWordFlow(), engagement);
        });
        return Boolean.TRUE;
    }

    private void saveWordFlowContrat(String wordFlow, Engagement engagement) {
        Optional<Etape> etape = etapeRepository.findById(wordFlow);
        if (etape.isPresent()) {
            EtapeTraitement contratEtape = new EtapeTraitement();
            switch (wordFlow) {
                case WordFlow.ENGAGEMENT:
                    contratEtape.setEngagement(engagement);
                    contratEtape.setActif(Boolean.TRUE);
                    contratEtape.setDate(LocalDate.now());
                    contratEtape.setEtape(etape.get());
                    contratEtape.setValide(Boolean.TRUE);
                    break;
                case WordFlow.VISA_CONTROLEUR:
                    contratEtape.setEngagement(engagement);
                    contratEtape.setActif(Boolean.TRUE);
                    contratEtape.setDate(LocalDate.now());
                    contratEtape.setEtape(etape.get());
                    contratEtape.setValide(Boolean.TRUE);
                    break;
                case WordFlow.VISA_ORDONNATEUR:
                    contratEtape.setEngagement(engagement);
                    contratEtape.setActif(Boolean.TRUE);
                    contratEtape.setDate(LocalDate.now());
                    contratEtape.setEtape(etape.get());
                    contratEtape.setValide(Boolean.TRUE);
                    break;
                case WordFlow.APPROUVER:
                    contratEtape.setEngagement(engagement);
                    contratEtape.setActif(Boolean.TRUE);
                    contratEtape.setDate(LocalDate.now());
                    contratEtape.setEtape(etape.get());
                    contratEtape.setValide(Boolean.TRUE);
                    break;
                case WordFlow.REJETER:
                    contratEtape.setEngagement(engagement);
                    contratEtape.setActif(Boolean.TRUE);
                    contratEtape.setDate(LocalDate.now());
                    contratEtape.setEtape(etape.get());
                    contratEtape.setValide(Boolean.TRUE);
                    break;
                case WordFlow.VALIDER:
                    contratEtape.setEngagement(engagement);
                    contratEtape.setActif(Boolean.TRUE);
                    contratEtape.setDate(LocalDate.now());
                    contratEtape.setEtape(etape.get());
                    contratEtape.setValide(Boolean.TRUE);
                    break;
            }
            // initialiser le worflow
            List<EtapeTraitement> contratEtapeList = etapeTraitementRepository.findAllByEngagementId(engagement.getId());
            contratEtapeList = contratEtapeList.stream().flatMap(l -> {
                l.setActif(Boolean.FALSE);
                return Stream.of(l);
            }).collect(Collectors.toList());
            etapeTraitementRepository.saveAll(contratEtapeList);
            // verifier si c'est une mise à jour
            Optional<EtapeTraitement> contratEtapeOptional = etapeTraitementRepository
                    .findTo1pByEngagementIdAndEtapeId(engagement.getId(), etape.get().getId());
            contratEtapeOptional.ifPresent(contratEtape1 -> contratEtape.setId(contratEtape1.getId()));
            etapeTraitementRepository.save(contratEtape);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune étape trouvé");
        }
    }

}
