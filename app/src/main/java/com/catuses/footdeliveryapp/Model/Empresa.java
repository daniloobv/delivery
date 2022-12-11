package com.catuses.footdeliveryapp.Model;

public class Empresa {
    private String id;                  //id empresa
    private String user_id;             // esta empresa esta asociada con este usuario
    private String categoria_id;        //categoria de la empresa
    private String nombre_comercial;    //nombre comercial de la empresa
    private String pais_id;             //pais del pais registrado
    private String departamento_id;        //departamento del pais registrado
    private String empresa_id;
    private String telefono;
    private String photo;

    public Empresa() {
    }

    public Empresa(String id, String user_id, String categoria_id, String nombre_comercial, String pais_id, String departamento_id, String empresa_id, String telefono, String photo ) {

        this.id = id;
        this.user_id = user_id;
        this.categoria_id = categoria_id;
        this.nombre_comercial = nombre_comercial;
        this.pais_id = pais_id;
        this.departamento_id = departamento_id;
        this.empresa_id = empresa_id;
        this.telefono = telefono;
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(String categoria_id) {
        this.categoria_id = categoria_id;
    }

    public String getNombre_comercial() {
        return nombre_comercial;
    }

    public void setNombre_comercial(String nombre_comercial) {
        this.nombre_comercial = nombre_comercial;
    }

    public String getPais_id() {
        return pais_id;
    }

    public void setPais_id(String pais_id) {
        this.pais_id = pais_id;
    }

    public String getDepartamento_id() {
        return departamento_id;
    }

    public void setDepartamento_id(String departamento_id) {
        this.departamento_id = departamento_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(String empresa_id) {
        this.empresa_id = empresa_id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
