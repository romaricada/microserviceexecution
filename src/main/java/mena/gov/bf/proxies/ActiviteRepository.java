package mena.gov.bf.proxies;

import mena.gov.bf.bean.ActiviteDTO;
import mena.gov.bf.utils.ConstantsConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = ConstantsConfig.microserviceppmName, url = ConstantsConfig.microserviceppmUrl)
public interface ActiviteRepository {

    @RequestMapping(value = "/activites", method = RequestMethod.GET)
    List<ActiviteDTO> listActivites();

    @GetMapping("/activites/{id}")
    ActiviteDTO findActiviteById(@PathVariable("id") Long id);

    @GetMapping("/activites/all-by-annee")
    List<ActiviteDTO> findAllActiviteByAnnee(@RequestParam(name = "idAnnee") Long idAnnee);
}
