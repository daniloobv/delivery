package com.catuses.footdeliveryapp.Model;

public class Categoria {
    private String categoria;
    private String categoria_id;
    private String photo;

    public Categoria() {
    }

    public Categoria(String categoria, String categoria_id, String photo) {
        this.categoria = categoria;
        this.categoria_id = categoria_id;
        this.photo = photo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(String categoria_id) {
        this.categoria_id = categoria_id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

