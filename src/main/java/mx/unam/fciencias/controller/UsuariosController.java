/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.fciencias.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import mx.unam.fciencias.dao.UsuarioDAO;
import mx.unam.fciencias.model.dto.UsuarioDto;
import org.primefaces.context.RequestContext;


/**
 *
 * @author juanherrera
 */
@ManagedBean
@SessionScoped
public class UsuariosController {
    
    private UsuarioDto usuario;
    
    private Collection<UsuarioDto> usuarios;
    
    private UsuarioDto usuarioSeleccionado;
    
    private UsuarioDAO usuarioDAO;
    
    private UsuarioDto usuarioSesion;
    
    //valores para correo.    
    final String miCorreo = "fractal.enterprise.djr@gmail.com";
    final String miContraseña = "fibonacciproject";
    final String servidorSMTP = "smtp.gmail.com";
    final String puertoEnvio = "465";
    String mailReceptor = null;
    String asunto = null;
    String cuerpo = null;
    //--correo
    
    @PostConstruct
    public void init(){
        usuarioDAO=new UsuarioDAO();
        usuario=new UsuarioDto();
        usuarioSesion = new UsuarioDto();
        usuarioSeleccionado = new UsuarioDto();
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
           
        }
        else{
             FacesContext fc=FacesContext.getCurrentInstance();
            fc.addMessage("validacion", 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Error en la operacion"));
   
       }
        usuarios=new ArrayList<UsuarioDto>();
        usuarios.addAll(usuarioDAO.selectAllUsuarios());
    }

    public String revisaInicio(){
        if(usuarios.contains(usuarioSesion)){
           System.out.println("igual");
            //return true;
            return "consulta";
            }
        else{
             FacesContext fc=FacesContext.getCurrentInstance();
            fc.addMessage("validacion", 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Error en usuario o contraseña"));
            System.out.println("" + usuarioSesion.getNombreDeUsuario() + " " + usuarioSesion.getContraseña() );
            //return false;
            return "mensaje";
        }
    }
    
    public String getNombrePorCorreo(){
    return (usuarioDAO.findByMail(usuarioSeleccionado.getCorreo())).getNombreDeUsuario();
    }

    public String getClavePorCorreo(){
    return (usuarioDAO.findByMail(usuarioSeleccionado.getCorreo())).getContraseña();
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
    
    public UsuarioDto getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(UsuarioDto usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }
    
    public void mandaCorreo(){
        System.out.println("entra a findByMail");
    usuarioSeleccionado = usuarioDAO.findByMail(usuarioSeleccionado.getCorreo());
            System.out.println("Sale de Find entra a enviar");
            System.out.println(usuarioSeleccionado.getCorreo());
            System.out.println(usuarioSeleccionado.getNombreDeUsuario());
            System.out.println(usuarioSeleccionado.getContraseña());
    enviarMail(usuarioSeleccionado.getCorreo(),"FIBO" ,usuarioSeleccionado.getNombreDeUsuario(),usuarioSeleccionado.getContraseña());
    }
    
    public void enviarMail(String mailReceptor, String asunto,
            String usuario, String clave) {
        this.mailReceptor = mailReceptor;
        this.asunto = asunto;
        this.cuerpo = "Has solicitado tus datos\nTú nombre de usuario es : " + usuario + "\n y tu contraseña es: " + clave;

        Properties props = new Properties();
        props.put("mail.smtp.user", miCorreo);
        props.put("mail.smtp.host", servidorSMTP);
        props.put("mail.smtp.port", puertoEnvio);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", puertoEnvio);
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        SecurityManager security = System.getSecurityManager();

        try {
            Authenticator auth = new autentificadorSMTP();
            Session session = Session.getInstance(props, auth);
            // session.setDebug(true);

            MimeMessage msg = new MimeMessage(session);
            msg.setText(cuerpo);
            msg.setSubject(asunto);
            msg.setFrom(new InternetAddress(miCorreo));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    mailReceptor));
            Transport.send(msg);
        } catch (Exception mex) {
            mex.printStackTrace();
        }

    }

    private class autentificadorSMTP extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(miCorreo, miContraseña);
        }
    }

    
    
}
