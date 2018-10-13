package com.packo.iptv.beans;

public class UserBean {
    private String nombre;
    private String apellido;
    private String correo;
    private String password;
    private String lastLogin;

    public void UserBean(){
        this.nombre="";
        this.apellido="";
        this.correo="";
        this.password="";
        this.lastLogin="";
    }
    public void UserBean(String nombre,String apellido,String correo,String password,String lastLogin){
        this.nombre=nombre;
        this.apellido=apellido;
        this.correo=correo;
        this.password=password;
        this.lastLogin=lastLogin;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
}
