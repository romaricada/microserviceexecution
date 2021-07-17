package mena.gov.bf.web.rest;

import mena.gov.bf.bean.CandidatLot;
import mena.gov.bf.service.ContratService;
import mena.gov.bf.service.dto.StatutExecutionDTO;
import mena.gov.bf.web.rest.errors.BadRequestAlertException;
import mena.gov.bf.service.dto.ContratDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link mena.gov.bf.domain.Contrat}.
 */
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/api")
public class ContratResource {

    private final Logger log = LoggerFactory.getLogger(ContratResource.class);

    private static final String ENTITY_NAME = "microserviceexecutionContrat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContratService contratService;

    public ContratResource(ContratService contratService) {
        this.contratService = contratService;
    }

    /**
     * {@code POST  /contrats} : Create a new contrat.
     *
     * @param contratDTO the contratDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contratDTO, or with status {@code 400 (Bad Request)} if the contrat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contrats")
    public ResponseEntity<ContratDTO> createContrat(@Valid @RequestBody ContratDTO contratDTO) throws URISyntaxException {
        log.debug("REST request to save Contrat : {}", contratDTO);
        if (contratDTO.getId() != null) {
            throw new BadRequestAlertException("A new contrat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContratDTO result = contratService.save(contratDTO);
        return ResponseEntity.created(new URI("/api/contrats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contrats} : Updates an existing contrat.
     *
     * @param contratDTO the contratDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contratDTO,
     * or with status {@code 400 (Bad Request)} if the contratDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contratDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contrats")
    public ResponseEntity<ContratDTO> updateContrat(@Valid @RequestBody ContratDTO contratDTO) throws URISyntaxException {
        log.debug("REST request to update Contrat : {}", contratDTO);
        if (contratDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContratDTO result = contratService.save(contratDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contratDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contrats} : get all the contrats.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contrats in body.
     */
    @GetMapping("/contrats")
    public ResponseEntity<List<ContratDTO>> getAllContrats(Pageable pageable) {
        log.debug("REST request to get a page of Contrats");
        Page<ContratDTO> page = contratService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contrats/:id} : get the "id" contrat.
     *
     * @param id the id of the contratDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contratDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contrats/{id}")
    public ResponseEntity<ContratDTO> getContrat(@PathVariable Long id) {
        log.debug("REST request to get Contrat : {}", id);
        Optional<ContratDTO> contratDTO = contratService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contratDTO);
    }

    /**
     * Utilisé pour recuperer un contrat par son identidiant.
     * @param contratId
     * @return ResponseEntity
     */
    @GetMapping("/contrats/by-id/{contratId}")
    public ResponseEntity<ContratDTO> getContratById(@PathVariable Long contratId) {
        log.debug("REST request to get Contrat : {}", contratId);
        Optional<ContratDTO> contratDTO = contratService.findById(contratId);
        return ResponseUtil.wrapOrNotFound(contratDTO);
    }

    /**
     * {@code DELETE  /contrats/:id} : delete the "id" contrat.
     *
     * @param id the id of the contratDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contrats/{id}")
    public ResponseEntity<Void> deleteContrat(@PathVariable Long id) {
        log.debug("REST request to delete Contrat : {}", id);
        contratService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

   /* @GetMapping("/contrats/contrat-by-candidatLot")
    public ResponseEntity<ContratDTO> getContratByCandidatLot(@RequestParam(name = "candidatLotId") Long candidatLotId) {
        return ResponseEntity.ok(  ).body( contratService.findContratByCandidatLot(candidatLotId) );
    }

    @GetMapping("/contrats/contrat-by-lot")
    public ResponseEntity<List<ContratDTO>> getContratByLot(@RequestParam Long lotId) {
        return ResponseEntity.ok().body(contratService.findAllContratByLot(lotId));
    }*/

    @PutMapping("/contrats/active-marche-resilier")
    public ResponseEntity<StatutExecutionDTO> activateMarcheResilier(@Valid @RequestBody StatutExecutionDTO statutExecutionDTO) {
        StatutExecutionDTO result = contratService.activateMarcheResilier(statutExecutionDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/contrats/contratAll")
    public ResponseEntity<List<ContratDTO>> getCandidatAll(@RequestParam Long contratId) {
        return ResponseEntity.ok().body(contratService.findAllContrat(contratId));
    }

    @GetMapping("/contrats/contrat-by-exerciceId")
    public ResponseEntity<List<ContratDTO>> getContratAllbyExerciceId(@RequestParam Long exerciceId) {
        return ResponseEntity.ok().body(contratService.findAllbyExerciceId(exerciceId));
    }


    @GetMapping("/contrats/contrat-by-candidatLot")
    public ResponseEntity<ContratDTO> getContratbyCandidatLotId(@RequestParam Long candidatLotId) {
        return ResponseEntity.ok().body(contratService.findContratByCandidatLot(candidatLotId));
    }

    @GetMapping("/contrats/contrat-by-avis_dac")
    public ResponseEntity<List<ContratDTO>> getContratbyAvisDac(@RequestParam Long avisDacId) {
        return ResponseEntity.ok().body(contratService.findAllbyAvisDac(avisDacId));
    }


    @GetMapping("/contrats/contrat-by-Candidat")
    public ResponseEntity<List<ContratDTO>> findAllContratByAttributaire(
            @RequestParam Long avisDacId,
            @RequestParam Long candidatId) {

        return ResponseEntity.ok().body(contratService.findAllContratByAttributaire(avisDacId, candidatId));

    }

    @GetMapping("/contrats/candidatbyContrat")
    public ResponseEntity<CandidatLot> findCandidatLotbyContrat(@RequestParam Long contratId) {

        System.out.println("===========id1==================" +contratId);
        return ResponseEntity.ok().body(contratService.findCandidatLotbyContrat(contratId));
    }

    @PostMapping("/contrats/saveAll-Lot")
    public ResponseEntity<ContratDTO> saveAllbyLot(@RequestParam ContratDTO contratDTO){
        return ResponseEntity.ok().body(contratService.saveAllContrat(contratDTO));
    }

    @PostMapping("/contrats/saveAll-contrat")
    public ResponseEntity<ContratDTO> saveListContrat(@Valid @RequestBody ContratDTO contratDTO){

        return  null;
                //ResponseEntity.ok().body(contratService.saveListContrat(contratDTO));
    }
}
