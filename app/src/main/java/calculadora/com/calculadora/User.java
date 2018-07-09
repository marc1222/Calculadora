package calculadora.com.calculadora;

import java.util.Date;
import java.util.Objects;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
    @PrimaryKey
   private String Username = "";
   private String name = "";
   private String lastname = "";
   private String pass = "";
   private String telf = "";
   private String university = "";
   private RealmList<Puntuation> Yourpuntuations;
    public User() {

    }
    User(String un, String nombre, String apellidos, String p, String u, String tlf) {
        this.Username = un;
        this.name = nombre;
        this.lastname = apellidos;
        this.pass = p;
        this.telf = tlf;
        this.university = u;
    }

   public static boolean logIn(String n, String p) {

       return false;
   }
    public String getNombre() {  return this.name;    }
    public String getApellido() {  return this.lastname;    }
    public String getPass() {
        return pass;
    }
    public void setPass(String newpass) {
        this.pass = newpass;
    }
    public String getUsername() {
        return this.Username;
    }
    public String getTelf() {
        return telf;
    }
    public String getUniversity() {
        return university;
    }

}
