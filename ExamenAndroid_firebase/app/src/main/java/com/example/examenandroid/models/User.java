package com.example.examenandroid.models;

public class User {

    public static final String USER = "users";

    public static final String UID = "id_cliente";
    public static final String NOMBRE = "nombre_cliente";
    public static final String APELLIDOS = "apellidos";
    public static final String DIRECCION = "direccion";
    public static final String CORREO = "correo";
    public static final String TELEFONO = "telefono";
    public static final String COMUNA = "id_comuna";

    public static final String TIPOUSER = "tipoUser";

    private String id_cliente;
    private String nombre_cliente;
    private String apellidos;
    private String direccion;
    private String correo;
    private String telefono;
    private int id_comuna;
    private String tipoUser;

    public User() {
    }

    public User(String id_cliente, String nombre_cliente, String apellidos, String direccion, String correo, String telefono, int id_comuna, String tipoUser) {
        this.id_cliente = id_cliente;
        this.nombre_cliente = nombre_cliente;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.correo = correo;
        this.telefono = telefono;
        this.id_comuna = id_comuna;
        this.tipoUser = tipoUser;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getId_comuna() {
        return id_comuna;
    }

    public void setId_comuna(int id_comuna) {
        this.id_comuna = id_comuna;
    }


    public String getTipouser() {
        return tipoUser;
    }

    public void setTipouser(String tipoUser) {
        this.tipoUser = tipoUser;
    }


}
