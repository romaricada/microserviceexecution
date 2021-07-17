package mena.gov.bf.proxies;


import mena.gov.bf.bean.BesoinLigneBudgetaire;
import mena.gov.bf.bean.LigneBudgetaire;
import mena.gov.bf.utils.ConstantsConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@FeignClient(name = ConstantsConfig.microserviceppmName, url = ConstantsConfig.microserviceppmUrls)
public interface LigneBudgetaireRepository {
    @GetMapping("/ligne-budgetaires/find-all-liste")
    List <LigneBudgetaire> findAllListe();

    @GetMapping("/ligne-budgetaires/{id}")
    Optional<LigneBudgetaire> getLigneBudgetaire(@RequestParam(name = "id") Long id);
}
