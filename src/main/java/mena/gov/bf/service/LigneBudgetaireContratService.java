package mena.gov.bf.service;

import mena.gov.bf.bean.BesoinLigneBudgetaire;
import mena.gov.bf.bean.BesoinLigneBudgetaireDTO;
import mena.gov.bf.bean.LigneBudgetaire;
import mena.gov.bf.domain.Contrat;
import mena.gov.bf.domain.LigneBudgetaireContrat;
import mena.gov.bf.fileManager.FileManagerService;
import mena.gov.bf.model.TypeDossier;
import mena.gov.bf.proxies.BesoinLigneBudgetaireRepository;
import mena.gov.bf.proxies.LigneBudgetaireRepository;
import mena.gov.bf.repository.ContratRepository;
import mena.gov.bf.repository.LigneBudgetaireContratRepository;
import mena.gov.bf.service.dto.ContratDTO;
import mena.gov.bf.service.dto.LigneBudgetaireContratDTO;
import mena.gov.bf.service.mapper.ContratMapper;
import mena.gov.bf.service.mapper.LigneBudgetaireContratMapper;
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
import java.util.stream.Collectors;

@Service
public class LigneBudgetaireContratService {
    private final Logger log = LoggerFactory.getLogger(LigneBudgetaireContratService.class);

    private final LigneBudgetaireContratRepository ligneBudgetaireContratRepository;

    private final LigneBudgetaireContratMapper ligneBudgetaireContratMapper;

    @Autowired
    private FileManagerService fileManagerService;

    @Autowired
    private LigneBudgetaireRepository ligneBudgetaireRepository;

    @Autowired
    private ContratRepository contratRepository;
    @Autowired
    private ContratMapper contratMapper;

    @Autowired
    private BesoinLigneBudgetaireRepository besoinLigneBudgetaireRepository;

    public LigneBudgetaireContratService(
            LigneBudgetaireContratRepository ligneBudgetaireContratRepository,
            LigneBudgetaireContratMapper ligneBudgetaireContratMapper) {
        this.ligneBudgetaireContratRepository = ligneBudgetaireContratRepository;
        this.ligneBudgetaireContratMapper = ligneBudgetaireContratMapper;
    }

    /**
     * Save a LigneBudgetaireContrat.
     *
     * @param ligneBudgetaireContratDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public LigneBudgetaireContratDTO save(LigneBudgetaireContratDTO ligneBudgetaireContratDTO) {
        log.debug("Request to save LigneBudgetaireContrat : {}", ligneBudgetaireContratDTO);
        LigneBudgetaireContrat ligneBudgetaireContrat = ligneBudgetaireContratMapper.toEntity(ligneBudgetaireContratDTO);
        ligneBudgetaireContrat = ligneBudgetaireContratRepository.save(ligneBudgetaireContrat);
        return ligneBudgetaireContratMapper.toDto(ligneBudgetaireContrat);
    }

    /**
     * Get all the LigneBudgetaireContrat.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LigneBudgetaireContratDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LigneBudgetaireContrat");
        return ligneBudgetaireContratRepository.findAll(pageable)
                .map(ligneBudgetaireContratMapper::toDto);
    }


    /**
     * Get one LigneBudgetaireContrat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LigneBudgetaireContratDTO> findOne(Long id) {
        log.debug("Request to get LigneBudgetaireContrat : {}", id);
         return ligneBudgetaireContratRepository.findById(id).map(ligneBudgetaireContratMapper::toDto);
    }

    /**
     * Delete the LigneBudgetaireContrat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LigneBudgetaireContrat : {}", id);
        ligneBudgetaireContratRepository.deleteById(id);
    }

    @Transactional
    public List<ContratDTO> findLigneBudgetaireByContrat () {
        List<ContratDTO> contrats = contratRepository.findAll().stream().map(contratMapper::toDto).collect(Collectors.toList());

    /*List <LigneBudgetaireContrat> ligneBudgetaireContrat = ligneBudgetaireContratRepository.findAll().stream().filter(ligneBudgetaireContrat1 ->
        ligneBudgetaireContrat1.getContrat() != null && ligneBudgetaireContrat1.getContrat().getId().equals(contratId)).collect(Collectors.toList());

    List<LigneBudgetaire> ligneBudgetaires = new ArrayList<>();
    if (!ligneBudgetaireContrat.isEmpty()){
        for (LigneBudgetaire ligneBudgetaire: ligneBudgetaireContrat) {
            ligneBudgetaireContrat.forEach(ligneBudgetaireContratDTO1 -> {
                ligneBudgetaireContratDTO1.setLigneBudgetaire(this.ligneBudgetaireRepository.findAllListe());
                log.debug("=====contratDTO.getLigneBudgetaires().size()======{}", ligneBudgetaireContratDTO);
        }

        });
    }
    return ligneBudgetaireContratDTO;*/
        for( ContratDTO contrat : contrats){
            for (LigneBudgetaireContratDTO ligneBudgetaireContrat : contrat.getLigneBudgetaireContrats()) {
                LigneBudgetaire ligneBudgetaire = ligneBudgetaireRepository.getLigneBudgetaire(ligneBudgetaireContrat.getLigneBudgetaireId()).get();
                List<BesoinLigneBudgetaire> besoinLigneBudgetaires = besoinLigneBudgetaireRepository.findAll().stream().filter(besoinLigneBudgetaire -> besoinLigneBudgetaire.getLigneBudgetId().equals(ligneBudgetaire.getId())).collect(Collectors.toList());
                ligneBudgetaireContrat.setLigneBudgetaire(ligneBudgetaire);
                ligneBudgetaire.setBesoinLigneBudgetaireList(besoinLigneBudgetaires);
            }
        }
      return  contrats;

    }

    public List<LigneBudgetaireContratDTO> findLignebudgetaireByContratId(Long contratId) {
        return ligneBudgetaireContratRepository.findAll().stream().filter(ligneBudgetaireContrat -> ligneBudgetaireContrat.getContrat()!= null &&
            ligneBudgetaireContrat.getContrat().getId().equals(contratId)).map(ligneBudgetaireContratMapper::toDto).collect(Collectors.toList());
    }


}
