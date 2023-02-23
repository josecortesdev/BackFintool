package com.tutorial.crud.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// ESTA CLASE VA A SER LA ENCARGADA DE GENERAR LA SEGURIDAD

//lO QUE HACE ESTA CLASE ES OBTENER LA AUTORIZACIÓN DEL USUARIO PARA HACER O NO COSAS

//SE ENCARGA DE CONVERTIR LA CLASE ROL EN AUTHORITIES, CLASE PROPIA DEL NÚCLEO DE SEGURIDAD DE SPRING BOOT

public class UsuarioPrincipal implements UserDetails {
    // Implementa la interfaz userdetails, por lo que implementamos también los métodos, son bastantes

    //Creamos las variables. Una de estas variables va a ser una colección,

    private String nombre;
    private String nombreUsuario;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    //Es una colección, es genérico
    // GrantedAuthority, es una clase específica de la seguridad


    //constructor con todos los campos

    public UsuarioPrincipal(String nombre, String nombreUsuario, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    //El método más importante de esta clase
    //Es un método estático que se va a llamar build
    //Con este, vamos a asignar los privilegios a cada usuario
    //Es decir, su AUTORIZACIÓN. Si es un administrador o un usuario
    public static UsuarioPrincipal build(Usuario usuario){   // Devuelve un UsuarioPrincipal (con todos sus privilegios)
 //Como parámetro lleva un Usuario, convertimos a un Usuario que representa a la bbdd, en un UsuarioPrincipal que es el que
 //utilizamos para saber los privilegios de cadaa usuario

   // 1. Optenemos los roles con una lista, de GrantedAuthority, llamada authorities
        List<GrantedAuthority> authorities =
                usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol  //usamos función lambda, conviene repasarlo
                .getRolNombre().name())).collect(Collectors.toList());
   // la parte de rol -> etc era de una función lambda
   //hemos obtenido una lista a partir de los roles. Estamos convirtiendo la clase rol en una clase GrantedAuthority

   // 2. Devolvemos un UsuarioPrincipal
        return new UsuarioPrincipal(usuario.getNombre(), usuario.getNombreUsuario(), usuario.getEmail(), usuario.getPassword(), authorities);
   // le hemos pasado por parámetro los campos, incluyendo authorities
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    } // ponemos el password creado

    @Override
    public String getUsername() {
        return nombreUsuario;
    }  // lo habíamos llamado nombreUsuario

    // todos estos booleanos los ponemos a true

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //creamos un getter de email y nombre

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }
}
