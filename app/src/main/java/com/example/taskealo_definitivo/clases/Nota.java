package com.example.taskealo_definitivo;

public class Nota {
    private String titulo;
    private String cuerpo;
    private String clave;

    public Nota(String titulo, String cuerpo) {
        this.titulo = titulo;
        this.cuerpo = cuerpo;

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

}
