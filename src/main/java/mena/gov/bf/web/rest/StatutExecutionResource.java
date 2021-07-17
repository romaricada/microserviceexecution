package mena.gov.bf.web.rest;

import mena.gov.bf.service.StatutExecutionService;
import mena.gov.bf.web.rest.errors.BadRequestAlertException;
import mena.gov.bf.service.dto.StatutExecutionDTO;

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
 * REST controller for managing {@link mena.gov.bf.domain.StatutExecution}.
 */
@RestController
@RequestMapping("/api")
public class StatutExecutionResource {

    private final Logger log = LoggerFactory.getLogger(StatutExecutionResource.class);

    private static final String ENTITY_NAME = "microserviceexecutionStatutExecution";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatutExecutionService statutExecutionService;

    public StatutExecutionResource(StatutExecutionService statutExecutionService) {
        this.statutExecutionService = statutExecutionService;
    }

    /**
     * {@code POST  /statut-executions} : Create a new statutExecution.
     *
     * @param statutExecutionDTO the statutExecutionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statutExecutionDTO, or with status {@code 400 (Bad Request)} if the statutExecution has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/statut-executions")
    public ResponseEntity<StatutExecutionDTO> createStatutExecution(@Valid @RequestBody StatutExecutionDTO statutExecutionDTO) throws URISyntaxException {
        log.debug("REST request to save StatutExecution : {}", statutExecutionDTO);
        if (statutExecutionDTO.getId() != null) {
            throw new BadRequestAlertException("A new statutExecution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatutExecutionDTO result = statutExecutionService.save(statutExecutionDTO);
        return ResponseEntity.created(new URI("/api/statut-executions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /statut-executions} : Updates an existing statutExecution.
     *
     * @param statutExecutionDTO the statutExecutionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statutExecutionDTO,
     * or with status {@code 400 (Bad Request)} if the statutExecutionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statutExecutionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/statut-executions")
    public ResponseEntity<StatutExecutionDTO> updateStatutExecution(@Valid @RequestBody StatutExecutionDTO statutExecutionDTO) throws URISyntaxException {
        log.debug("REST request to update StatutExecution : {}", statutExecutionDTO);
        if (statutExecutionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StatutExecutionDTO result = statutExecutionService.save(statutExecutionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statutExecutionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /statut-executions} : get all the statutExecutions.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statutExecutions in body.
     */
    @GetMapping("/statut-executions")
    public ResponseEntity<List<StatutExecutionDTO>> getAllStatutExecutions(Pageable pageable) {
        log.debug("REST request to get a page of StatutExecutions");
        Page<StatutExecutionDTO> page = statutExecutionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /statut-executions/:id} : get the "id" statutExecution.
     *
     * @param id the id of the statutExecutionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statutExecutionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/statut-executions/{id}")
    public ResponseEntity<StatutExecutionDTO> getStatutExecution(@PathVariable Long id) {
        log.debug("REST request to get StatutExecution : {}", id);
        Optional<StatutExecutionDTO> statutExecutionDTO = statutExecutionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statutExecutionDTO);
    }

    /**
     * {@code DELETE  /statut-executions/:id} : delete the "id" statutExecution.
     *
     * @param id the id of the statutExecutionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/statut-executions/{id}")
    public ResponseEntity<Void> deleteStatutExecution(@PathVariable Long id) {
        log.debug("REST request to delete StatutExecution : {}", id);
        statutExecutionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/statut-executions/statut-by-contrat")
    public ResponseEntity<List<StatutExecutionDTO>> getAllStatutExecutionByContrat(@RequestParam Long contratId) {
        List<StatutExecutionDTO> statutExecutionDTOS = statutExecutionService.findAllStatutexecutionByContrat(contratId);
        return ResponseEntity.ok().body(statutExecutionDTOS);
    }

    @PutMapping("/statut-executions/active-marche-resilier")
    public ResponseEntity<StatutExecutionDTO> activateMarcheResilier(@Valid @RequestBody StatutExecutionDTO statutExecutionDTO) {
        StatutExecutionDTO result = statutExecutionService.activateMarcheResilier(statutExecutionDTO);
        return ResponseEntity.ok().body(result);
    }
}
