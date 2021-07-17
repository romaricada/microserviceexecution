package mena.gov.bf.proxies;


import mena.gov.bf.bean.CandidatLot;
import mena.gov.bf.bean.CandidatLotDTO;
import mena.gov.bf.utils.ConstantsConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = ConstantsConfig.microserviceDacCamName, url = ConstantsConfig.microserviceDacCamUrl)
public interface CandidatLotRepository {
    @GetMapping("/candidat-lots")
    List<CandidatLot> findCandidatLot();

    @GetMapping("/candidat-lots")
    List<CandidatLotDTO> findCandidatLotListe();

    @PostMapping("/candidat-lots")
    CandidatLot saveCandidatLot();

    @PostMapping("/all-candidat-lots")
    List<CandidatLot> saveAllCandidatLot(List<CandidatLot> candidatLots);

    @GetMapping("/candidat-lots/all-candidat-by-lot")
    List<CandidatLot> findAllByByLot(@RequestParam(name = "lotId") Long lotId);

    @GetMapping("/candidat-lots/attributaire-by-lot")
    CandidatLot findAttributaireByLot(@RequestParam(name = "lotId") Long lotId);

    @GetMapping("/candidat-lots/{id}")
    CandidatLot getOne(@PathVariable(name = "id") Long id);


    @GetMapping("/candidat-lots/find-Candidat-contrat")
    CandidatLot findAllByLotIdAndDeletedIsFalseAndAttributaireIsNotNullAndAttributaireIsTrue(@PathVariable(name = "lotId") Long lotId);

    @PutMapping("/candidat-lots/save-dembre")
    CandidatLot save1(@Valid @RequestBody CandidatLot candidatLot);
}
