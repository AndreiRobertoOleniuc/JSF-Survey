/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.andrei.survey;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Andrei Oleniuc
 */
@Named(value = "model")
@SessionScoped
public class Model implements Serializable {

    private final SAXBuilder builderBenutzer;
    private final File xmlFileBenutzer;
    private final SAXBuilder builderEintrage;
    private final File xmlFileEintrage;

    private boolean isLoggedIn;
    @Size(min = 1, message = "Please enter the Email")
    @Pattern(regexp = "^(.+)@(.+)$", message = "Format is Wrong")
    private String email;
    @Size(min = 1, message = "Please enter the Password")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Format is Wrong")
    private String password;
    private String ferien;
    @Size(min = 1, message = "Please enter the Ort")
    @Pattern(regexp = "^[a-zA-Z0-9_\\s]+$", message = "Format is Wrong")
    private String ort;
    @Size(min = 1, message = "Please enter the Aktivität")
    @Pattern(regexp = "^[a-zA-Z0-9_\\s]+$", message = "Format is Wrong")
    private String akt;
    private int id;

    public Model() {
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        path = path + "WEB-INF\\User.xml";
        xmlFileBenutzer = new File(path);
        builderBenutzer = new SAXBuilder();
        String path2 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        path2 = path2 + "WEB-INF\\Submits.xml";
        xmlFileEintrage = new File(path2);
        builderEintrage = new SAXBuilder();
    }

    public String doLogin() {
        try {
            Document doc = (Document) builderBenutzer.build(xmlFileBenutzer);
            Element root = doc.getRootElement();
            List list = root.getChildren("user");
            for (int i = 0; i < list.size(); i++) {
                Element node = (Element) list.get(i);
                String xmlemail = node.getChildText("email");
                String xmlpassword = node.getChildText("password");
                if (xmlemail.equals(email) && xmlpassword.equals(password)) {
                    this.isLoggedIn = true;
                    id = Integer.parseInt(node.getChildText("id"));
                    return "/secured/Auswahl1";
                }
            }
        } catch (JDOMException ex) {
            Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/index";
    }

    public String saveData() {
        try {
            Document doc = (Document) builderEintrage.build(xmlFileEintrage);
            Element root = doc.getRootElement();
            Element sub = new Element("sub");
            sub.addContent(new Element("id").setText(Integer.toString(root.getChildren().size() + 1)));
            sub.addContent(new Element("iduser").setText(Integer.toString(id)));
            sub.addContent(new Element("ferien").setText(ferien));
            sub.addContent(new Element("data").setText(ort));
            root.addContent(sub);
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter(xmlFileEintrage));
            return "/secured/Ausgabe";
        } catch (JDOMException ex) {
            Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/index";
    }

    public String saveData2() {
        try {
            Document doc = (Document) builderEintrage.build(xmlFileEintrage);
            Element root = doc.getRootElement();
            Element sub = new Element("sub");
            sub.addContent(new Element("id").setText(Integer.toString(root.getChildren().size() + 1)));
            sub.addContent(new Element("iduser").setText(Integer.toString(id)));
            sub.addContent(new Element("ferien").setText(ferien));
            sub.addContent(new Element("data").setText(akt));
            root.addContent(sub);
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter(xmlFileEintrage));
            return "/secured/Ausgabe";
        } catch (JDOMException ex) {
            Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/index";
    }

    public String ferienJa() {
        ferien = "ja";
        return "/secured/Auswahl2a";
    }

    public String ferienNein() {
        ferien = "nein";
        return "/secured/Auswahl2b";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public String getAkt() {
        return akt;
    }

    public void setAkt(String akt) {
        this.akt = akt;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
