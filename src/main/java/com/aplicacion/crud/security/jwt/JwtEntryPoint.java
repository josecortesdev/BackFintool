package com.tutorial.crud.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//ESTA CLASE COMPROBARÁ SI HAY UN TOKEN, EN CASO DE QUE NO SEA ASÍ, DEVUELVE UN 40 no autorizado
//RECHAZA TODAS LAS PETICIONES QUE NO ESTÉN AUTENTICADAS

@Component   // es un component
public class JwtEntryPoint implements AuthenticationEntryPoint {
    // implementa el authenticationEntrypoint
    // Implementamos el método commence

//Ponemos un logger, lo usamos en desarrollo para ver cual es el método que nos esté dando error
// en caso de que no funcione la aplicación
    //libreria logger org.slf4j. LoggerFactory de la misma librería, getlogger y la CLASE que tenemos
    private final static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

//modificamos ocn nombres más sencillos, req, res...
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {
// donde pone "throws" significa que lanza dos excepciones: IO y Servlet
        logger.error("fail en el método commence");   // Lanzamos el error utilizando logger. Esto se utliza solo en desarrollo
        //en producción no interesa tenerlo
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "no autorizado"); // envía un error, no autorizado
    }
}
