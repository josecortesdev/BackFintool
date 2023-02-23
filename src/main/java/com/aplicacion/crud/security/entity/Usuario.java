package com.tutorial.crud.security.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity   // para la bbdd
public class Usuario {   // Creamos id, nombre, nombreusuario, email, password y los roles, que son te tipo Set<Rol>
    @Id   // id es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull     // ponemos notnull en vez de notblank porque son primitivos
    private String nombre;
    @NotNull
    private String apellido;
    @NotNull
    @Column(unique = true)   // a mayores, va a ser único, este nombre de usuario no se puede repetir
    private String nombreUsuario;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id")) // como estoy relacionando con la clase Rol, necesito este inverse
    private Set<Rol> roles = new HashSet<>();            // Estos son los roles, tipo Set<Rol>, un conjunto

    //constructor vacío

    public Usuario() {
    }

    //generamos getters, setters, constructor
    //el constructor no incluye los roles pero sí los getters y setters

    public Usuario(@NotNull String nombre, @NotNull String apellido, @NotNull String nombreUsuario,
                   @NotNull String email,
                   @NotNull String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
