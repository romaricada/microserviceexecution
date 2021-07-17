package mena.gov.bf.proxies;

import mena.gov.bf.model.DataFile;
import mena.gov.bf.model.ExecutionFile;
import mena.gov.bf.model.TypeDossier;
import mena.gov.bf.utils.ConstantsConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = ConstantsConfig.microserviceDacCamName, url = ConstantsConfig.microserviceDacCamUrl)
public interface FileManangerRepository {
    @PostMapping("files/upload-file-execution")
    void uploadeFiles(@RequestBody ExecutionFile executionFile);

    @GetMapping("files/all-file")
    List<DataFile> getFiles(@RequestParam(name = "id") Long id, @RequestParam(name = "typeDossier") TypeDossier typeDossier);

    @GetMapping("files/delete")
    List<DataFile> deleteFile(@RequestParam(name = "id") Long id, @RequestParam(name = "typeDossier") TypeDossier typeDossier, @RequestParam(name = "fileName") String fileName);
}
