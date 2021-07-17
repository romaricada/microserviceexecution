/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mena.gov.bf.repository;

import mena.gov.bf.domain.Etape;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nafolo
 */
public interface EtapeRepository extends JpaRepository<Etape, String>{
    
}
