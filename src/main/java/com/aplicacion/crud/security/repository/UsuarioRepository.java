package com.tutorial.crud.security.repository;

import com.tutorial.crud.security.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // decimos que es un repositorio
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {  // Es tipo Usuario

    //Tener un usuario a partir de NombreUsuario
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);  // findBy seguido del nombre del campo comenzando en mayus.
    //Va a obtener un usuario a partir de su nombre

    //Comprobar si existe o no por NombreUsuario, va a ser un booleano
    boolean existsByNombreUsuario(String nombreUsuario); // Recuerda, el nombre de este método debe de coincidir con el campo

    //Lo mismo pero con Email
    boolean existsByEmail(String email);

}
