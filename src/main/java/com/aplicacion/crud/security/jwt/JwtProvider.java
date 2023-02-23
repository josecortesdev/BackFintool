package com.tutorial.crud.security.jwt;

import com.tutorial.crud.security.entity.UsuarioPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// ESTA CLASE, EL PROVIDER, LO QUE HACE ES GENERAR EL TOKEN
// Y TIENE LOS MÉTODOS PARA VALIDAR SI ESTÁ BIEN FORMADO EL TOKEN

@Component
public class JwtProvider { // No implementa ni hereda de ninguna otra clase

    //También tiene el logger, lo hemos copiado de la otra clase
    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class); // Recuerda modificar la clase

    //Estos dos valores, los tenemos en el aplication properties

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    // método para generar el token, como parámetro lleva el authentication
    public String generateToken(Authentication authentication){

        //Necesitamos un usuarioprincipal, tiraba error y le hacemos un casteo pulsando en el botón.
        //Con el casteo se incluye el (UsuarioPrincipal)
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();

        //Una vez que tenemos el principal autenticado, vamos a construir el token
        List<String> roles =
                usuarioPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return Jwts.builder()
                .setSubject(usuarioPrincipal.getUsername())  // le pasamos el usuario
                .claim("roles", roles)
                .setIssuedAt(new Date()) // ponemos fecha del MOMENTO
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                //Fecha de EXPIRACIÓN. Va desde el momento actual, hasta el momento dado
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())  // lo firmamos con el tipo de algoritmo y el secret
                .compact();
    }

    // método para obtener el nombre usuario
    public String getNombreUsuarioFromToken(String token){

        //parser, firmamos con el secret, le pasamos el token, obtenemos el body y el subject
        // el subject era el nombre de usuario
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }

    // método para validar el token
    public boolean validateToken(String token){

        //ponemos un try catch
        //lanzamos varias excepciones (varios catch)

        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
             // le pasamos la firma (secret) y el token
            return true; // si no hay problema devuelve cierto, en caso contrario devuelve el false que puedes ver abajo
        }catch (MalformedJwtException e){  // mal formado
            logger.error("token mal formado");
        }catch (UnsupportedJwtException e){  // no soportado
            logger.error("token no soportado");
        }catch (ExpiredJwtException e){  // que haya expirado
            logger.error("token expirado");
        }catch (IllegalArgumentException e){  // que esté vacío
            logger.error("token vacío");
        }catch (SignatureException e){  // fallo con la firma
            logger.error("fail en la firma");
        }
        return false;  // si hubiese problema con lo del try y ocurre cualquiera de las excepciones, devuelve false
    }
}
