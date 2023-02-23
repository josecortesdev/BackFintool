package com.tutorial.crud.security.dto;


// Esta clase la vamos a utilizar en el momento en que hagamos un login,
// pues nos va a devolver el responseentity del controlador, json web token

public class JwtDto {

    //tres campos: el token, bearer y nombreUsuario

    private String token;

    // Colección, va a ser genérico y de authorities

    //constructor, todos excepto bearer


    public JwtDto() {
    }

    public JwtDto(String token) {
        this.token = token;

    }

    // getters y setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
