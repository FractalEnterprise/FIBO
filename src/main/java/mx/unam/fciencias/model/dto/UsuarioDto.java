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


/**
 *
 * @author guillermorojas
 */
@Entity
@Table(name = "usuario")
@NamedQueries(
        {
        @NamedQuery(name=UsuarioDto.SELECT_ALL,query="SELECT a FROM UsuarioDto a"),
        @NamedQuery(name=UsuarioDto.FIND_BY_MAIL,query="SELECT a FROM UsuarioDto a WHERE a.correo=?1")
        }
)
public class UsuarioDto implements Serializable{
    public static final String SELECT_ALL="select all";
    public static final String FIND_BY_MAIL="find by mail";
    @Id
    @NotNull
    @Size(min = 2,max = 60)
    private String nombreDeUsuario;
   
    @NotNull
    @Size(min = 5,max = 30)
    private String correo;
    
    @NotNull
    @Size(min = 3,max = 15)
    private String contraseña;
    
    @Size(max = 20)
    private String rango;
    
    
    public UsuarioDto() {
    }

    public UsuarioDto(String nombreDeUsuario, String correo, String contraseña) {
        this.nombreDeUsuario = nombreDeUsuario;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }
    
    

    
    @Override
    public boolean equals(Object object){
        if( object==null || !(object instanceof UsuarioDto) ){
            return false;
        }
        
        
        UsuarioDto usuario=(UsuarioDto)object;
         if ((this.nombreDeUsuario == null && usuario.nombreDeUsuario != null) || (this.nombreDeUsuario != null && !this.nombreDeUsuario.equals(usuario.nombreDeUsuario))) {
            return false;
        }
        if(usuario.getContraseña()!= null && this.contraseña.equals(usuario.getContraseña()) ){
            return true;
        }
        else{
            return false;
        }
    
    
    }    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombreDeUsuario != null ? nombreDeUsuario.hashCode() : 0);
        return hash;
    }

 
}
