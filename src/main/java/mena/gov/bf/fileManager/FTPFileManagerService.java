package mena.gov.bf.fileManager;

import mena.gov.bf.bean.*;
import mena.gov.bf.model.DataFile;
import mena.gov.bf.model.TypeDossier;
import mena.gov.bf.proxies.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import javax.activation.MimetypesFileTypeMap;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FTPFileManagerService {

    private final Logger log = LoggerFactory.getLogger(FTPFileManagerService.class);
   private final PathSevice pathSevice;
    private final ServeurRepository serveurRepository;

   private Path path;
   private String host = "";
   private int port = 0;
   private String user = "";
   private String pswd = "";

   private FTPClient client = new FTPClient();

    public FTPFileManagerService(PathSevice pathSevice, ServeurRepository serveurRepository) {
        this.pathSevice = pathSevice;
        this.serveurRepository = serveurRepository;
    }

    private void serveurValues() {
        Serveur serveur = serveurRepository.getServeurActif();
        host = serveur.getAdresse();
        port = serveur.getPort();
        user = serveur.getNomServeur();
        pswd = serveur.getMotPasse();
    }



    public void init(Long id, TypeDossier typeDossier) {
        String chemin = this.pathSevice.getPath(id, typeDossier);
        path = Paths.get(chemin);
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public boolean ftpConnect() {
        try {
            serveurValues();
            client.connect(host, port);
            boolean success = client.login(user, pswd);
            return success;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    public void ftpDisconnect() {
        try {
            if (client.isConnected()) {
                client.logout();
                client.disconnect();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void fileUploading(Long id, TypeDossier typeDossier, List<DataFile> dataFiles) {
        InputStream inputStream = null;
        String path = this.pathSevice.getPath(id, typeDossier);
        boolean success = ftpConnect();

        if (success) {
            try {
                success = client.changeWorkingDirectory(path);
                if (!success) {
                    client.makeDirectory(path);
                }
                if (!dataFiles.isEmpty()) {
                    for (DataFile dataFile : dataFiles) {
                        if (dataFile.getFile() != null) {
                            inputStream = new ByteArrayInputStream(dataFile.getFile());
                            client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
                            client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
                            client.enterLocalPassiveMode();
                            client.storeFile(path + dataFile.getFileName(), inputStream);
                        } else {
                            System.out.println("============= Le byte est null ============");
                        }
                    }
                    System.out.println("======================== Document chargé avec success ==============");
                } else {
                    System.out.println("************* Nombre de fichiers joints: 0 ***********************");
                }

            } catch (Exception e) {
                // TODO: handle exception
            }
            ftpDisconnect();
        } else {
            System.out.println("===================== Echec de connexion ==================");
        }

    }

    @Transactional
    public void deleteAll(Long id, TypeDossier typeDossier) {
        String chemin = this.pathSevice.getPath(id, typeDossier);
        path = Paths.get(chemin);
        FileSystemUtils.deleteRecursively(path.toFile());
    }

    @Transactional
    public void deleteFile(Long id, TypeDossier typeDossier, String filename, boolean success) {
        String chemin1 = this.pathSevice.getPath(id, typeDossier);
        try {
            if (!success) {
                System.out.println("============== Could not login to the server ===============");
            } else {
                success = client.deleteFile(chemin1 + filename);
                if (success)
                    System.out.println("Fichier '" + filename + "' Supprimé avec succès...");
                else
                    System.out.println("File '" + filename + "' doesn't exist...");
                System.out.println("=============   Fin du fichier   ===============");
                ftpDisconnect();

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public List<DataFile> getEntityDataFile(Long id, TypeDossier typeDossier, boolean success) {
        List<DataFile> dataFiles = new ArrayList<>();
        String chemin1 = this.pathSevice.getPath(id, typeDossier);
        byte[] byteArray;
        try {
            // Boolean success = ftpConnect();
            if (!success) {
                System.out.println("============== Could not login to the server ===============");
            } else {
                FTPFile[] liste = client.listFiles(chemin1);
                MimetypesFileTypeMap contenType = new MimetypesFileTypeMap();
                for (FTPFile file : liste) {
                    client.enterLocalPassiveMode();
                    client.setFileType(FTPClient.BINARY_FILE_TYPE);
                    client.setRemoteVerificationEnabled(false);
                    InputStream inputStream = client.retrieveFileStream(chemin1 + file.getName());
                    byteArray = IOUtils.toByteArray(inputStream);
                    log.debug("==byteArray : {}", byteArray.length);
                    try {

                        if (byteArray.length > 0) {
                            DataFile dataFile = new DataFile();
                            dataFile.setFileName(file.getName());
                            dataFile.setFile(byteArray);
                            log.debug("mimeType: {}", contenType.getContentType(file.getName()));
                            dataFile.setFileContentType(contenType.getContentType(file.getName()));
                            dataFiles.add(dataFile);
                        }
                        inputStream.close();
                        while (!client.completePendingCommand())
                            ;
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println("======= Erreur de lecture de fichier =======");
                    }
                }

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return dataFiles;
    }

}
