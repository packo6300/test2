package com.packo.iptv.beans;

public class ItemBean  {
    private String imagen;
    private String nombre;
    private String url;

    public ItemBean(){
        this.imagen="";
        this.nombre="";
        this.url="";
    }
    public ItemBean(String imagen,String nombre,String url){
        this.imagen=imagen;
        this.nombre=nombre;
        this.url=url;
    }
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
