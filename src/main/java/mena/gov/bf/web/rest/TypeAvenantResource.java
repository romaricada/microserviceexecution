package mena.gov.bf.web.rest;

import mena.gov.bf.service.TypeAvenantService;
import mena.gov.bf.web.rest.errors.BadRequestAlertException;
import mena.gov.bf.service.dto.TypeAvenantDTO;

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
 * REST controller for managing {@link mena.gov.bf.domain.TypeAvenant}.
 */
@RestController
@RequestMapping("/api")
public class TypeAvenantResource {

    private final Logger log = LoggerFactory.getLogger(TypeAvenantResource.class);

    private static final String ENTITY_NAME = "microserviceexecutionTypeAvenant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeAvenantService typeAvenantService;

    public TypeAvenantResource(TypeAvenantService typeAvenantService) {
        this.typeAvenantService = typeAvenantService;
    }

    /**
     * {@code POST  /type-avenants} : Create a new typeAvenant.
     *
     * @param typeAvenantDTO the typeAvenantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeAvenantDTO, or with status {@code 400 (Bad Request)} if the typeAvenant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-avenants")
    public ResponseEntity<TypeAvenantDTO> createTypeAvenant(@Valid @RequestBody TypeAvenantDTO typeAvenantDTO) throws URISyntaxException {
        log.debug("REST request to save TypeAvenant : {}", typeAvenantDTO);
        if (typeAvenantDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeAvenant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeAvenantDTO result = typeAvenantService.save(typeAvenantDTO);
        return ResponseEntity.created(new URI("/api/type-avenants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-avenants} : Updates an existing typeAvenant.
     *
     * @param typeAvenantDTO the typeAvenantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeAvenantDTO,
     * or with status {@code 400 (Bad Request)} if the typeAvenantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeAvenantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-avenants")
    public ResponseEntity<TypeAvenantDTO> updateTypeAvenant(@Valid @RequestBody TypeAvenantDTO typeAvenantDTO) throws URISyntaxException {
        log.debug("REST request to update TypeAvenant : {}", typeAvenantDTO);
        if (typeAvenantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeAvenantDTO result = typeAvenantService.save(typeAvenantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeAvenantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-avenants} : get all the typeAvenants.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeAvenants in body.
     */
    @GetMapping("/type-avenants")
    public ResponseEntity<List<TypeAvenantDTO>> getAllTypeAvenants(Pageable pageable) {
        log.debug("REST request to get a page of TypeAvenants");
        Page<TypeAvenantDTO> page = typeAvenantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-avenants/:id} : get the "id" typeAvenant.
     *
     * @param id the id of the typeAvenantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeAvenantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-avenants/{id}")
    public ResponseEntity<TypeAvenantDTO> getTypeAvenant(@PathVariable Long id) {
        log.debug("REST request to get TypeAvenant : {}", id);
        Optional<TypeAvenantDTO> typeAvenantDTO = typeAvenantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeAvenantDTO);
    }

    /**
     * {@code DELETE  /type-avenants/:id} : delete the "id" typeAvenant.
     *
     * @param id the id of the typeAvenantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-avenants/{id}")
    public ResponseEntity<Void> deleteTypeAvenant(@PathVariable Long id) {
        log.debug("REST request to delete TypeAvenant : {}", id);
        typeAvenantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
