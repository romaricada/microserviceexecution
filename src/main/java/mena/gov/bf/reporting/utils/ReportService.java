package mena.gov.bf.reporting.utils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@Service
@Transactional
public class ReportService {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(ReportService.class);

    private static final long serialVersionUID = 1L;

    private String etatCompile; //Fichier compilé de l'état
    private JasperPrint jasperPrint;
    private HashMap parametres; //Paramètres
    private ApplicationContext applicationContext;

    public ReportService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     *
     * @param etatCompile
     * @param datasource
     * @param parametres
     * @return
     */
    public byte[] ImprimerPDF(InputStream etatCompile, JRBeanCollectionDataSource datasource, HashMap<String, ? super Object> parametres) {

        try {

            if (datasource == null) {
                jasperPrint = JasperFillManager.fillReport(etatCompile, parametres);
            } else {
                jasperPrint = JasperFillManager.fillReport(etatCompile, parametres, datasource);
            }

            // Transforme le fichier en un tableau de bytes
            byte[] contents = JasperExportManager.exportReportToPdf(jasperPrint);

            return contents;
        } catch (JRException ex) {
            log.error(Marker.ANY_MARKER, null, ex);
        }

        return null;

    }

    /**
     *
     * @param fileName
     * @param dataSource
     * @param parameters
     * @return
     */
    public byte[] createReports(String fileName, JRBeanCollectionDataSource dataSource, HashMap<String, ? super Object> parameters) {
        try {
            Resource resource = applicationContext.getResource(Constants.REPORT_CLASS_PATH+""+fileName);
            return ImprimerPDF(resource.getInputStream(), dataSource, parameters);
        } catch (IOException ex) {
            log.error(Marker.ANY_MARKER, null, ex);
        }
        return null;
    }

    public String getEtatCompile() {
        return etatCompile;
    }

    public void setEtatCompile(String etatCompile) {
        this.etatCompile = etatCompile;
    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }

    public void setJasperPrint(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public HashMap getParametres() {
        return parametres;
    }

    public void setParametres(HashMap parametres) {
        this.parametres = parametres;
    }
}
