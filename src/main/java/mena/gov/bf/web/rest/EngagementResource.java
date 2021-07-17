package mena.gov.bf.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import mena.gov.bf.service.EngagementService;
import mena.gov.bf.service.dto.EngagementDTO;
import mena.gov.bf.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link mena.gov.bf.domain.Engagement}.
 */
@RestController
@RequestMapping("/api")
public class EngagementResource {

    private final Logger log = LoggerFactory.getLogger(EngagementResource.class);

    private static final String ENTITY_NAME = "microserviceexecutionEngagement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EngagementService engagementService;

    public EngagementResource(EngagementService engagementService) {
        this.engagementService = engagementService;
    }

    /**
     * {@code POST  /contrats} : Create a new engagement.
     *
     * @param engagementDTO the engagementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contratDTO, or with status {@code 400 (Bad Request)} if the contrat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/engagements")
    public ResponseEntity<EngagementDTO> createEngagement(@Valid @RequestBody EngagementDTO engagementDTO) {
        log.debug("REST request to save Contrat : {}", engagementDTO);
        if (engagementDTO.getId() != null) {
            throw new BadRequestAlertException("A new engagement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EngagementDTO result = engagementService.save(engagementDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
                        ""))
                .body(result);
    }

    /**
     * {@code PUT  /contrats} : Updates an existing contrat.
     *
     * @param engagementDTO the engagementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contratDTO,
     * or with status {@code 400 (Bad Request)} if the contratDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contratDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/engagements")
    public ResponseEntity<EngagementDTO> updateEngagement(@Valid @RequestBody EngagementDTO engagementDTO) {
        log.debug("REST request to update Engagement : {}", engagementDTO);
        if (engagementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EngagementDTO result = engagementService.save(engagementDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ""))
                .body(result);
    }

    /**
     * {@code GET  /contrats} : get all the contrats.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contrats in body.
     */
    @GetMapping("/engagements")
    public ResponseEntity<List<EngagementDTO>> getAllEngagement(Pageable pageable) {
        log.debug("REST request to get a page of engagements");
        Page<EngagementDTO> page = engagementService.findAll(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contrats/:id} : get the "id" contrat.
     *
     * @param id the id of the engagementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the engagementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/engagements/{id}")
    public ResponseEntity<EngagementDTO> getEngagement(@PathVariable Long id) {
        log.debug("REST request to get Engagement : {}", id);
        Optional<EngagementDTO> engagementDTO = engagementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(engagementDTO);
    }

    /**
     * {@code DELETE  /contrats/:id} : delete the "id" contrat.
     *
     * @param id the id of the contratDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/engagements/{id}")
    public ResponseEntity<Void> deleteEngagement(@PathVariable Long id) {
        log.debug("REST request to delete Engagement : {}", id);
        engagementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

//    @GetMapping("/engagements/montant-a-reporter")
//    public ResponseEntity<Double> getMontantAReporter(@RequestParam(name = "activiteId") Long activiteId) {
//         double montant = engagementService.montantAEngagePourUnReportDActivite(activiteId);
//         return ResponseEntity.ok().body(montant);
//    }
    @GetMapping("/engagements/findAllEngagement")
    public ResponseEntity<List<EngagementDTO>> findAllEngagement(@RequestParam Long activiteId) {
        List<EngagementDTO> engagements = engagementService.finAllbyEngagement(activiteId);
         return ResponseEntity.ok().body(engagements);
    }

    @GetMapping("/engagements/find-all-by-avis-dac")
    public ResponseEntity<List<EngagementDTO>> findAllByAvisDac(@RequestParam Long avisDacId) {
        return ResponseEntity.ok().body(engagementService.findAllByAvisDac(avisDacId));
         // return ResponseEntity.ok().body(engagements);
    }

    @GetMapping("/engagements/find-all-by-ligne-budgetaire")
    public ResponseEntity<List<EngagementDTO>> findEngagementByLigneBudgetaire(@RequestParam Long ligneBudgetaireId) {
        return ResponseEntity.ok().body(engagementService.findEngagementByLigneBudgetaire(ligneBudgetaireId));
         // return ResponseEntity.ok().body(engagements);
    }

    @GetMapping("/engagements/find-all-by-contrat")
    public ResponseEntity<List<EngagementDTO>> findEngagementByContrat(@RequestParam Long contratId) {
        return ResponseEntity.ok().body(engagementService.findEngagementByContrat(contratId));
    }

//    @GetMapping("/engagements/engagement-besoin-ligne-budget")
//    public ResponseEntity<List<EngagementDTO>> findEngagementByBesoinLigneBudget(@PathVariable Long activiteId) {
//        return ResponseEntity.ok().body(engagementService.engagementByBesoinLigneBudge(activiteId));
//    }

    /*@GetMapping("/engagements/difference-montant")
    public ResponseEntity<Double> differenceMontant(@PathVariable BesoinLigneBudgetaireDTO blb, @PathVariable EngagementDTO e) {

        return ResponseEntity.ok(engagementService.DiffEntreMontantEstimeEtMontantEngager(blb, e));
    }*/

    @GetMapping("/engagements/difference-montant-2")
    public ResponseEntity<Double> differenceMontant2(@RequestParam Double blb, @RequestParam Double e){
        return ResponseEntity.ok(engagementService.DiffEntreMontantEstimeEtMontantEngager1(blb, e));
    }

    @PutMapping("/engagements/save-list")
    public ResponseEntity<Boolean> saveList(@RequestBody @Valid List<EngagementDTO> engagementDTOList) {
        return ResponseEntity.ok(engagementService.saveList(engagementDTOList));
    }
}
