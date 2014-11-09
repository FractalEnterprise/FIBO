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
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import mx.unam.fciencias.dao.UsuarioDAO;
import mx.unam.fciencias.model.dto.UsuarioDto;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author juanherrera
 */

@ManagedBean
@SessionScoped
public class BeanDatos {
    
   private PieChartModel model;
   
    public BeanDatos() {
        model = new PieChartModel();
        model.set("Bueno ", 540);
        model.set("Malo ", 902);
        model.set("Feo ", 121);
       // model.setTitle("Custom Pie");
       // model.setLegendPosition("w");
       // model.setFill(false);
       // model.setShowDataLabels(true);
       // model.setDiameter(150);
    }

    public PieChartModel getModel() {
        return model;
    }
} 