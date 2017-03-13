package com.project.vnr.fooddrive;

/**
 * Created by DELL on 11-03-2017.
 */

public class ProfileData {

    public String Name;
    public String Addr;
    public String InchName;
    public String Design;
    public String Desc, Pswd;
    public String Contact, Email, Categ;
    public int status;

    //public int rating,count;
    public ProfileData() {

    }

    public ProfileData(String Name, String Addr, String InchName, String Design, String Desc, String Pswd, String Contact, String Email, String categ, int status) {
        this.Name = Name;
        this.Addr = Addr;
        this.InchName = InchName;
        this.Design = Design;
        this.Desc = Desc;
        this.Contact = Contact;
        this.Email = Email;
        this.Pswd = Pswd;
        this.Categ = categ;
        this.status = status;
        //this.rating=(rating)
    }

    public void updateData(String Name, String Addr, String InchName, String Desc, String Contact, String Email) {
        this.Name = Name;
        this.Addr = Addr;
        this.InchName = InchName;
        this.Desc = Desc;
        this.Contact = Contact;
        this.Email = Email;
    }
    public void updateData(String Name,String Addr, String Contact,String Email ) {
        this.Name = Name;
        this.Addr = Addr;
        this.Contact = Contact;
        this.Email = Email;
    }

}
