/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.fciencias.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
import mx.unam.fciencias.dao.ComentarioDAO;
import mx.unam.fciencias.dao.ComentarioProfesorDAO;
import mx.unam.fciencias.dao.ProfesorDAO;
import mx.unam.fciencias.dao.UsuarioDAO;
import mx.unam.fciencias.model.dto.ComentarioDto;
import mx.unam.fciencias.model.dto.ComentarioProfesorDto;
import mx.unam.fciencias.model.dto.ProfesorDto;
import mx.unam.fciencias.model.dto.UsuarioDto;
import org.primefaces.context.RequestContext;


/**
 *
 * @author juanherrera
 */
@ManagedBean
@SessionScoped
public class UsuariosController {
    
    public String nProfesor; //una variable que guardará un nombre de profesor
    public String nMateria;//una variable que guardará un nombre de materia
    private ProfesorDto profesor;
    private ComentarioDto comentarioDto;
    private ComentarioProfesorDAO cpDao;
     public ComentarioDAO comentarioDAO;
     private ProfesorDAO profesorDao;
     
    public List<ProfesorDto> listaProf;//una lista que guardará todos los nombres de profesores
    public List<ComentarioProfesorDto> listaCom;//una lista que guardará todos los comentarios de la tabla comentarioProfesor 
    public List<ComentarioDto> listaComentarios;//una lista que guardará todos los comentarios de la tabla comentarioMateria;
    
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
        
        profesorDao = new ProfesorDAO();
        cpDao = new ComentarioProfesorDAO();
        comentarioDAO = new ComentarioDAO();
        
        comentarioDto = new ComentarioDto();
        profesor = new ProfesorDto();
        
        listaComentarios = new ArrayList<ComentarioDto>();
        listaProf = new ArrayList<ProfesorDto>();
        
         listaProf.addAll(profesorDao.selectAllNames());//
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

    
    /**
     * Devuelve un objeto del tipo ComentarioDto
     * 
     */
    public ComentarioDto getComentario(){
        return comentarioDto;
    }
    
     public List<ProfesorDto> generarListaProf(){
       return listaProf = profesorDao.selectAllNames();
       //System.out.println(listaProf.get(1));
     }
     
     /**
     *@return Regresa una lista con objetos del tipo ComentarioDto
     */
     public List<ComentarioDto> obtenerListaComentarios(){
         //oficialmentre usar el siguiente:
         //return this.listaComentarios;
         
         
         //para hacer pruebas
         this.listaComentarios.addAll(comentarioDAO.selectAllSubjects("Ingeniería de Software"));
         return this.listaComentarios;
     } 
     
    
     
     
     
     /**
     * Agrega un nuevo comentario a la tabla comentarioMateria
     */
    public void agregarComentarioMateria(){
        comentarioDAO.getEm().getTransaction().begin();
        comentarioDAO.create(comentarioDto);
        comentarioDAO.getEm().getTransaction().commit();
        comentarioDto=new ComentarioDto();
        
        listaComentarios=new ArrayList<ComentarioDto>();
        listaComentarios.addAll(comentarioDAO.selectAllComments());
    
     }
 
    /**
     * * Primero actualiza una variable global con el nombre de un profesor, despues
     * Hace una consulta a la tabla comentarioProfesor, buscando el nombre que recibi&oacute; como par&aacute;metro
     * @param n el nombre de un profesor
     */
    public void setNombreProfesor(String n){
        this.nProfesor=n;
        
    }
    
    /**
     
    *@return nProfesor  una cadena que representa el  nombre de un profesor
    */
    public String getNombreProfesor(){
        return this.nProfesor;
        
    }
    
    
    
    /**
     * Hace una consulta a la tabla comentarioMateria, buscando el nombre que recibi&oacute; como par&aacute;metro 
     * los resultados de esa busqueda se almacenan en una lista 'listaComentarios' del tipo List<ComentarioDto>
     */
    public void generaComentariosMateria(){
        //oficialmente usar la sig:
        //this.listaComentarios.addAll(comentarioDAO.selectAllSubjects(getNombreMateria()));
        
        //para realizar pruebas
        this.listaComentarios.addAll(comentarioDAO.selectAllSubjects("Ingeniería de Software"));
    }
    
     /**
    *@return  una cadena que representa el  nombre de una materia
    */
    public String getNombreMateria(){
        return nMateria;
    }

}
