/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.fciencias.controller;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.unam.fciencias.dao.UsuarioDAO;
import mx.unam.fciencias.model.dto.UsuarioDto;


/**
 *
 * @author guillermorojas
 */
@ManagedBean
@SessionScoped
public class UsuariosController {
    
    private UsuarioDto usuario;
    
    private Collection<UsuarioDto> usuarios;
    
    private UsuarioDto usuarioSeleccionado;
    
    private UsuarioDAO usuarioDAO;
    
    @PostConstruct
    public void init(){
        usuarioDAO=new UsuarioDAO();
        usuario=new UsuarioDto();
        usuarios=new ArrayList<UsuarioDto>();
        usuarios.addAll(usuarioDAO.selectAllUsuarios());
    }
    
    public void guardaUsuario(){
        if(usuarios.contains(usuario)){
           FacesContext fc=FacesContext.getCurrentInstance();
            fc.addMessage("validacion", 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Usuario repetido"));
        }else{

        usuarioDAO.getEm().getTransaction().begin();
          usuarioDAO.create(usuario);
            usuarioDAO.getEm().getTransaction().commit();
            
            usuario=new UsuarioDto();
        }
        usuarios=new ArrayList<UsuarioDto>();
        usuarios.addAll(usuarioDAO.selectAllUsuarios());
    }
    
    public void borraUsuario(UsuarioDto user){
        if(usuarios.contains(user)){
            usuarioDAO.getEm().getTransaction().begin();
            usuarioDAO.delete(user.getNombreDeUsuario());
            usuarioDAO.getEm().getTransaction().commit();
        }
        else{
             FacesContext fc=FacesContext.getCurrentInstance();
            fc.addMessage("validacion", 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Error en la operacion"));
   
       }
        usuarios=new ArrayList<UsuarioDto>();
        usuarios.addAll(usuarioDAO.selectAllUsuarios());
    }

    public UsuarioDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDto usuario) {
        this.usuario = usuario;
    }

    public Collection<UsuarioDto> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Collection<UsuarioDto> usuarios) {
        this.usuarios = usuarios;
    }

    public UsuarioDto getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(UsuarioDto usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }
    
    
    
    
}
