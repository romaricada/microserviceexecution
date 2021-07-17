package mena.gov.bf.proxies;

import mena.gov.bf.bean.ExerciceBudgetaire;
import mena.gov.bf.utils.ConstantsConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Spring Data repository for the ExerciceBudgetaire entity.
 */

@FeignClient(name = ConstantsConfig.microserviceppmName, url = ConstantsConfig.microserviceppmUrl, decode404 = true)
public interface ExerciceBudgetaireRepository {
    @GetMapping("/exercice-budgetaires/{id}")
    ExerciceBudgetaire findExerciceBudgetaireById(@PathVariable("id") Long id);

    @GetMapping("/exercice-budgetaires/current-exercice")
    ExerciceBudgetaire findCurrentExercice();
}
