package mena.gov.bf.fileManager;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import mena.gov.bf.bean.Serveur;
import mena.gov.bf.model.DataFile;
import mena.gov.bf.model.TypeDossier;
import mena.gov.bf.proxies.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.activation.MimetypesFileTypeMap;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@Service
public class SFTPFileManagerService {

    private final Logger log = LoggerFactory.getLogger( FileManagerService.class );
    private final PathSevice pathSevice;
    private final ServeurRepository serveurRepository;

    private String host = "";
    private int port = 0;
    private String user = "";
    private String pswd = "";

    private JSch ssh = new JSch();
    private Session session;
    private ChannelSftp sftp;

    public SFTPFileManagerService(PathSevice pathSevice, ServeurRepository serveurRepository) {
        this.pathSevice = pathSevice;
        this.serveurRepository = serveurRepository;
    }


    private void setServeurValues() {
        Serveur serveur = serveurRepository.getServeurActif();
        host = serveur.getAdresse();
        port = serveur.getPort();
        user = serveur.getNomServeur();
        pswd = serveur.getMotPasse();
        System.out.println( "Informations Serveur: adresse=" + host + ", port=" + port + ", user=" + user );
    }

    public boolean sftpConnect() {
        java.util.Properties config = new java.util.Properties();
        config.put( "StrictHostKeyChecking", "no" );
        setServeurValues();
        /*
         * host = "192.162.71.76"; port = 22; user = "odk"; pswd = "O2kp@$$wd";
         */
        try {
            session = ssh.getSession( user, host, port );
            session.setConfig( config );
            session.setPassword( pswd );
            // session.setTimeout(360000000);
            session.connect();

            sftp = (ChannelSftp) session.openChannel( "sftp" );
            sftp.connect();
            System.out.println( "========== Connection success =============" );
            return true;
        } catch (JSchException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }

    public void sftDisconnect() {
        sftp.disconnect();
        session.disconnect();
    }

    @Transactional
    public void sftpConnexion() {
        boolean success = sftpConnect();
        if (success)
            System.out.println( "============= Connexion effectuée avec success ========" );
        else
            System.out.println( "=============== Echec de connexion =================" );
    }


    public void fileUploading(Long id, TypeDossier typeDossier, List<DataFile> dataFiles) {
        boolean success = sftpConnect();
        InputStream inputStream;
        if (success) {
            String filePath = createDirectory( id, typeDossier );
            log.debug( "==================dataFiles.size() : {}", dataFiles.size() );
            if (!dataFiles.isEmpty()) {
                for (DataFile dataFile : dataFiles) {
                    if (dataFile.getFile() != null) {
                        inputStream = new ByteArrayInputStream( dataFile.getFile() );
                        try {

                            sftp.put( inputStream, filePath + dataFile.getFileName() );
                            System.out.println( "======================== Document chargé avec success ==============" );
                        } catch (SftpException e) {
                            // TODO Auto-generated catch block
                            log.debug( "================= Problème de SFTP =====================" );
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println( "************* Le byte est null ***********************" );
                    }
                }
            } else {
                System.out.println( "************* Nombre de fichiers joints: 0 ***********************" );
            }
        }
    }

    @Transactional
    public List<DataFile> getEntityDataFile(Long id, TypeDossier typeDossier, boolean success) {
        List<DataFile> datas = new ArrayList<>();
        String currentDirectory = null;
        String path = this.pathSevice.getPath( id, typeDossier );
        byte[] byteArray;
        MimetypesFileTypeMap contenType = new MimetypesFileTypeMap();
        try {
            try {
                currentDirectory = sftp.pwd();
            } catch (SftpException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            String chemin1 = currentDirectory + path;
            if (!success) {
                System.out.println( "============== Could not login to the server ===============" );
            } else {
                Vector filelist = sftp.ls( chemin1 );
                for (int i = 0; i < filelist.size(); i++) {
                    LsEntry entry = (LsEntry) filelist.get( i );
                    if (!entry.getAttrs().isDir()) {
                        InputStream inputStream = sftp.get( chemin1 + entry.getFilename() );
                        byteArray = IOUtils.toByteArray( inputStream );
                        try {
                            if (byteArray.length > 0) {
                                DataFile dataFile = new DataFile();
                                dataFile.setFileName( entry.getFilename() );
                                dataFile.setFile( byteArray );
                                dataFile.setFileContentType( contenType.getContentType( entry.getFilename() ) );
                                datas.add( dataFile );
                            }
                            inputStream.close();

                        } catch (Exception e) {
                            // TODO: handle exception
                            System.out.println( "======= Erreur de lecture de fichier =======" );
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception

        }

        return datas;
    }

    public void deleteAllByEntity(Long id, TypeDossier typeDossier, boolean success) {
        String currentDirectory = null;
        String path = this.pathSevice.getPath( id, typeDossier );
        byte[] byteArray;
        List<DataFile> datas = new ArrayList<>();
        try {
            try {
                currentDirectory = sftp.pwd();
            } catch (SftpException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            String chemin1 = currentDirectory + path;
            if (!success) {
                System.out.println( "============== Could not login to the server ===============" );
            } else {

                Vector filelist = sftp.ls( chemin1 );
                MimetypesFileTypeMap contenType = new MimetypesFileTypeMap();
                for (int i = 0; i < filelist.size(); i++) {
                    LsEntry entry = (LsEntry) filelist.get( i );
                    if (!entry.getAttrs().isDir()) {
                        InputStream inputStream = sftp.get( chemin1 + entry.getFilename() );
                        byteArray = IOUtils.toByteArray( inputStream );
                        log.debug( "==byteArray : {}", byteArray.length );
                        try {
                            // int in = inputStream.read(byteArray);
                            //  if (in != -1) {
                            if (byteArray.length > 0) {
                                DataFile dataFile = new DataFile();
                                dataFile.setFileName( entry.getFilename() );
                                dataFile.setFile( byteArray );
                                log.debug( "contentType: {}", contenType.getContentType( entry.getFilename() ) );
                                dataFile.setFileContentType( contenType.getContentType( entry.getFilename() ) );
                                datas.add( dataFile );
                            }
                            inputStream.close();
                            /*} else {
                                System.out.println("Aucun octe lu");
                            }*/
                            // while (!sftp.completePendingCommand())
                        } catch (Exception e) {
                            // TODO: handle exception
                            System.out.println( "======= Erreur de lecture de fichier =======" );
                        }
                    }

                }
                sftp.cd( ".." );
                sftp.rmdir( chemin1 );
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println( "=============== Echec de suppression de document ================" );
        }
    }

    @Transactional
    public void deleteFile(Long id, TypeDossier typeDossier, String filename, boolean success) {
        String currentDirectory = null;
        String path = this.pathSevice.getPath( id, typeDossier );
        try {
            try {
                currentDirectory = sftp.pwd();
            } catch (SftpException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            //  String chemin1 = currentDirectory;
            if (!success) {
                System.out.println( "============== Could not login to the server ===============" );
            } else {
                LsEntry entry = getFile( id, typeDossier, currentDirectory, filename );
                if (entry != null) {
                    sftp.rm( currentDirectory + path + entry.getFilename() );
                    System.out.println( "Fichier '" + entry.getFilename() + "' Supprimé avec succès..." );
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println( "=============== Echec de suppression de document ================" );
        }
    }

    private LsEntry getFile(Long id, TypeDossier typeDossier, String currentDirectory, String filename) {
        String chem = this.pathSevice.getPath( id, typeDossier );
        String chemin1 = currentDirectory + chem;
        LsEntry entry = null;
        try {
            Vector filelist = sftp.ls( chemin1 );
            for (int i = 0; i < filelist.size(); i++) {
                entry = (LsEntry) filelist.get( i );
                if (!entry.getAttrs().isDir() && entry.getFilename().equals( filename ))
                    break;
            }
            return entry;
        } catch (SftpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


    private String createDirectory(Long id, TypeDossier typeDossier) {
        String currentDirectory = null;
        try {
            currentDirectory = sftp.pwd();
        } catch (SftpException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String path = currentDirectory + this.pathSevice.getPath( id, typeDossier );

        try {
            String[] folders = path.split( "/" );
            if (folders[0].isEmpty())
                folders[0] = "/";
            String fullPath = folders[0];
            for (int i = 1; i < folders.length; i++) {
                Vector ls = sftp.ls( fullPath );
                boolean isExist = false;
                for (Object o : ls) {
                    if (o instanceof LsEntry) {
                        LsEntry e = (LsEntry) o;
                        if (e.getAttrs().isDir() && e.getFilename().equals( folders[i] )) {
                            isExist = true;
                        }
                    }
                }
                if (!isExist && !folders[i].isEmpty()) {
                    sftp.mkdir( fullPath + folders[i] );
                }
                fullPath = fullPath + folders[i] + "/";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

}
