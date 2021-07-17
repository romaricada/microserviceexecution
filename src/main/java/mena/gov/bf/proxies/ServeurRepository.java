package mena.gov.bf.proxies;

import mena.gov.bf.bean.Serveur;
import mena.gov.bf.utils.ConstantsConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = ConstantsConfig.microservicegedName, url = ConstantsConfig.microservicegedmUrl)
public interface ServeurRepository {

    @GetMapping("/serveurs/serveur-actif")
    Serveur getServeurActif();
}
