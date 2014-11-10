/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.fciencias.dao;

import java.util.List;
import mx.unam.fciencias.model.dto.ComentarioDto;


/**
 *Esta clase nos ayuda a obtener informaci&oacute;n de la tabla comentarioMateria
 * @author in-d
 */

public class ComentarioDAO extends AbstractDAO<ComentarioDto> {
    
    public ComentarioDAO() {
        super(ComentarioDto.class);
    }
    
    /**
     * Busca en la base de datos un comentario cuyo id coincida con el par&aacute;metro recibido
     * @param numero
     * @return un objeto del tipo ComentarioDto 
     */   
    public ComentarioDto findByNumber(int numero){
        List<ComentarioDto> comentario= em.createNamedQuery(ComentarioDto.FIND_BY_NUMBER).setParameter(1,numero).getResultList();
    if(comentario.size()==0){
        return null;
    }
    else {
        return comentario.get(0);
    }
        
    }
    
    /**
     * con este m&eacute;todo obtenemos todos los comentarios de la tabla 'comentarioMateria' que tengan un identificador distinto de cero
     * 
     * @return devuelve una lista con los todos los comentarios 
     */
    public List<ComentarioDto> selectAllComments(){
        return em.createNamedQuery(ComentarioDto.SELECT_ALL_COMMENTS).getResultList();
    }
    
    /**
     *Realiza una consulta a la tabla 'comentarioMateria'
     * @param materia
     * @return un objeto del tipo ComentarioDto cuyo campo nombreMateria coincide con el que recibe como par&aacute;metro 
     */
     public ComentarioDto findBySubject(String materia){
        List<ComentarioDto> comentario= em.createNamedQuery(ComentarioDto.FIND_BY_SUBJECT).setParameter(1,materia).getResultList();
    if(comentario.size()==0){
        return null;
    }
    else {
        return comentario.get(0);
        }
        
    }
     
     /**
      * *Realiza una consulta a la tabla 'comentarioMateria'
      * Los objetos que regresa este m&eacute;todo coinciden en el campo nombreMateria
      * @return una lista con objetos del tipo ComentarioDto  
      */
     public List<ComentarioDto> selectAllSubjects(String materia){
         List<ComentarioDto> comentario = em.createNamedQuery(ComentarioDto.SELECT_ALL_SUBJECTS).setParameter(1,materia).getResultList();
         return comentario;
        
    }
    
}
