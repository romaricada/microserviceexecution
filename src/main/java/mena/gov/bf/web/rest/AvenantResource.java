package mena.gov.bf.web.rest;

import mena.gov.bf.service.AvenantService;
import mena.gov.bf.web.rest.errors.BadRequestAlertException;
import mena.gov.bf.service.dto.AvenantDTO;

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
 * REST controller for managing {@link mena.gov.bf.domain.Avenant}.
 */
@RestController
@RequestMapping("/api")
public class AvenantResource {

    private final Logger log = LoggerFactory.getLogger(AvenantResource.class);

    private static final String ENTITY_NAME = "microserviceexecutionAvenant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvenantService avenantService;

    public AvenantResource(AvenantService avenantService) {
        this.avenantService = avenantService;
    }

    /**
     * {@code POST  /avenants} : Create a new avenant.
     *
     * @param avenantDTO the avenantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avenantDTO, or with status {@code 400 (Bad Request)} if the avenant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/avenants")
    public ResponseEntity<AvenantDTO> createAvenant(@Valid @RequestBody AvenantDTO avenantDTO) throws URISyntaxException {
        log.debug("REST request to save Avenant : {}", avenantDTO);
        if (avenantDTO.getId() != null) {
            throw new BadRequestAlertException("A new avenant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvenantDTO result = avenantService.save(avenantDTO);
        return ResponseEntity.created(new URI("/api/avenants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avenants} : Updates an existing avenant.
     *
     * @param avenantDTO the avenantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avenantDTO,
     * or with status {@code 400 (Bad Request)} if the avenantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avenantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/avenants")
    public ResponseEntity<AvenantDTO> updateAvenant(@Valid @RequestBody AvenantDTO avenantDTO) throws URISyntaxException {
        log.debug("REST request to update Avenant : {}", avenantDTO);
        if (avenantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AvenantDTO result = avenantService.save(avenantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avenantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /avenants} : get all the avenants.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avenants in body.
     */
    @GetMapping("/avenants")
    public ResponseEntity<List<AvenantDTO>> getAllAvenants(Pageable pageable) {
        log.debug("REST request to get a page of Avenants");
        Page<AvenantDTO> page = avenantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /avenants/:id} : get the "id" avenant.
     *
     * @param id the id of the avenantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avenantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/avenants/{id}")
    public ResponseEntity<AvenantDTO> getAvenant(@PathVariable Long id) {
        log.debug("REST request to get Avenant : {}", id);
        Optional<AvenantDTO> avenantDTO = avenantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avenantDTO);
    }

    /**
     * {@code DELETE  /avenants/:id} : delete the "id" avenant.
     *
     * @param id the id of the avenantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/avenants/{id}")
    public ResponseEntity<Void> deleteAvenant(@PathVariable Long id) {
        log.debug("REST request to delete Avenant : {}", id);
        avenantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/avenants/avenant-by-contrat")
    public ResponseEntity<List<AvenantDTO>> getAllAvenantsByContrat(@RequestParam Long contratId) {
        List<AvenantDTO> avenantDTOS = avenantService.findAllAvenantByContrat(contratId);
        return ResponseEntity.ok().body(avenantDTOS);
    }
}
