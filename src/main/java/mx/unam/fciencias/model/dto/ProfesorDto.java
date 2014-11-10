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
@Table(name = "profesor") 
@NamedQueries(
        {
        @NamedQuery(name=ProfesorDto.SELECT_ALL_NAMES,query="SELECT a.nombreDeProfesor FROM ProfesorDto a")    
        }
)

/**
 *
 * @author in-d
 */
public class ProfesorDto implements Serializable {
    //PARA SELECCIONAR TODOS LOS atributos de la tabla
    public static final String SELECT_ALL_NAMES="select all names"; 
    @Id
    @NotNull
    @Size(min = 2,max = 60)
    private String nombreDeProfesor;
    
    @Size(min = 1,max = 60)
    private String calificación;
    
    public ProfesorDto(){
    }
    
    public ProfesorDto(String n,String c){
        nombreDeProfesor = n;
        calificación = c;
    }
    
    public String getNombreProfesor(){
        return nombreDeProfesor;
    
    }
    
    public String getCalificacion(){
        return calificación;
    }
    
    
}
