package mena.gov.bf.reporting.service;

import mena.gov.bf.bean.LigneBudgetaire;
import mena.gov.bf.domain.LigneBudgetaireContrat;
import mena.gov.bf.reporting.utils.Copyright;
import mena.gov.bf.reporting.utils.ReportService;
import mena.gov.bf.repository.LigneBudgetaireContratRepository;
import mena.gov.bf.service.LigneBudgetaireContratService;
import mena.gov.bf.service.dto.ContratDTO;

import mena.gov.bf.service.dto.EngagementDTO;
import mena.gov.bf.service.dto.LigneBudgetaireContratDTO;
import mena.gov.bf.service.mapper.LigneBudgetaireContratMapper;
import mena.gov.bf.service.dto.LiquidationDTO;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportingService {
    private final ReportService reportService;
    private final LigneBudgetaireContratService ligneBudgetaireContratService;

    @Autowired
    LigneBudgetaireContratMapper ligneBudgetaireContratMapper;
    @Autowired
    LigneBudgetaireContratRepository ligneBudgetaireContratRepository;


    private final Logger log = LoggerFactory.getLogger( ReportingService.class );
    public ReportingService(ReportService reportService, LigneBudgetaireContratService ligneBudgetaireContratService) {
        this.reportService = reportService;
        this.ligneBudgetaireContratService = ligneBudgetaireContratService;

    }

    public byte[] imprimeContrat(ContratDTO contratDTO) {


/*
        List<LigneBudgetaireContratDTO> ligneBudgetaireContratDTOS = ligneBudgetaireContratService.findLignebudgetaireByContratId(contratId);
*/

        HashMap<String, Object> parametres = new HashMap<>();
        System.out.println("==========================Laurent====================================");
        System.out.println(contratDTO);
        System.out.println("==========================Laurent====================================");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String jourDelivre = LocalDate.now().format(formatter);
        parametres.put("jourDelivre", jourDelivre);
        /* parametres.put("Programme", ligneBudgetaireContratDTOS.get(0).);*/
        parametres.put(Copyright.PARAM_COPY_RIGHT, Copyright.COPYRIGHT);
        List<ContratDTO> contratDTOS = new ArrayList<>();
        contratDTOS.add(contratDTO);

        JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(contratDTOS);
        return reportService.createReports("etatContrat.jasper", datasource, parametres);

    }

    public byte[] imprimeContratReception(ContratDTO contratDTO) {
        HashMap<String, Object> parametres = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String jourDelivre = LocalDate.now().format(formatter);
        parametres.put("jourDelivre", jourDelivre);
        parametres.put(Copyright.PARAM_COPY_RIGHT, Copyright.COPYRIGHT);
        List<ContratDTO> contratDTOS = new ArrayList<>();
        contratDTOS.add(contratDTO);

        JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(contratDTOS);
        return reportService.createReports("etatContrat_reception.jasper", datasource, parametres);

    }


    public byte[] imprimeEngagement(EngagementDTO engagementDTO) {

        List<LigneBudgetaire> ligneBudgetaires = new ArrayList<>();
        engagementDTO.getLigneBudgetaireContrats().forEach(ligneBudgetaireContratDTO -> {
            System.out.println("==========================Laurent====================================");
            System.out.println(ligneBudgetaireContratDTO);
            System.out.println("==========================Laurent====================================");
            ligneBudgetaires.add(ligneBudgetaireContratDTO.getLigneBudget());

        });

        engagementDTO.setLigneBudgetaires(ligneBudgetaires);
        for (int i = 0; i < engagementDTO.getLigneBudgetaires().size(); i++) {
            engagementDTO.getLigneBudgetaires().get(i).setMontantdejaengage(engagementDTO.getLigneBudgetaireContrats().get(i).getMontantdejaengage());
            engagementDTO.getLigneBudgetaires().get(i).setMontantdisponible(engagementDTO.getLigneBudgetaireContrats().get(i).getMontantdisponible());
            engagementDTO.getLigneBudgetaires().get(i).setMontantEstime(engagementDTO.getLigneBudgetaireContrats().get(i).getMontantEstime());
            engagementDTO.getLigneBudgetaires().get(i).setMontantEngage(engagementDTO.getLigneBudgetaireContrats().get(i).getMontantEngage());
        }
        engagementDTO.getLigneBudgetaires().forEach(ligneBudgetaire -> {
            System.out.println("==========================Laurent====================================");
            System.out.println(ligneBudgetaire);
            System.out.println("==========================Laurent====================================");

        });
        HashMap<String, Object> parametres = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String jourDelivre = LocalDate.now().format(formatter);
        parametres.put("jourDelivre", jourDelivre);
        parametres.put("reference", engagementDTO.getReference());
        parametres.put(Copyright.PARAM_COPY_RIGHT, Copyright.COPYRIGHT);


        List<EngagementDTO> engagementDTOS = new ArrayList<>();
        engagementDTOS.add(engagementDTO);


       /* DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String jourDelivre = LocalDate.now().format(formatter);
        parametres.put("jourDelivre", jourDelivre);
        parametres.put(ConstanteGlobale.PARAM_COPY_RIGHT, ConstanteGlobale.COPYRIGHT);
        parametres.put("nomActivite", activites.get(0).getNomActivite());
       */


        JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(engagementDTOS);
        return reportService.createReports("engagement.jasper", datasource, parametres);
    }

    public byte[] imprimeLiquidation(LiquidationDTO liquidationDTO) {
        System.out.println("==========================liquidationDTO====================================");
        System.out.println(liquidationDTO);
        System.out.println("==========================liquidationDTO====================================");
        HashMap<String, Object> parametres = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String jourDelivre = LocalDate.now().format(formatter);
        parametres.put("jourDelivre", jourDelivre);
        parametres.put(Copyright.PARAM_COPY_RIGHT, Copyright.COPYRIGHT);
        List<LiquidationDTO> liquidationDTOS = new ArrayList<>();
        liquidationDTOS.add(liquidationDTO);

        JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(liquidationDTOS);
        return reportService.createReports("etatliquidation.jasper", datasource, parametres);

    }
}
