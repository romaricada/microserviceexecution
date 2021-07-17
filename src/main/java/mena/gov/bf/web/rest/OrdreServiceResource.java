package mena.gov.bf.web.rest;

import mena.gov.bf.domain.Contrat;
import mena.gov.bf.service.OrdreServiceService;
import mena.gov.bf.service.dto.ContratDTO;
import mena.gov.bf.web.rest.errors.BadRequestAlertException;
import mena.gov.bf.service.dto.OrdreServiceDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link mena.gov.bf.domain.OrdreService}.
 */
@RestController
@RequestMapping("/api")
public class OrdreServiceResource {

    private final Logger log = LoggerFactory.getLogger(OrdreServiceResource.class);

    private static final String ENTITY_NAME = "microserviceexecutionOrdreService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdreServiceService ordreServiceService;

    public OrdreServiceResource(OrdreServiceService ordreServiceService) {
        this.ordreServiceService = ordreServiceService;
    }

    /**
     * {@code POST  /ordre-services} : Create a new ordreService.
     *
     * @param ordreServiceDTO the ordreServiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordreServiceDTO, or with status {@code 400 (Bad Request)} if the ordreService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ordre-services")
    public ResponseEntity<OrdreServiceDTO> createOrdreService(@Valid @RequestBody OrdreServiceDTO ordreServiceDTO) throws URISyntaxException {
        log.debug("REST request to save OrdreService : {}", ordreServiceDTO);
        if (ordreServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordreService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        //OrdreServiceDTO result = ordreServiceService.save(ordreServiceDTO);
        return ResponseEntity.ok(ordreServiceService.save(ordreServiceDTO));
            /*ResponseEntity.created(new URI("/api/ordre-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);*/
    }

    /**
     * {@code PUT  /ordre-services} : Updates an existing ordreService.
     *
     * @param ordreServiceDTO the ordreServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordreServiceDTO,
     * or with status {@code 400 (Bad Request)} if the ordreServiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordreServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ordre-services")
    public ResponseEntity<OrdreServiceDTO> updateOrdreService(@Valid @RequestBody OrdreServiceDTO ordreServiceDTO) throws URISyntaxException {
        log.debug("REST request to update OrdreService : {}", ordreServiceDTO);
        if (ordreServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrdreServiceDTO result = ordreServiceService.save(ordreServiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ordreServiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ordre-services} : get all the ordreServices.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordreServices in body.
     */
    @GetMapping("/ordre-services")
    public List<OrdreServiceDTO> getAllOrdreServices() {
        log.debug("REST request to get all OrdreServices");
        return ordreServiceService.findAll();
    }

    /**
     * {@code GET  /ordre-services/:id} : get the "id" ordreService.
     *
     * @param id the id of the ordreServiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordreServiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ordre-services/{id}")
    public ResponseEntity<OrdreServiceDTO> getOrdreService(@PathVariable Long id) {
        log.debug("REST request to get OrdreService : {}", id);
        Optional<OrdreServiceDTO> ordreServiceDTO = ordreServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordreServiceDTO);
    }

    /**
     * {@code DELETE  /ordre-services/:id} : delete the "id" ordreService.
     *
     * @param id the id of the ordreServiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ordre-services/{id}")
    public ResponseEntity<Void> deleteOrdreService(@PathVariable Long id) {
        log.debug("REST request to delete OrdreService : {}", id);
        ordreServiceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/ordre-services/findContratbyordre")
    public ResponseEntity<List<ContratDTO>> findContratByOrdreService(@RequestParam Long id) {

        log.debug("========================" +id);
        return  ResponseEntity.ok().body(ordreServiceService.findContratByOrdreService(id));
    }

    @GetMapping("/ordre-services/ordreService-by-contrat")
    public ResponseEntity<List<OrdreServiceDTO>> findAllOrdreServiceByContrat(@RequestParam Long contratId){
        log.debug("========================" +contratId);
        return ResponseEntity.ok().body(ordreServiceService.findAllOrdreServiceByContrat(contratId));
    }
}
