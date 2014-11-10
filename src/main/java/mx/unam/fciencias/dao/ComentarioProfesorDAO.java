/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.fciencias.dao;
import java.util.List;
import mx.unam.fciencias.model.dto.ComentarioProfesorDto;



/**
 *
 * @author in-d
 */
public class ComentarioProfesorDAO extends AbstractDAO<ComentarioProfesorDto>{
    
     public ComentarioProfesorDAO() {
        super(ComentarioProfesorDto.class);
    }
    
      public ComentarioProfesorDto findByNumber(int numero){
        List<ComentarioProfesorDto> comentario= em.createNamedQuery(ComentarioProfesorDto.FIND_BY_NUMBER).setParameter(1,numero).getResultList();
    if(comentario.size()==0){
        return null;
    }
    else {
        return comentario.get(0);
    }
        
    }
      
       public List<ComentarioProfesorDto> selectAllComments(){
        return em.createNamedQuery(ComentarioProfesorDto.SELECT_ALL_COMMENTS).getResultList();
    }
     
    
}
