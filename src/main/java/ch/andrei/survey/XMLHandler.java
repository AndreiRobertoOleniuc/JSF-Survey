/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.andrei.survey;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import static org.jdom2.Content.CType.Element;
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
public class XMLHandler {

    private final SAXBuilder builderBenutzer;
    private final File xmlFileBenutzer;
    private final SAXBuilder builderEintrage;
    private final File xmlFileEintrage;

    @Inject
    private Model loginBean;

    public XMLHandler() {
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        path = path + "WEB-INF\\User.xml";
        xmlFileBenutzer = new File(path);
        builderBenutzer = new SAXBuilder();
        String path2 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        path2 = path2 + "WEB-INF\\Submits.xml";
        xmlFileEintrage = new File(path2);
        builderEintrage = new SAXBuilder();
    }

    public boolean check(String email, String password) {
        try {
            Document doc = (Document) builderBenutzer.build(xmlFileBenutzer);
            Element root = doc.getRootElement();
            List list = root.getChildren("user");
            for (int i = 0; i < list.size(); i++) {
                Element node = (Element) list.get(i);
                String xmlemail = node.getChildText("email");
                String xmlpassword = node.getChildText("password");
                if (xmlemail.equals(email) && xmlpassword.equals(password)) {
                    loginBean.setIsLoggedIn(true);
                    loginBean.setId(Integer.parseInt(node.getChildText("id")));
                    return true;
                }
            }
        } catch (JDOMException ex) {
            Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
