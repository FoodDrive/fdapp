package com.example.dell.fddesign;

/**
 * Created by DELL on 07-03-2017.
 */

public class IndivData {
    public String Name ;
    public String Addr;
    public String Pswd;
    public String Contact,Email,Categ;
    public IndivData()
    {

    }
    public IndivData(String Name,String Addr,String Pswd, String Contact,String Email, String categ){
        this.Name = Name;
        this.Addr = Addr;
        this.Contact = Contact;
        this.Email = Email;
        this.Pswd = Pswd;
        this.Categ=categ;
    }
}
