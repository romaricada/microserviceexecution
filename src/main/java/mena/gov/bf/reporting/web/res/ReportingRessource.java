package mena.gov.bf.reporting.web.res;

import mena.gov.bf.domain.LigneBudgetaireContrat;
import mena.gov.bf.reporting.service.ReportingService;
import mena.gov.bf.service.dto.ContratDTO;
import mena.gov.bf.service.dto.EngagementDTO;
import mena.gov.bf.service.dto.LigneBudgetaireContratDTO;
import mena.gov.bf.service.dto.LiquidationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api")
public class ReportingRessource {
    @Autowired
    ReportingService reportingService;

    @PutMapping("/reporting/etat-contrat")
    public ResponseEntity<byte[]> imprimeContrat(@RequestBody ContratDTO contratDTO) {
        byte[] contents = reportingService.imprimeContrat(contratDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }

    @PutMapping("/reporting/etat-contrat-reception")
    public ResponseEntity<byte[]> imprimeContratReception(@RequestBody ContratDTO contratDTO) {
        byte[] contents = reportingService.imprimeContratReception(contratDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }

    @PutMapping("/reporting/etat-engagement")
    public ResponseEntity<byte[]> imprimeEngagement(@RequestBody @Valid EngagementDTO engagementDTO) {
        byte[] contents = reportingService.imprimeEngagement(engagementDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        return new ResponseEntity<>(contents, headers, HttpStatus.OK);

     }
    @PutMapping("/reporting/etat-liquidation")
    public ResponseEntity<byte[]> imprimeLiquidation(@RequestBody LiquidationDTO liquidationDTO) {
        byte[] contents = reportingService.imprimeLiquidation(liquidationDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }

}
