package com.tutorial.crud.util;

import com.tutorial.crud.security.entity.Rol;
import com.tutorial.crud.security.enums.RolNombre;
import com.tutorial.crud.security.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * MUY IMPORTANTE: ESTA CLASE SÓLO SE EJECUTARÁ UNA VEZ PARA CREAR LOS ROLES.
 * UNA VEZ CREADOS SE DEBERÁ ELIMINAR O BIEN COMENTAR EL CÓDIGO
 *
 */

//Además, esto se puede hacer desde la BBDD aunque lo hago por comandos para aprender
    //En la bbdd vamos a rol y veremos dos roles, uno admin y otro user

@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RolService rolService;

    @Override
    public void run(String... args) throws Exception {

        //Lo que hace es crear dos roles

        //LE QUITO EL COMENTARIO PARA DESPLEGARLO EN HEROKU --------------------------------------
//        Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
//        Rol rolUser = new Rol(RolNombre.ROLE_USER);
//        rolService.save(rolAdmin);
//        rolService.save(rolUser);
        //LE QUITO EL COMENTARIO PARA DESPLEGARLO EN HEROKU --------------------------------------

    }
}
