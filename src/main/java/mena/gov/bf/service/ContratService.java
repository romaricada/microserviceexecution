package mena.gov.bf.service;

import mena.gov.bf.bean.*;
import mena.gov.bf.domain.*;
import mena.gov.bf.domain.enumeration.WordFlow;
import mena.gov.bf.fileManager.FileManagerService;
import mena.gov.bf.model.TypeDossier;
import mena.gov.bf.proxies.BesoinLigneBudgetaireRepository;
import mena.gov.bf.proxies.CandidatLotRepository;
import mena.gov.bf.proxies.LotRepository;
import mena.gov.bf.repository.*;
import mena.gov.bf.service.dto.ContratDTO;
import mena.gov.bf.service.dto.LigneBudgetaireContratDTO;
import mena.gov.bf.service.dto.StatutExecutionDTO;
import mena.gov.bf.service.mapper.ContratMapper;
import mena.gov.bf.service.mapper.LigneBudgetaireContratMapper;
import mena.gov.bf.service.mapper.StatutExecutionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service Implementation for managing {@link Contrat}.
 */
@Service
@Transactional
public class ContratService {

    private final Logger log = LoggerFactory.getLogger(ContratService.class);

    private final ContratRepository contratRepository;

    private final LotRepository lotRepository;

    private final ContratMapper contratMapper;

    private final CandidatLotRepository candidatLotRepository;

    private final StatutExecutionRepository statutExecutionRepository;

    private final StatutExecutionMapper statutExecutionMapper;

    private final EtapeTraitementRepository etapeTraitementRepository;

    private  final FileManagerService fileManagerService;

    private final LigneBudgetaireContratRepository ligneBudgetaireContratRepository;

    private final LigneBudgetaireContratMapper ligneBudgetaireContratMapper;

    private final EtapeRepository etapeRepository;

    private final BesoinLigneBudgetaireRepository besoinLigneBudgetaireRepository;


    public ContratService(ContratRepository contratRepository, LotRepository lotRepository, StatutExecutionRepository statutExecutionRepository, StatutExecutionMapper statutExecutionMapper,
                          ContratMapper contratMapper, CandidatLotRepository candidatLotRepository, EtapeTraitementRepository etapeTraitementRepository, FileManagerService fileManagerService, LigneBudgetaireContratRepository ligneBudgetaireContratRepository, LigneBudgetaireContratMapper ligneBudgetaireContratMapper, EtapeRepository etapeRepository,
                          BesoinLigneBudgetaireRepository besoinLigneBudgetaireRepository) {
        this.contratRepository = contratRepository;
        this.lotRepository = lotRepository;
        this.statutExecutionRepository = statutExecutionRepository;
        this.statutExecutionMapper = statutExecutionMapper;
        this.contratMapper = contratMapper;
        this.candidatLotRepository = candidatLotRepository;
        this.etapeTraitementRepository = etapeTraitementRepository;
        this.fileManagerService = fileManagerService;
        this.ligneBudgetaireContratRepository = ligneBudgetaireContratRepository;
        this.ligneBudgetaireContratMapper = ligneBudgetaireContratMapper;
        this.etapeRepository = etapeRepository;
        this.besoinLigneBudgetaireRepository = besoinLigneBudgetaireRepository;
    }

    /**
     * Save a contrat.
     *
     * @param contratDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public ContratDTO save(ContratDTO contratDTO) {
        log.debug("Request to save Contrat : {}", contratDTO);
        Contrat contrat = contratMapper.toEntity(contratDTO);
        contrat = contratRepository.save(contrat);
        if (!contratDTO.getLigneBudgetaireContrats().isEmpty()) {
            // Actualisé la liste
            ligneBudgetaireContratRepository.deleteAll(deleteLigneContrat(contratDTO.getLigneBudgetaireContrats(), contrat.getId()));
            List<LigneBudgetaireContrat> ligneBudgetaireContratList = new ArrayList<>();
            for (LigneBudgetaireContratDTO ligneBudgetaireContratDTO : contratDTO.getLigneBudgetaireContrats()) {
                ligneBudgetaireContratDTO.setContratId(contrat.getId());
                ligneBudgetaireContratList.add(ligneBudgetaireContratMapper.toEntity(ligneBudgetaireContratDTO));
            }
            ligneBudgetaireContratRepository.saveAll(ligneBudgetaireContratList);
        }
        // To save wordFlow
        this.saveWordFlowContrat(contratDTO.getWordFlow(), contrat);

        if (!contratDTO.getFiles().isEmpty()) {
            this.fileManagerService.fileUploading(contrat.getId(), TypeDossier.CONTRAT, contratDTO.getFiles());
        }
        return contratMapper.toDto(contrat);
    }


    /**
     * Pour actualiser la liste des ligne à la modification.
     * @param newList
     * @return List<LigneBudgetaireContrat> à supprimer.
     */
    private List<LigneBudgetaireContrat> deleteLigneContrat(Set<LigneBudgetaireContratDTO> newList, Long contratId) {
        List<LigneBudgetaireContrat> existeListe = ligneBudgetaireContratRepository.findAll().stream().
            filter(ligneBudgetaireContrat -> ligneBudgetaireContrat.getContrat()!=null && ligneBudgetaireContrat.getContrat().getId().equals(contratId) ).collect(Collectors.toList());
        List<LigneBudgetaireContrat> suppList = new ArrayList<>();
        if (!existeListe.isEmpty()) {
            existeListe.forEach(old -> {
                if (!newList.stream().filter(v -> v.getId() != null && v.getId().equals(old.getId())).findFirst().isPresent()) {
                    suppList.add(old);
                }
            });
        }
        return suppList;
    }

    /**
     * Get all the contrats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContratDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contrats");
        return contratRepository.findAll(pageable)
            .map(contratMapper::toDto);
    }


    /**
     * Get one contrat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContratDTO> findOne(Long id) {
        log.debug("Request to get Contrat : {}", id);
        Optional<ContratDTO> contratDTO = contratRepository.findById(id).map(contratMapper::toDto);
        if(contratDTO.isPresent()) {
            contratDTO.get().setFiles( this.fileManagerService.getEntityDataFile( contratDTO.get().getId(), TypeDossier.CONTRAT ) );
        }
        return contratDTO;
    }

    public Optional<ContratDTO> findById(Long id) {
        log.debug("Request to get Contrat : {}", id);
        Optional<ContratDTO> contratDTO = contratRepository.findById(id).map(contratMapper::toDto);
        if (contratDTO.isPresent()) {
            return contratDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Identifiant inexistant");
        }
    }


    /**
     * Delete the contrat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Contrat : {}", id);
        contratRepository.deleteById(id);
    }

    @Transactional
    public StatutExecutionDTO activateMarcheResilier(StatutExecutionDTO statutExecutionDTO) {
        if (statutExecutionDTO.getContratId()!=null) {
            Contrat contrat= contratRepository.getOne(statutExecutionDTO.getContratId());
            contrat.setResilierContrat(true);
            contratRepository.save(contrat);
        }
        StatutExecution statutExecution = statutExecutionMapper.toEntity( statutExecutionDTO );
        statutExecutionDTO.setContratResilier(true);
        statutExecution = statutExecutionRepository.save( statutExecution );
        return statutExecutionMapper.toDto( statutExecution );
    }


    public List<ContratDTO> findAllContrat(Long contratId) {
        return contratRepository.findAllByDeletedIsFalse()
            .stream()
            .filter(contrat1 -> contrat1.getId() == contratId)
            .map(contratMapper::toDto).collect(Collectors.toList());
    }

    public List<ContratDTO> findAllbyExerciceId(Long exerciceId){
        return contratRepository.findAll().stream().filter(contrat -> contrat.getExerciceId()!=null
        && contrat.getExerciceId().equals(exerciceId) && !contrat.isDeleted()).map(contratMapper::toDto).collect(Collectors.toList());
    }


    @Transactional
    public ContratDTO saveAllContrat(ContratDTO contratDTO) {
       /* Contrat contrat = contratRepository.save(contratMapper.toEntity(contratDTO));
        contratDTO.getCandidatLots().forEach(candidatLot -> {
            candidatLot.setContratId(contrat.getId());
            candidatLot.setDeleted(false);
        });

        ContratDTO contratDTO1 = contratMapper.toDto(contrat);
        contratDTO1.setCandidatLots(candidatLotRepository.saveAllCandidatLot(contratDTO.getCandidatLots()));

        return contratDTO1;
    }


    public ContratDTO saveListContrat(ContratDTO contratDTO) {
        contratDTO.getCandidatLots().forEach(candidatLot -> {
            contratDTO.setCandidatLot(candidatLot);
            Contrat contrat = contratRepository.save(contratMapper.toEntity(contratDTO));
            System.out.println("===================="+contrat);

        });*/
        return null;
    }

    public  CandidatLot findCandidatLotbyContrat(Long contratId){

        List<CandidatLot> candidatLots =candidatLotRepository.findCandidatLot().stream()
            .filter(candidatLot -> candidatLot.getDeleted()!=null && !candidatLot.getDeleted() && candidatLot.getContratId().equals(contratId))
            .collect(Collectors.toList());
        return candidatLots.get(0);
    }

    public List<ContratDTO> findOneContrat(Long contratId){
        return contratRepository.findAll().stream().filter(contrat -> contrat.getId()!=null && contrat.getId().equals(contratId)).
            map(contratMapper::toDto).collect(Collectors.toList());
    }

    public List<Lot> findOneLot(Long lotId){
        return lotRepository.findAllLot(lotId);
    }

    /**
     * Liste des contrats par activite et par contrat.
     * @param avisdacId
     * @param candidatId
     * @return List<ContratDTO>
     */
    public List<ContratDTO> findAllContratByAttributaire(Long avisdacId, Long candidatId) {
        return contratRepository.findAll().stream()
                .filter(val -> val.isDeleted() != null
                        && val.getAvisDacId().equals(avisdacId)
                        && val.getCandidaId().equals(candidatId)).map(contratMapper::toDto)
                .collect(Collectors.toList());
    }

    public ContratDTO findContratByCandidatLot (Long candidatLotId) {
    Optional<ContratDTO>  contratDTO = contratRepository.findAll().stream().filter(contrat -> contrat.isDeleted() != null
        && contrat.getCandidatLotsId().equals(candidatLotId)).map(contratMapper::toDto).findFirst();
        return contratDTO.orElse(null);

    }

    public List<ContratDTO> findAllbyAvisDac(Long avisDacId){
        return contratRepository.findAll().stream().filter(contrat -> contrat.getAvisDacId()!=null && !contrat.isDeleted()
        && contrat.getAvisDacId().equals(avisDacId)).map(contratMapper::toDto).collect(Collectors.toList());
    }

    private void saveWordFlowContrat(String wordFlow, Contrat contrat) {
        Optional<Etape> etape = etapeRepository.findById(wordFlow);
        if (etape.isPresent()) {
            EtapeTraitement contratEtape = new EtapeTraitement();
            switch (wordFlow) {
                case WordFlow.AVANT_PROJET:
                    contratEtape.setContrat(contrat);
                    contratEtape.setActif(Boolean.TRUE);
                    contratEtape.setDate(LocalDate.now());
                    contratEtape.setEtape(etape.get());
                    contratEtape.setValide(Boolean.TRUE);
                    break;
                case WordFlow.TRANS_SOUMISSIONNAIRE:
                    contratEtape.setContrat(contrat);
                    contratEtape.setActif(Boolean.TRUE);
                    contratEtape.setDate(LocalDate.now());
                    contratEtape.setEtape(etape.get());
                    contratEtape.setValide(Boolean.TRUE);
                    break;
                case WordFlow.SIGNATURE_GESTIONNAIRE:
                    contratEtape.setContrat(contrat);
                    contratEtape.setActif(Boolean.TRUE);
                    contratEtape.setDate(LocalDate.now());
                    contratEtape.setEtape(etape.get());
                    contratEtape.setValide(Boolean.TRUE);
                    break;
                case WordFlow.RECEPTION_CONTRAT:
                    contratEtape.setContrat(contrat);
                    contratEtape.setActif(Boolean.TRUE);
                    contratEtape.setDate(LocalDate.now());
                    contratEtape.setEtape(etape.get());
                    contratEtape.setValide(Boolean.TRUE);
                    break;
                    case WordFlow.APPROUVER:
                    contratEtape.setContrat(contrat);
                    contratEtape.setActif(Boolean.TRUE);
                    contratEtape.setDate(LocalDate.now());
                    contratEtape.setEtape(etape.get());
                    contratEtape.setValide(Boolean.TRUE);
                    break;

            }
            // initialiser le worflow
            List<EtapeTraitement> contratEtapeList = etapeTraitementRepository.findAllByContratId(contrat.getId());
            contratEtapeList = contratEtapeList.stream().flatMap(l -> {
                l.setActif(Boolean.FALSE);
                return Stream.of(l);
            }).collect(Collectors.toList());
            etapeTraitementRepository.saveAll(contratEtapeList);
            // verifier si c'est une mise à jour
            Optional<EtapeTraitement> contratEtapeOptional = etapeTraitementRepository
                    .findTo1pByContratIdAndEtapeId(contrat.getId(), etape.get().getId());
            contratEtapeOptional.ifPresent(contratEtape1 -> contratEtape.setId(contratEtape1.getId()));
            etapeTraitementRepository.save(contratEtape);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune étape trouvé");
        }
    }

   /* public List<BesoinLigneBudgetaireDTO> findBesoinLigneByContrat( List<Long> blblId) {
        List <BesoinLigneBudgetaireDTO> besoinLigneBudgetaireDTO = besoinLigneBudgetaireRepository.findBesoinLigneBudgetaireByContratId(blblId);
    }*/
}
