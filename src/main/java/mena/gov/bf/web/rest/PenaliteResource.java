package mena.gov.bf.web.rest;

import mena.gov.bf.service.PenaliteService;
import mena.gov.bf.web.rest.errors.BadRequestAlertException;
import mena.gov.bf.service.dto.PenaliteDTO;

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
 * REST controller for managing {@link mena.gov.bf.domain.Penalite}.
 */
@RestController
@RequestMapping("/api")
public class PenaliteResource {

    private final Logger log = LoggerFactory.getLogger(PenaliteResource.class);

    private static final String ENTITY_NAME = "microserviceexecutionPenalite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PenaliteService penaliteService;

    public PenaliteResource(PenaliteService penaliteService) {
        this.penaliteService = penaliteService;
    }

    /**
     * {@code POST  /penalites} : Create a new penalite.
     *
     * @param penaliteDTO the penaliteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new penaliteDTO, or with status {@code 400 (Bad Request)} if the penalite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/penalites")
    public ResponseEntity<PenaliteDTO> createPenalite(@Valid @RequestBody PenaliteDTO penaliteDTO) throws URISyntaxException {
        log.debug("REST request to save Penalite : {}", penaliteDTO);
        if (penaliteDTO.getId() != null) {
            throw new BadRequestAlertException("A new penalite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PenaliteDTO result = penaliteService.save(penaliteDTO);
        return ResponseEntity.created(new URI("/api/penalites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /penalites} : Updates an existing penalite.
     *
     * @param penaliteDTO the penaliteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated penaliteDTO,
     * or with status {@code 400 (Bad Request)} if the penaliteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the penaliteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/penalites")
    public ResponseEntity<PenaliteDTO> updatePenalite(@Valid @RequestBody PenaliteDTO penaliteDTO) throws URISyntaxException {
        log.debug("REST request to update Penalite : {}", penaliteDTO);
        if (penaliteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PenaliteDTO result = penaliteService.save(penaliteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, penaliteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /penalites} : get all the penalites.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of penalites in body.
     */
    @GetMapping("/penalites")
    public ResponseEntity<List<PenaliteDTO>> getAllPenalites(Pageable pageable) {
        log.debug("REST request to get a page of Penalites");
        Page<PenaliteDTO> page = penaliteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /penalites/:id} : get the "id" penalite.
     *
     * @param id the id of the penaliteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the penaliteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/penalites/{id}")
    public ResponseEntity<PenaliteDTO> getPenalite(@PathVariable Long id) {
        log.debug("REST request to get Penalite : {}", id);
        Optional<PenaliteDTO> penaliteDTO = penaliteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(penaliteDTO);
    }

    /**
     * {@code DELETE  /penalites/:id} : delete the "id" penalite.
     *
     * @param id the id of the penaliteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/penalites/{id}")
    public ResponseEntity<Void> deletePenalite(@PathVariable Long id) {
        log.debug("REST request to delete Penalite : {}", id);
        penaliteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/penalites/penalite-by-contrat")
    public ResponseEntity<List<PenaliteDTO>> getPenaliteByContrat(@RequestParam Long contratId) {
        List<PenaliteDTO> penaliteDTO = penaliteService.findAllPenaliteByContrat(contratId);
        return ResponseEntity.ok().body(penaliteDTO);
    }

    @GetMapping("/penalites/penalite-by-liquidation")
    public ResponseEntity<List<PenaliteDTO>> getPenaliteByLiquidation(@RequestParam Long liquidationId) {
        List<PenaliteDTO> penaliteDTO = penaliteService.findPenalitebyLiquidation(liquidationId);
        return ResponseEntity.ok().body(penaliteDTO);
    }
}
