package com.example.tpov2.model;


public class AmigoConPeso {
    private Usuario amigo;
    private Integer peso;

    public AmigoConPeso() {
    }

    public AmigoConPeso(Usuario amigo, Integer peso) {
        this.amigo = amigo;
        this.peso = peso;
    }

    public Usuario getAmigo() {
        return amigo;
    }

    public void setAmigo(Usuario amigo) {
        this.amigo = amigo;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }
}