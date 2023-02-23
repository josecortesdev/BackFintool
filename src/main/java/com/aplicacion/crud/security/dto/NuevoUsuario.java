package com.tutorial.crud.security.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

// Esta clase la vamos a usar para mandar un nuevo usuario
//Es igual a la clase usuario, no lleva id

public class NuevoUsuario {

    //campos

    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotBlank
    private String nombreUsuario;
    @Email
    private String email;
    @NotBlank
    private String password;
    private Set<String> roles = new HashSet<>();
    // Usamos un hashset porque al utilizar una API REST, vamos a usar un json
    // como esto es tipo json, es mejor usar cadenas, así el tráfico va a ser más ágil
    //Con el nombre usuario vamos a hacer lo mismo, va a tener solo dos campos, nombreusuario y email
    //esto lo hacemos porque si el usuario que tenemos tiene nombre,nombreusuario,email,password,dirección,tfno,foto..
    //Entonces: por cada petición que hacemos para el login, solo necesitamos el nombreusuario y la contraseña
    //si mandamos todos los datos que hemos escrito, el tráfico entre cliente y servidor va a colapsar porque los datos serían
    //mucho mayores. Con esto lo que hacemos es agilizar, mandamos un json y a poder ser, cadenas todot
    //Esta sería la forma más correcta y ágil de hacerlo

    //Resumen: cuanto más fluido sea el tráfico, cuanto menos datos envíes, la aplicación va a ser más eficiente


    //generamos getters y setters

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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }


    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
