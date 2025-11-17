package com.example.tpov2.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Objects;

@Node("Usuario")
public class Usuario {

    @Id
    private String userId;
    private String nombre;
    private Integer edad; // Añadido

    public Usuario() {
    }

    public Usuario(String userId, String nombre, Integer edad) {
        this.userId = userId;
        this.nombre = nombre;
        this.edad = edad; // Añadido
    }

    public String getUserId() {
        return userId;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getEdad() {
        return edad; // Añadido
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(userId, usuario.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "userId='" + userId + '\'' +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad + // Añadido
                '}';
    }
}
