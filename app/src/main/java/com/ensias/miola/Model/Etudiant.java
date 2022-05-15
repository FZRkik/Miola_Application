package com.ensias.miola.Model;

public class Etudiant {
    public String cne, date_naissance,fullname,mail,id ,phone_nbr,pw, type ;

    public Etudiant(){}

    public Etudiant(String cne, String date_naissance, String id ,String fullname, String mail, String phone_nbr, String pw, String type) {
        this.cne = cne;
        this.date_naissance = date_naissance;
        this.fullname = fullname;
        this.mail = mail;
        this.id=id;
        this.phone_nbr = phone_nbr;
        this.pw = pw;
        this.type = type;
    }

    public String getCne() {
        return cne;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    public String getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(String date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone_nbr() {
        return phone_nbr;
    }

    public void setPhone_nbr(String phone_nbr) {
        this.phone_nbr = phone_nbr;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
