/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.fciencias.dao;

import java.util.List;
import mx.unam.fciencias.model.dto.ProfesorDto;

/**
 *
 * @author in-d
 */
public class ProfesorDAO extends AbstractDAO<ProfesorDto> {
    
     public ProfesorDAO() {
        super(ProfesorDto.class);
    }
     
     public List<ProfesorDto> selectAllNames(){
     return em.createNamedQuery(ProfesorDto.SELECT_ALL_NAMES).getResultList();
       
     } 
    
     
    
    
    
}
