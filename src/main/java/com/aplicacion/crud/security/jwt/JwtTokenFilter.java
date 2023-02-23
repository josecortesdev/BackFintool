package com.tutorial.crud.security.jwt;

import com.tutorial.crud.security.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// QUIZÁS LA MÁS IMPORTANTE DE LAS TRES, EL TOKENFILTER, SE EJECUTA EN CADA PETICIÓN, COMPRUEBA QUE SEA VÁLIDO EL TOKEN
//La validez la comprueba utilizando el provider
// SI ES VÁLIDO, PERMITE EL ACCESO AL RECURSO. EN CASO CONTRARIO, LANZA LA EXCEPCIÓN
// en el contexto autenticación, también dice quién es el usuario que está autenticado

public class JwtTokenFilter extends OncePerRequestFilter {  // implementamos los métodos de la clase de la que hereda

    private final static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class); // logger copiado

    @Autowired
    JwtProvider jwtProvider;  // La inyectamos

    @Autowired
    UserDetailsServiceImpl userDetailsService;  // inyectamos el servicio

//El método más importante, en contexto de autenticación, va a decir que está autenticado y si el token el válido o no
// nota: repasar autentificación y autenticación
// Una vez por cada petición que se haga del servidor:
// comprueba que el token es válido y que existe el token, se obtiene el usuario, creamos userdetails, lo autenticamos
//Una vez que tenemos autenticado ese usuario, al contexto de autenticación se lo pasamos
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        //usamos un try catch
        try {
            String token = getToken(req); // obtenemos el token. Aquí le estamos pasando el reques
            if(token != null && jwtProvider.validateToken(token)){
                // si el token no es nulo y el jwtprovider . El método validateToken es un booleano
                String nombreUsuario = jwtProvider.getNombreUsuarioFromToken(token); // obtenemos usuario
                UserDetails userDetails = userDetailsService.loadUserByUsername(nombreUsuario);
                //Cargar usuario, le pasamos nombreUsuario
                //lo podemos usar gracias a que hemos inyectado el servicio

//Extraemos el nombre del token y creamos una autenticación
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
// Las autoridades que tiene, porque además de la autenticación, necesitamos la AUTORIZACIÓN, es decir,
//saber quién es quién, que está logueado y a que recursos puede o no puede acceder


//Al contexto de auntenticación, le vamos a asignar ese usuario, que sería securitycontextholder, ponemos el context, setauthentication, pasamos auth
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e){
            logger.error("fail en el método doFilter " + e.getMessage());
        }
        //Si va bien:
        filterChain.doFilter(req, res);
    }

    // Método privado para extraer el token y eliminar la parte 'bearer' + el espacio
    private String getToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");  //
        if(header != null && header.startsWith("Bearer"))
            // si no es null y además, empieza con Bearer,
            // devolvermos un replace, Bearer + espaci y devolvemos una cadena de longitud 0
            return header.replace("Bearer ", "");

        //En caso contrario, null
        return null;
    }
}
