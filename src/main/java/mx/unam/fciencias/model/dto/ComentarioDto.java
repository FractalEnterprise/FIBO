/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.fciencias.model.dto;

import java.util.List;
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

/**
 *Con esta clase manipulamos la tabla "comentarioMateria" de la base de datos
 * para realizar consultas
 * con la consulta SELECT_ALL_COMMENTS obtenemos todos los comentarios de la tabla que tengan un identificador distinto de cero
 * con la consulta FIND_BY_NUMBER obtenemos un comentario de la tabla cuyo identificador coincida con el que recibe como par&aacute;metro
 * @author in-d
 */


@Entity
@Table(name = "comentarioMateria") 
@NamedQueries(
        {
        @NamedQuery(name=ComentarioDto.FIND_BY_NUMBER,query="SELECT a FROM ComentarioDto a WHERE a.id=?1"),
        @NamedQuery(name=ComentarioDto.SELECT_ALL_COMMENTS,query="SELECT a FROM ComentarioDto a WHERE a.id<>0"),
        @NamedQuery(name=ComentarioDto.FIND_BY_SUBJECT,query="SELECT a.comentarioMateria FROM ComentarioDto a WHERE a.nombreMateria=?1"),
        @NamedQuery(name=ComentarioDto.SELECT_ALL_SUBJECTS,query="SELECT a FROM ComentarioDto a WHERE a.nombreMateria = ?1 ")
        }
)
public class ComentarioDto implements Serializable{
    //la siguiente variable se utilizará para consultar un comentario Con un id recibido como parametro de la tabla comentarioMateria
    public static final String FIND_BY_NUMBER="select By number"; 
    //PARA SELECCIONAR TODOS LOS COMENTARIOS DE LA TABLA
    public static final String SELECT_ALL_COMMENTS="select all comments"; 
     //la siguiente variable se utilizará para consultar un comentario con un nombre de materia recibido como parametro en la tabla comentarioMateria
    public static final String FIND_BY_SUBJECT="find by subject"; 
    //se utilizar&aacute; para consultar 
    public static final String SELECT_ALL_SUBJECTS="select all subjects";
    //Declaramos los atributos de la tabla comentarioMateria:
    @Id
    @Size(min = 2,max = 60)
    private String nombreUsuario;
   
   
    @Size(min = 5,max = 60)
    private String nombreMateria;
    
    
    @Size(min = 6,max = 70)
    private String semestreMateria;
    
    @NotNull
    //@Size(max = 10)//??
    private int id;
    
    @Size(min = 5,max = 255)
    private String comentarioMateria;
    
    private List<ComentarioDto> listaDeCom; //una lista que contendrá los comentarios de la tabla comentarioMateria
    
     public ComentarioDto() {
    }

    public ComentarioDto(String nombreUsuario, String nombreMateria, String semestreMateria,int id,String comentarioMateria) {
        this.nombreUsuario = nombreUsuario;
        this.nombreMateria = nombreMateria;
        this.semestreMateria = semestreMateria;
        this.id=id;
        this.comentarioMateria = comentarioMateria;
    }

    
    /**
     * Actualiza un comentario con el par&aacute;metro dado
     */
    public void setComentario(String c){
        this.comentarioMateria = c;
    
    }
    
    /**
     * @return regresa un comentario
     */
    public String getComentario(){
        return this.comentarioMateria;
    }
    
    
    public List<ComentarioDto> getListaComentarios(){
        return listaDeCom;
    }
    /**
     * Actualiza un nombre de usuario con el par&aacute;metro dado
     */
    public void setNombreUsuario(String n){
        this.nombreUsuario = n;
    }
    /**
     * @return regresa un nombre de usuario
     */
    public String getNombreUsuario(){
        return this.nombreUsuario;
    }
    /**
     * Actualiza un nombre de materia con el par&aacute;metro dado
     */
    public void setNombreMateria(String n){
        this.nombreMateria = n;
    }
    /**
     * @return regresa un nombre de materia
     */
    public String getNombreMateria(){
        return this.nombreMateria;
        }
    
     /**
     * Actualiza el nombre de semestre de una materia con el par&aacute;metro dado
     */
    public void setSemestreMateria(String s){
        this.semestreMateria = s;
    }
    
    /**
     * @return regresa el semestre en que se imparte una materia
     */
    public String getSemestreMateria(){
        return this.semestreMateria;
    }
    
    public int getId(){
        return this.id;
    }
    
}
