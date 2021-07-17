package mena.gov.bf.proxies;


import mena.gov.bf.bean.Lot;
import mena.gov.bf.bean.LotDTO;
import mena.gov.bf.utils.ConstantsConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = ConstantsConfig.microserviceDacCamName, url = ConstantsConfig.microserviceDacCamUrl)
public interface LotRepository {

    @GetMapping("/lots/activite-id")
    Lot findAllByActiviteWithCandidat(Long activiteId);

    @GetMapping("/lots/lotAll")
    List<Lot> findAllLot(@RequestParam(name = "lotId") Long lotId);

    @GetMapping("/lots/lotAll")
    List<LotDTO> findAllInOneLot(@RequestParam(name = "lotId") Long lotId);

    @GetMapping("/lots/{id}")
    Lot getOne(@PathVariable(name = "id") Long id);
}
