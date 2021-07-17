package mena.gov.bf.web.rest;

import mena.gov.bf.service.EtapeExecutionService;
import mena.gov.bf.web.rest.errors.BadRequestAlertException;
import mena.gov.bf.service.dto.EtapeExecutionDTO;

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
 * REST controller for managing {@link mena.gov.bf.domain.EtapeExecution}.
 */
@RestController
@RequestMapping("/api")
public class EtapeExecutionResource {

    private final Logger log = LoggerFactory.getLogger(EtapeExecutionResource.class);

    private static final String ENTITY_NAME = "microserviceexecutionEtapeExecution";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtapeExecutionService etapeExecutionService;

    public EtapeExecutionResource(EtapeExecutionService etapeExecutionService) {
        this.etapeExecutionService = etapeExecutionService;
    }

    /**
     * {@code POST  /etape-executions} : Create a new etapeExecution.
     *
     * @param etapeExecutionDTO the etapeExecutionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etapeExecutionDTO, or with status {@code 400 (Bad Request)} if the etapeExecution has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etape-executions")
    public ResponseEntity<EtapeExecutionDTO> createEtapeExecution(@Valid @RequestBody EtapeExecutionDTO etapeExecutionDTO) throws URISyntaxException {
        log.debug("REST request to save EtapeExecution : {}", etapeExecutionDTO);
        if (etapeExecutionDTO.getId() != null) {
            throw new BadRequestAlertException("A new etapeExecution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtapeExecutionDTO result = etapeExecutionService.save(etapeExecutionDTO);
        return ResponseEntity.created(new URI("/api/etape-executions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etape-executions} : Updates an existing etapeExecution.
     *
     * @param etapeExecutionDTO the etapeExecutionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etapeExecutionDTO,
     * or with status {@code 400 (Bad Request)} if the etapeExecutionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etapeExecutionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etape-executions")
    public ResponseEntity<EtapeExecutionDTO> updateEtapeExecution(@Valid @RequestBody EtapeExecutionDTO etapeExecutionDTO) throws URISyntaxException {
        log.debug("REST request to update EtapeExecution : {}", etapeExecutionDTO);
        if (etapeExecutionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EtapeExecutionDTO result = etapeExecutionService.save(etapeExecutionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etapeExecutionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /etape-executions} : get all the etapeExecutions.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etapeExecutions in body.
     */
    @GetMapping("/etape-executions")
    public ResponseEntity<List<EtapeExecutionDTO>> getAllEtapeExecutions(Pageable pageable) {
        log.debug("REST request to get a page of EtapeExecutions");
        Page<EtapeExecutionDTO> page = etapeExecutionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etape-executions/:id} : get the "id" etapeExecution.
     *
     * @param id the id of the etapeExecutionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etapeExecutionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etape-executions/{id}")
    public ResponseEntity<EtapeExecutionDTO> getEtapeExecution(@PathVariable Long id) {
        log.debug("REST request to get EtapeExecution : {}", id);
        Optional<EtapeExecutionDTO> etapeExecutionDTO = etapeExecutionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etapeExecutionDTO);
    }

    /**
     * {@code DELETE  /etape-executions/:id} : delete the "id" etapeExecution.
     *
     * @param id the id of the etapeExecutionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etape-executions/{id}")
    public ResponseEntity<Void> deleteEtapeExecution(@PathVariable Long id) {
        log.debug("REST request to delete EtapeExecution : {}", id);
        etapeExecutionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/etape-executions/all")
    public ResponseEntity<List<EtapeExecutionDTO>> getAllEtapeExecution() {
        return ResponseEntity.ok(etapeExecutionService.getAllEtapeExecution());
    }
}
