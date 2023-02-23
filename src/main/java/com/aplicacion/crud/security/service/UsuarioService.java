package com.tutorial.crud.security.service;

import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//La transaccional es para mantener la coherencia en la bbdd.
// Hay varios accesos, uno escribe una tabla y el otro escribe al mismo tiempo se puede crear una incoherencia
//Es para evitar eso, si falla una operación se hace un rollback y se vuelve al estado anterior


@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository; // Lo inyectamos con Autowired

    //obtener nobre usuario
    //AQUÍ EL NOMBRE DEL MÉTODO DA IGUAL, ES EN EL REPOSITORY DONDE IMPORTA, AQUÍ COMO SI LO LLAMAS AMBROSIO
    public Optional<Usuario> getByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    //existe por nombre usuario
    public boolean existsByNombreUsuario(String nombreUsuario){
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    //existe por email
    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    //guardar
    public void save(Usuario usuario){
        usuarioRepository.save(usuario);
    }
}
