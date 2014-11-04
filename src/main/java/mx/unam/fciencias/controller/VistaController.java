/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.fciencias.controller;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author guillermorojas
 */
//Clase ManagedBean, es decir es administrada por el servidor.
//en el archivo faces-config.xml se define su nombre
@ManagedBean(name="controller")
@SessionScoped
public class VistaController {
    
    //encapsulamiento de variables
    private String usuario;
    
    //encapsulamiento de variables
    private Collection<String> lista;

    //anotacion que sirve para indicar metodo a ejecutar despues del constructor,
    @PostConstruct
    public void init(){
        lista=new ArrayList<String>();
    }
    
    
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Collection<String> getLista() {
        return lista;
    }

    public void setLista(Collection<String> lista) {
        this.lista = lista;
    }
    
    
    
    public void guardaElemento(){
        
        if(this.usuario!=null && !this.usuario.isEmpty()){
            lista.add(usuario);

            for(String cadena:lista){
                System.out.println(cadena);
            }
            System.out.println("--------------");
        }
    }
    
    
}
