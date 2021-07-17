package mena.gov.bf.web.rest;

import mena.gov.bf.service.LigneBudgetaireEngagementService;
import mena.gov.bf.service.dto.LigneBudgetaireEngagementDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LigneBudgetaireEngagementResource {
    private final Logger log = LoggerFactory.getLogger(LigneBudgetaireContratResource.class);

    private static final String ENTITY_NAME = "microserviceexecutionLigneBudgetaireEngagement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LigneBudgetaireEngagementService ligneBudgetaireEngagementService;

    public LigneBudgetaireEngagementResource(LigneBudgetaireEngagementService ligneBudgetaireEngagementService) {
        this.ligneBudgetaireEngagementService = ligneBudgetaireEngagementService;
    }


    @GetMapping("/ligneBudgetaireEngagements/find-all-by-ligne-budgetaire")
    public ResponseEntity<List<LigneBudgetaireEngagementDTO>> findEngagementByLigneBudgetaire(@RequestParam Long ligneBudgetaireId) {
        return ResponseEntity.ok().body(ligneBudgetaireEngagementService.findLigneBudgetaireEngagementByLigneBudgetaire(ligneBudgetaireId));
    }
}
