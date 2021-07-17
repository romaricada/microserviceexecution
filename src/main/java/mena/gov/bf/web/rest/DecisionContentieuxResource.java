package mena.gov.bf.web.rest;

import mena.gov.bf.service.DecisionContentieuxService;
import mena.gov.bf.web.rest.errors.BadRequestAlertException;
import mena.gov.bf.service.dto.DecisionContentieuxDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link mena.gov.bf.domain.DecisionContentieux}.
 */
@RestController
@RequestMapping("/api")
public class DecisionContentieuxResource {

    private final Logger log = LoggerFactory.getLogger(DecisionContentieuxResource.class);

    private static final String ENTITY_NAME = "microserviceexecutionDecisionContentieux";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DecisionContentieuxService decisionContentieuxService;

    public DecisionContentieuxResource(DecisionContentieuxService decisionContentieuxService) {
        this.decisionContentieuxService = decisionContentieuxService;
    }

    /**
     * {@code POST  /decision-contentieuxes} : Create a new decisionContentieux.
     *
     * @param decisionContentieuxDTO the decisionContentieuxDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new decisionContentieuxDTO, or with status {@code 400 (Bad Request)} if the decisionContentieux has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/decision-contentieuxes")
    public ResponseEntity<DecisionContentieuxDTO> createDecisionContentieux(@Valid @RequestBody DecisionContentieuxDTO decisionContentieuxDTO) throws URISyntaxException {
        log.debug("REST request to save DecisionContentieux : {}", decisionContentieuxDTO);
        if (decisionContentieuxDTO.getId() != null) {
            throw new BadRequestAlertException("A new decisionContentieux cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DecisionContentieuxDTO result = decisionContentieuxService.save(decisionContentieuxDTO);
        return ResponseEntity.created(new URI("/api/decision-contentieuxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /decision-contentieuxes} : Updates an existing decisionContentieux.
     *
     * @param decisionContentieuxDTO the decisionContentieuxDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated decisionContentieuxDTO,
     * or with status {@code 400 (Bad Request)} if the decisionContentieuxDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the decisionContentieuxDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/decision-contentieuxes")
    public ResponseEntity<DecisionContentieuxDTO> updateDecisionContentieux(@Valid @RequestBody DecisionContentieuxDTO decisionContentieuxDTO) throws URISyntaxException {
        log.debug("REST request to update DecisionContentieux : {}", decisionContentieuxDTO);
        if (decisionContentieuxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DecisionContentieuxDTO result = decisionContentieuxService.save(decisionContentieuxDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, decisionContentieuxDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /decision-contentieuxes} : get all the decisionContentieuxes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of decisionContentieuxes in body.
     */
    @GetMapping("/decision-contentieuxes")
    public ResponseEntity<List<DecisionContentieuxDTO>> getAllDecisionContentieuxes(Pageable pageable) {
        log.debug("REST request to get a page of DecisionContentieuxes");
        Page<DecisionContentieuxDTO> page = decisionContentieuxService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /decision-contentieuxes/:id} : get the "id" decisionContentieux.
     *
     * @param id the id of the decisionContentieuxDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the decisionContentieuxDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/decision-contentieuxes/{id}")
    public ResponseEntity<DecisionContentieuxDTO> getDecisionContentieux(@PathVariable Long id) {
        log.debug("REST request to get DecisionContentieux : {}", id);
        Optional<DecisionContentieuxDTO> decisionContentieuxDTO = decisionContentieuxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(decisionContentieuxDTO);
    }

    @GetMapping("/decision-contentieuxes/decisionContentieux-by-contentieux")
    public ResponseEntity<List<DecisionContentieuxDTO>> getDecisionContentieuxbyContentieux(@RequestParam Long contentieuxId) {
        System.out.println("===============id=================");
        System.out.println(contentieuxId);
        System.out.println("===============id=================");
        List<DecisionContentieuxDTO> decisionContentieuxDTOS = decisionContentieuxService.findDecisionContentieuxbyContentieux(contentieuxId);
        return ResponseEntity.ok(decisionContentieuxDTOS);
    }

    /**
     * {@code DELETE  /decision-contentieuxes/:id} : delete the "id" decisionContentieux.
     *
     * @param id the id of the decisionContentieuxDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/decision-contentieuxes/{id}")
    public ResponseEntity<Void> deleteDecisionContentieux(@PathVariable Long id) {
        log.debug("REST request to delete DecisionContentieux : {}", id);
        decisionContentieuxService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
