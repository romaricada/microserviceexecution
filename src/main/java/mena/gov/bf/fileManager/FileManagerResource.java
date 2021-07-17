package mena.gov.bf.fileManager;

import mena.gov.bf.proxies.ServeurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FileManagerResource {

    private static final Logger log = LoggerFactory.getLogger(FileManagerResource.class);
    private final ServeurRepository serveurRepository;
    private final FileManagerService fileManagerService;

    public FileManagerResource(ServeurRepository serveurRepository, FileManagerService fileManagerService) {
        this.serveurRepository = serveurRepository;
        this.fileManagerService = fileManagerService;
    }


    private String getServeurType() {
        return serveurRepository.getServeurActif().getTypeServeur().name();
    }

   /* @PostMapping("files/upload-file-execution")
    public void uploadeExecutionFile( @Valid  @RequestBody ExecutionFile executionFile){

        fileManagerService.fileUploading( executionFile.getId(), executionFile.getTypeDossier(), executionFile.getFiles() );
    }

    @GetMapping("files/all-file")
    public ResponseEntity<List<DataFile>> getFiles(@RequestParam(name = "id") Long id, @RequestParam(name = "typeDossier") TypeDossier typeDossier){
       return ResponseEntity.ok(  ).body( fileManagerService.getEntityDataFile( id,typeDossier));
    }

    @GetMapping("files/delete")
    public void deleFile(@RequestParam(name = "id") Long id, @RequestParam(name = "typeDossier") TypeDossier typeDossier, @RequestParam(name = "fileName") String fileName){
        fileManagerService.deleteFile( id,typeDossier, fileName );
    } */
}
