/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.andrei.survey;

/**
 *
 * @author Andrei Oleniuc
 */
public class Datatable {

    private String id;
    private String iduser;
    private String ferien;
    private String data;

    public Datatable(String id, String iduser, String ferien, String data) {
        this.id = id;
        this.iduser = iduser;
        this.ferien = ferien;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getFerien() {
        return ferien;
    }

    public void setFerien(String ferien) {
        this.ferien = ferien;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
