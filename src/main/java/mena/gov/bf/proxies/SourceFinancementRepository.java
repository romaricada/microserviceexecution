package mena.gov.bf.proxies;

import mena.gov.bf.bean.SourceFinancementDTO;
import mena.gov.bf.utils.ConstantsConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = ConstantsConfig.microserviceppmName, url = ConstantsConfig.microserviceppmUrl)
public interface SourceFinancementRepository {
    @GetMapping("/source-financements/findAll-by")
    List<SourceFinancementDTO> findAllSourceFinancement();
}
