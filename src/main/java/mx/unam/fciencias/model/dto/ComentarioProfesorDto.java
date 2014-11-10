/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.fciencias.model.dto;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "comentarioProfesor") 
@NamedQueries(
        {
        @NamedQuery(name=ComentarioProfesorDto.FIND_BY_NUMBER,query="SELECT a FROM ComentarioProfesorDto a WHERE a.id=?1"),
        @NamedQuery(name=ComentarioProfesorDto.SELECT_ALL_COMMENTS,query="SELECT a FROM ComentarioProfesorDto a WHERE a.id<>0")    
        }
)


/**
 *Esta clase tiene las instrucciones para acceder a los atributos de la tabla comentarioProfesor
 * @author in-d
 */
public class ComentarioProfesorDto {
     //la siguiente variable se utilizará para consultar un comentario de la tabla comentarioProfesor
    public static final String FIND_BY_NUMBER="find by number"; 
    //PARA SELECCIONAR TODOS LOS COMENTARIOS DE LA TABLA comentarioProfesor
    public static final String SELECT_ALL_COMMENTS="SelectAllComments"; 
    
    
     @Id
    @Size(min = 2,max = 60)
    private String nombreUsuario;
    
    @Size(min = 5,max = 60)
    private String nombreProfesor;
     
     @NotNull
     int id;
     
     @Size(min = 5,max = 255)
     private String comentarioProfesor;
     
     
      public ComentarioProfesorDto() {
    }

    public ComentarioProfesorDto(String nombreUsuario, String n,int id,String c) {
        this.nombreUsuario = nombreUsuario;
        this.nombreProfesor = n;
        this.id=id;
        this.comentarioProfesor = c;
    }

    public String getNombreUsuario(){
        return this.nombreUsuario;
    }
    
    public String getNombreProf(){
        return this.nombreProfesor;
    }
    
    public String getComentario(){
        return this.comentarioProfesor;
       
    }
    
    
     
}
