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
        @NamedQuery(name=UsuarioDto.SELECT_ALL,query="SELECT a FROM UsuarioDto a")
        }
)
public class UsuarioDto implements Serializable{
    public static final String SELECT_ALL="select all";
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
    
    
    /*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    
    @NotNull
    @Size(min = 2,max = 50)
    private String nombre;
    
    @NotNull
    @Size(min = 2,max = 50)
    private String apellidoPaterno;
    
    @NotNull
    @Size(min = 2,max = 50)
    private String apellidoMaterno;
    
    @NotNull
    @Size(max = 9)
    private String numeroCuenta;

    public UsuarioDto() {
    }

    public UsuarioDto(String nombre, String apellidoPaterno, String apellidoMaterno, String numeroCuenta) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.numeroCuenta = numeroCuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    */
    
    
    
    @Override
    public boolean equals(Object object){
        if( object==null || !(object instanceof UsuarioDto) ){
            return false;
        }
        
        
        UsuarioDto usuario=(UsuarioDto)object;
         if ((this.nombreDeUsuario == null && usuario.nombreDeUsuario != null) || (this.nombreDeUsuario != null && !this.nombreDeUsuario.equals(usuario.nombreDeUsuario))) {
            return false;
        }
        if(true /*usuario.getNumeroCuenta()!=null && usuario.getNumeroCuenta().equals(this.numeroCuenta)*/ ){
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
