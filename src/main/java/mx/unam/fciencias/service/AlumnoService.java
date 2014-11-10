/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.fciencias.service;


import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import mx.unam.fciencias.dao.ComentarioDAO;
import mx.unam.fciencias.model.dto.ComentarioDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Service;
/**
 *
 * @author in-d
 */
//@Service("alumnoService")
public class AlumnoService implements Serializable{

//@Autowired
private ComentarioDAO comDAO;//guardará un comentario
public AlumnoService(){
}

public List<ComentarioDto> selectAllComentarios(){
return comDAO.selectAllComments();
}
    
    
}
