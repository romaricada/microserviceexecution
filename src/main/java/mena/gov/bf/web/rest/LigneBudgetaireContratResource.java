package mena.gov.bf.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import mena.gov.bf.service.LigneBudgetaireContratService;
import mena.gov.bf.service.PenaliteService;
import mena.gov.bf.service.dto.ContratDTO;
import mena.gov.bf.service.dto.LigneBudgetaireContratDTO;
import mena.gov.bf.service.dto.PenaliteDTO;
import mena.gov.bf.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LigneBudgetaireContratResource {
    private final Logger log = LoggerFactory.getLogger(LigneBudgetaireContratResource.class);

    private static final String ENTITY_NAME = "microserviceexecutionLigneBudgetaireContrat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LigneBudgetaireContratService ligneBudgetaireContratService;

    public LigneBudgetaireContratResource(LigneBudgetaireContratService ligneBudgetaireContratService) {
        this.ligneBudgetaireContratService = ligneBudgetaireContratService;
    }

    /**
     * {@code POST  /ligneBudgetaireContrats} : Create a new ligneBudgetaireContrat.
     *
     * @param ligneBudgetaireContratDTO the penaliteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ligneBudgetaireContratDTO, or with status {@code 400 (Bad Request)} if the penalite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ligneBudgetaireContrats")
    public ResponseEntity<LigneBudgetaireContratDTO> createLigneBudgetaireContrat(@Valid @RequestBody LigneBudgetaireContratDTO ligneBudgetaireContratDTO) throws URISyntaxException {
        log.debug("REST request to save LigneBudgetaireContrat : {}", ligneBudgetaireContratDTO);
        if (ligneBudgetaireContratDTO.getId() != null) {
            throw new BadRequestAlertException("A new penalite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LigneBudgetaireContratDTO result = ligneBudgetaireContratService.save(ligneBudgetaireContratDTO);
        return ResponseEntity.created(new URI("/api/ligneBudgetaireContrats/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /ligneBudgetaireContrats} : Updates an existing penalite.
     *
     * @param ligneBudgetaireContratDTO the ligneBudgetaireContratDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligneBudgetaireContratDTO,
     * or with status {@code 400 (Bad Request)} if the ligneBudgetaireContratDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ligneBudgetaireContratDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ligneBudgetaireContrats")
    public ResponseEntity<LigneBudgetaireContratDTO> updateLigneBudgetaireContrat(@Valid @RequestBody LigneBudgetaireContratDTO ligneBudgetaireContratDTO) throws URISyntaxException {
        log.debug("REST request to update LigneBudgetaireContrat : {}", ligneBudgetaireContratDTO);
        if (ligneBudgetaireContratDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LigneBudgetaireContratDTO result = ligneBudgetaireContratService.save(ligneBudgetaireContratDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ligneBudgetaireContratDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /ligneBudgetaireContrats} : get all the ligneBudgetaireContrats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ligneBudgetaireContrats in body.
     */
    @GetMapping("/ligneBudgetaireContrats")
    public ResponseEntity<List<LigneBudgetaireContratDTO>> getAllLigneBudgetaireContrat(Pageable pageable) {
        log.debug("REST request to get a page of LigneBudgetaireContrat");
        Page<LigneBudgetaireContratDTO> page = ligneBudgetaireContratService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ligneBudgetaireContrats/:id} : get the "id" ligneBudgetaireContrat.
     *
     * @param id the id of the penaliteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the penaliteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ligneBudgetaireContrats/{id}")
    public ResponseEntity<LigneBudgetaireContratDTO> getLigneBudgetaireContrat(@PathVariable Long id) {
        log.debug("REST request to get LigneBudgetaireContrat : {}", id);
        Optional<LigneBudgetaireContratDTO> ligneBudgetaireContratDTO = ligneBudgetaireContratService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ligneBudgetaireContratDTO);
    }

    /**
     * {@code DELETE  /ligneBudgetaireContrats/:id} : delete the "id" ligneBudgetaireContrat.
     *
     * @param id the id of the ligneBudgetaireContratDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ligneBudgetaireContrats/{id}")
    public ResponseEntity<Void> deleteLigneBudgetaireContrat(@PathVariable Long id) {
        log.debug("REST request to delete LigneBudgetaireContrat : {}", id);
        ligneBudgetaireContratService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/ligneBudgetaireContrats/all-id-by-contrat")
    public ResponseEntity <List<ContratDTO>> findLigneBudgetaireByContrat(){
        return ResponseEntity.ok(ligneBudgetaireContratService.findLigneBudgetaireByContrat());
    }
}
