package com.tutorial.crud.security.entity;

import com.tutorial.crud.security.enums.RolNombre;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity    // Esto es una entidad, al igual que Usuario
public class Rol {    // Dos campos: el id y un RolNombre
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Enumerated(EnumType.STRING)     // Le indicamos que es tipo cadena (string), si no lo indicamos, la bbdd crearía un ordinal (un número)
    private RolNombre rolNombre; // enumerador que creamos

    // constructor vacío, otro con rolnombre, getters y setters

    public Rol() {
    }

    public Rol(@NotNull RolNombre rolNombre) {
        this.rolNombre = rolNombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RolNombre getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(RolNombre rolNombre) {
        this.rolNombre = rolNombre;
    }
}
