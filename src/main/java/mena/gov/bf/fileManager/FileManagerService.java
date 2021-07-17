package mena.gov.bf.fileManager;

import mena.gov.bf.bean.Serveur;
import mena.gov.bf.model.DataFile;
import mena.gov.bf.model.TypeDossier;
import mena.gov.bf.proxies.ServeurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileManagerService {
    private final ServeurRepository serveurRepository;

    @Autowired
    FTPFileManagerService ftpManagerService;

    @Autowired
    SFTPFileManagerService sftpManagerService;

    public FileManagerService(ServeurRepository serveurRepository) {
        this.serveurRepository = serveurRepository;
    }



    public boolean connect() {
        boolean success = false;
        Serveur serveur = serveurRepository.getServeurActif();
        if (serveur.getTypeServeur().name().equals("SFTP"))
            try {
                success = sftpManagerService.sftpConnect();
                System.out.println("================= Connection successful =================");
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("================= Connection Denied =================");
            }
        else if (serveur.getTypeServeur().name().equals("FTP"))
            try {
                success = ftpManagerService.ftpConnect();
                System.out.println("================= Connection successful =================");
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("================= Connection Denied =================");
            }
        return success;

    }

    public void disconnect() {
        Serveur serveur = serveurRepository.getServeurActif();
        if (serveur.getTypeServeur().name().equals("SFTP")) {
            try {
                sftpManagerService.sftDisconnect();
                System.out.println("================= Connection close =================");
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        else if (serveur.getTypeServeur().name().equals("FTP")) {
            try {
                ftpManagerService.ftpDisconnect();
                System.out.println("================= Connection close =================");
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

    }

    public void fileUploading(Long id, TypeDossier dossier, List<DataFile> files) {
        Serveur serveur = serveurRepository.getServeurActif();
        if (serveur.getTypeServeur().name().equals("SFTP"))
            sftpManagerService.fileUploading(id, dossier, files);
        else if (serveur.getTypeServeur().name().equals("FTP"))
            ftpManagerService.fileUploading(id, dossier, files);
    }

    public List<DataFile> getEntityDataFile(Long id, TypeDossier typeDossier) {
        Serveur serveur = serveurRepository.getServeurActif();
        List<DataFile> dataFiles = null;
        if (serveur.getTypeServeur().name().equals("SFTP")) {
            dataFiles = sftpManagerService.getEntityDataFile(id, typeDossier, this.connect());
        } else if (serveur.getTypeServeur().name().equals("FTP")) {
            dataFiles = ftpManagerService.getEntityDataFile(id, typeDossier, this.connect());
        }
        return dataFiles;
    }

    public void deleteFile(Long id, TypeDossier typeDossier, String filename) {
        Serveur serveur = serveurRepository.getServeurActif();
        if (serveur.getTypeServeur().name().equals("SFTP")) {
            sftpManagerService.deleteFile(id, typeDossier, filename, this.connect());
        } else {
            ftpManagerService.deleteFile(id, typeDossier, filename, this.connect());
        }
    }

}
