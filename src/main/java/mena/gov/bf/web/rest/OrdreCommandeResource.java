package mena.gov.bf.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import mena.gov.bf.service.OrdreCommandeService;
import mena.gov.bf.service.dto.OrdreCommandeDTO;
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

/**
 * REST controller for managing {@link mena.gov.bf.domain.Penalite}.
 */
@RestController
@RequestMapping("/api")
public class OrdreCommandeResource {

    private final Logger log = LoggerFactory.getLogger(OrdreCommandeResource.class);

    private static final String ENTITY_NAME = "microserviceexecutionOrdreCommande";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdreCommandeService ordreCommandeService;

    public OrdreCommandeResource(OrdreCommandeService ordreCommandeService) {
        this.ordreCommandeService = ordreCommandeService;
    }


    /**
     * {@code POST  /ordre-commandes} : Create a new penalite.
     *
     * @param ordreCommandeDTO the ordreCommandeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordreCommandeDTO, or with status {@code 400 (Bad Request)} if the penalite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ordre-commandes")
    public ResponseEntity<OrdreCommandeDTO> createOrdreCommandeDTO(@Valid @RequestBody OrdreCommandeDTO ordreCommandeDTO) throws URISyntaxException {
        log.debug("REST request to save OrdreCommande : {}", ordreCommandeDTO);
        if (ordreCommandeDTO.getId() != null) {
            throw new BadRequestAlertException("A new penalite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdreCommandeDTO result = ordreCommandeService.save(ordreCommandeDTO);
        return ResponseEntity.created(new URI("/api/ordre-commandes/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /ordre-commandes} : Updates an existing ordreCommande.
     *
     * @param ordreCommandeDTO the ordreCommandeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordreCommandeDTO,
     * or with status {@code 400 (Bad Request)} if the penaliteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordreCommandeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ordre-commandes")
    public ResponseEntity<OrdreCommandeDTO> updateOrdreCommandeDTO(@Valid @RequestBody OrdreCommandeDTO ordreCommandeDTO) throws URISyntaxException {
        log.debug("REST request to update OrdreCommande : {}", ordreCommandeDTO);
        if (ordreCommandeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrdreCommandeDTO result = ordreCommandeService.save(ordreCommandeDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ordreCommandeDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /ordre-commandes} : get all the ordre-commandes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordre-commandes in body.
     */
    @GetMapping("/ordre-commandes")
    public ResponseEntity<List<OrdreCommandeDTO>> getAllordreCommandes(Pageable pageable) {
        log.debug("REST request to get a page of ordreCommande");
        Page<OrdreCommandeDTO> page = ordreCommandeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ordre-commandes/:id} : get the "id" ordreCommande.
     *
     * @param id the id of the ordreCommandeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordreCommandeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ordre-commandes/{id}")
    public ResponseEntity<OrdreCommandeDTO> getOrdreCommande(@PathVariable Long id) {
        log.debug("REST request to get Penalite : {}", id);
        Optional<OrdreCommandeDTO> ordreCommandeDTO = ordreCommandeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordreCommandeDTO);
    }

    @GetMapping("/ordre-commandes/findAll-by-contrat")
    public ResponseEntity<List<OrdreCommandeDTO>> findAllbyContrat(@RequestParam Long contratId) {
        log.debug("REST request to get Penalite : {}", contratId);
        return ResponseEntity.ok(ordreCommandeService.findAllbyContrat(contratId));
    }

    /**
     * {@code DELETE  /ordre-commandes/:id} : delete the "id" ordreCommande.
     *
     * @param id the id of the ordreCommandeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ordre-commandes/{id}")
    public ResponseEntity<Void> deleteOrdreCommande(@PathVariable Long id) {
        log.debug("REST request to delete ordreCommande : {}", id);
        ordreCommandeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @PutMapping("/ordre-commandes/deleteALL")
    public ResponseEntity<List<OrdreCommandeDTO>> deleteAll( @RequestBody List<OrdreCommandeDTO> ordreCommandeDTOS) {
        return ResponseEntity.ok(ordreCommandeService.deleteAll(ordreCommandeDTOS));
    }
}
