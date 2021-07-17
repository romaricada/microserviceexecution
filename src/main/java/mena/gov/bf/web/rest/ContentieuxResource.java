package mena.gov.bf.web.rest;

import mena.gov.bf.service.ContentieuxService;
import mena.gov.bf.web.rest.errors.BadRequestAlertException;
import mena.gov.bf.service.dto.ContentieuxDTO;

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
 * REST controller for managing {@link mena.gov.bf.domain.Contentieux}.
 */
@RestController
@RequestMapping("/api")
public class ContentieuxResource {

    private final Logger log = LoggerFactory.getLogger(ContentieuxResource.class);

    private static final String ENTITY_NAME = "microserviceexecutionContentieux";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContentieuxService contentieuxService;

    public ContentieuxResource(ContentieuxService contentieuxService) {
        this.contentieuxService = contentieuxService;
    }

    /**
     * {@code POST  /contentieuxes} : Create a new contentieux.
     *
     * @param contentieuxDTO the contentieuxDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contentieuxDTO, or with status {@code 400 (Bad Request)} if the contentieux has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contentieuxes")
    public ResponseEntity<ContentieuxDTO> createContentieux(@Valid @RequestBody ContentieuxDTO contentieuxDTO) throws URISyntaxException {
        log.debug("REST request to save Contentieux : {}", contentieuxDTO);
        if (contentieuxDTO.getId() != null) {
            throw new BadRequestAlertException("A new contentieux cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContentieuxDTO result = contentieuxService.save(contentieuxDTO);
        return ResponseEntity.created(new URI("/api/contentieuxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contentieuxes} : Updates an existing contentieux.
     *
     * @param contentieuxDTO the contentieuxDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contentieuxDTO,
     * or with status {@code 400 (Bad Request)} if the contentieuxDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contentieuxDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contentieuxes")
    public ResponseEntity<ContentieuxDTO> updateContentieux(@Valid @RequestBody ContentieuxDTO contentieuxDTO) throws URISyntaxException {
        log.debug("REST request to update Contentieux : {}", contentieuxDTO);
        if (contentieuxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContentieuxDTO result = contentieuxService.save(contentieuxDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contentieuxDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contentieuxes} : get all the contentieuxes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contentieuxes in body.
     */
    @GetMapping("/contentieuxes")
    public ResponseEntity<List<ContentieuxDTO>> getAllContentieuxes(Pageable pageable) {
        log.debug("REST request to get a page of Contentieuxes");
        Page<ContentieuxDTO> page = contentieuxService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contentieuxes/:id} : get the "id" contentieux.
     *
     * @param id the id of the contentieuxDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contentieuxDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contentieuxes/{id}")
    public ResponseEntity<ContentieuxDTO> getContentieux(@PathVariable Long id) {
        log.debug("REST request to get Contentieux : {}", id);
        Optional<ContentieuxDTO> contentieuxDTO = contentieuxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contentieuxDTO);
    }

    /**
     * {@code DELETE  /contentieuxes/:id} : delete the "id" contentieux.
     *
     * @param id the id of the contentieuxDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contentieuxes/{id}")
    public ResponseEntity<Void> deleteContentieux(@PathVariable Long id) {
        log.debug("REST request to delete Contentieux : {}", id);
        contentieuxService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/contentieuxes/contentieux-by-contrat")
    public ResponseEntity<List<ContentieuxDTO>> getAllContentieuxesByContrat(@RequestParam(name = "contratId") Long contratId) {
        List<ContentieuxDTO> contentieuxDTOS = contentieuxService.findAllContentieuxByContrat(contratId);
        return ResponseEntity.ok().body(contentieuxDTOS);
    }
}
