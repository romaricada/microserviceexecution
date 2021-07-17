package mena.gov.bf.proxies;


import mena.gov.bf.bean.BesoinLigneBudgetaire;
import mena.gov.bf.bean.BesoinLigneBudgetaireDTO;
import mena.gov.bf.utils.ConstantsConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = ConstantsConfig.microserviceppmName, url = ConstantsConfig.microserviceppmUrls)
public interface BesoinLigneBudgetaireRepository {
    @GetMapping("/besoin-ligne-budgetaires/all-besoinLigneBudgetaire-and-activite")
    List <BesoinLigneBudgetaire> findAllBesoinLigneBudgetaireByActivite(@RequestParam(name = "activiteId") Long activiteId);

    @GetMapping("/besoin-ligne-budgetaires/all-besoin-by-activite-and-ligne")
    List <BesoinLigneBudgetaire> findAllBesoinByActiviteAndLigne(@RequestParam(name = "activiteId") Long activiteId, @RequestParam(name = "ligneId") Long ligneId);

    @GetMapping("/besoin-ligne-budgetaires")
    List <BesoinLigneBudgetaire> findAll();

    @GetMapping("/besoin-ligne-budgetaires/all-besoinLigneBudgetaire-by-ligne-budgetaire")
    List <BesoinLigneBudgetaire> findAllByLigneBudgetaire(@RequestParam(name = "ligneBudgetaireId") Long ligneBudgetaireId);

    @PutMapping("/besoin-ligne-budgetaires/all-besoin-ligne-budgetaire-by-contrat")
    List <BesoinLigneBudgetaireDTO> findBesoinLigneBudgetaireByContratId(@RequestParam(name = "blblId") List<Long> blblId);

}
