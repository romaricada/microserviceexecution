package mena.gov.bf.service;

import mena.gov.bf.repository.LigneBudgetaireEngagementRepository;
import mena.gov.bf.service.dto.LigneBudgetaireEngagementDTO;
import mena.gov.bf.service.mapper.LigneBudgetaireEngagementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LigneBudgetaireEngagementService {

    @Autowired
    private LigneBudgetaireEngagementService ligneBudgetaireEngagement;

    @Autowired
    private LigneBudgetaireEngagementRepository ligneBudgetaireEngagementRepository;

    @Autowired
    private LigneBudgetaireEngagementMapper ligneBudgetaireEngagementMapper;

    private final Logger log = LoggerFactory.getLogger(LigneBudgetaireContratService.class);


    public List<LigneBudgetaireEngagementDTO> findLigneBudgetaireEngagementByLigneBudgetaire (Long ligneBudgetaireId) {
        return ligneBudgetaireEngagementRepository.findAll().stream().filter(ligneBudgetaireEngagement1 -> ligneBudgetaireEngagement1.getLigneBudgetaireId() != null
        && ligneBudgetaireEngagement1.getLigneBudgetaireId().equals(ligneBudgetaireId)).map(ligneBudgetaireEngagementMapper::toDto).collect(Collectors.toList());
    }
}
