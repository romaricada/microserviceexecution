package mena.gov.bf.web.rest;

import mena.gov.bf.service.LiquidationService;
import mena.gov.bf.web.rest.errors.BadRequestAlertException;
import mena.gov.bf.service.dto.LiquidationDTO;

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
 * REST controller for managing {@link mena.gov.bf.domain.Liquidation}.
 */
@RestController
@RequestMapping("/api")
public class LiquidationResource {

    private final Logger log = LoggerFactory.getLogger(LiquidationResource.class);

    private static final String ENTITY_NAME = "microserviceexecutionLiquidation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LiquidationService liquidationService;

    public LiquidationResource(LiquidationService liquidationService) {
        this.liquidationService = liquidationService;
    }

    /**
     * {@code POST  /liquidations} : Create a new liquidation.
     *
     * @param liquidationDTO the liquidationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new liquidationDTO, or with status {@code 400 (Bad Request)} if the liquidation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/liquidations")
    public ResponseEntity<LiquidationDTO> createLiquidation(@Valid @RequestBody LiquidationDTO liquidationDTO) throws URISyntaxException {
        log.debug("REST request to save Liquidation : {}", liquidationDTO);
        if (liquidationDTO.getId() != null) {
            throw new BadRequestAlertException("A new liquidation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LiquidationDTO result = liquidationService.save(liquidationDTO);
        return ResponseEntity.created(new URI("/api/liquidations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /liquidations} : Updates an existing liquidation.
     *
     * @param liquidationDTO the liquidationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated liquidationDTO,
     * or with status {@code 400 (Bad Request)} if the liquidationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the liquidationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/liquidations")
    public ResponseEntity<LiquidationDTO> updateLiquidation(@Valid @RequestBody LiquidationDTO liquidationDTO) throws URISyntaxException {
        log.debug("REST request to update Liquidation : {}", liquidationDTO);
        if (liquidationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LiquidationDTO result = liquidationService.save(liquidationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, liquidationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /liquidations} : get all the liquidations.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of liquidations in body.
     */
    @GetMapping("/liquidations")
    public ResponseEntity<List<LiquidationDTO>> getAllLiquidations(Pageable pageable) {
        log.debug("REST request to get a page of Liquidations");
        Page<LiquidationDTO> page = liquidationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /liquidations/:id} : get the "id" liquidation.
     *
     * @param id the id of the liquidationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the liquidationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/liquidations/{id}")
    public ResponseEntity<LiquidationDTO> getLiquidation(@PathVariable Long id) {
        log.debug("REST request to get Liquidation : {}", id);
        Optional<LiquidationDTO> liquidationDTO = liquidationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(liquidationDTO);
    }

    /**
     * {@code DELETE  /liquidations/:id} : delete the "id" liquidation.
     *
     * @param id the id of the liquidationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/liquidations/{id}")
    public ResponseEntity<Void> deleteLiquidation(@PathVariable Long id) {
        log.debug("REST request to delete Liquidation : {}", id);
        liquidationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/liquidations/liquidation-by-contrat")
    public ResponseEntity<List<LiquidationDTO>> getAllLiquidationsByContrat(@RequestParam Long contratId) {
        List<LiquidationDTO> liquidationDTOS = liquidationService.findAllLiquidationtionByContrat(contratId);
        return ResponseEntity.ok().body(liquidationDTOS);
    }

    @GetMapping("/liquidations/liquidation-by-engagement")
    public ResponseEntity<List<LiquidationDTO>> getAllLiquidationsByEngagement(@RequestParam Long engagementId) {
        List<LiquidationDTO> liquidationDTOS = liquidationService.findAllLiquidationtionByEngagement(engagementId);
        return ResponseEntity.ok().body(liquidationDTOS);
    }

    @PutMapping("/liquidations/deleteALL")
    public ResponseEntity<List<LiquidationDTO>> deleteAll( @RequestBody List<LiquidationDTO> liquidationDTOS) {
        return ResponseEntity.ok(liquidationService.deleteAll(liquidationDTOS));
    }
}
