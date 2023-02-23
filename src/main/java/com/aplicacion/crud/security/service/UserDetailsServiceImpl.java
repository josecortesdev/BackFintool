package com.tutorial.crud.security.service;

import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.entity.UsuarioPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {   // implementamos los métodos

    @Autowired
    UsuarioService usuarioService; // inyectamos el UsuarioService

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.getByNombreUsuario(nombreUsuario).get();
        //como era tipo Usuario y lo que está al otro lado del igual es un optional, daba error
        //lo convertimos usando un .get()
        //si no lo encuentra, lanzaría una excepción not found

        return UsuarioPrincipal.build(usuario); // Utilizamos la clase UsuarioPrincipal, método estático build
        //y construimos el usuario
    }
}
