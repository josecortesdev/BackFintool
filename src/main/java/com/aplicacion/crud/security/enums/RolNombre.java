package com.tutorial.crud.security.enums;

public enum RolNombre {  // tip cadena. Estos van a ser los tipos de rol que vamos a tener
    ROLE_ADMIN, ROLE_USER

    // El usuario va a poder ver los productos y el administrador, además de verlos, va a poder borrarlos,
    //editarlos o crear uno nuevo
    //Podríamos tener tantos roles como quisieramos, según la autorización que va a obtener cada usuario
}
