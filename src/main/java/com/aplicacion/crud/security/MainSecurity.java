package com.tutorial.crud.security;

import com.tutorial.crud.security.jwt.JwtEntryPoint;
import com.tutorial.crud.security.jwt.JwtTokenFilter;
import com.tutorial.crud.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


// La clase que organiza un poco todas estas clases de seguridad

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)   // para indicar a qué métodos puede acceder solo el administrador
public class MainSecurity extends WebSecurityConfigurerAdapter { // hereda

    //Los métodos que no lleven una anotación, puede acceder tanto el administrador como un usuario genérico
    //Pero los que lleven la anotación preauthorized solo va a poder accerder el admin

    @Autowired
    UserDetailsServiceImpl userDetailsService;  // inyectamos el servicio

    @Autowired
    JwtEntryPoint jwtEntryPoint; // inyectamos en EntryPoint, es el que devuelve el mensaje de no autorizado

    @Bean   // Es un bean. Para el token
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter();
    }

    @Bean   // Es un bean. Para la contraseña
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    //Nos interesan los 4 primeros métodos de los que nos sale al heredar
    //Puedes verlo al pulsar botón derecho, generate, override methods


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    // usamos el userdetailssertvice, le pasamos el parámetro, obtiene el usuario y le pasamos la contraseña,
    //el bean que tenemos
    }



    @Bean   // es un bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

 //Parte más importante
 //http cors, inhabilitamos csrf, si usasemos cookies, no sería buena idea
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll() // para crear un nuevo usuario y realizar el login /auth/** y se le permite a todot el mundo con permitAll
                .anyRequest().authenticated()    // Para el resto (que no sea admin o usuario simple), hay que estar autenticado
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint) // control de excepciones, que lance el error de no autenticado
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // tipo de política, política de sesión: sin estado -> stateless
        //Porque como hemos dicho, no hay estado, no utilizamos cookies, no almacenamos sesión en el servidor
        //Aquí por cada petición se envía un token

        //por último, añadimos el jwttokenfilter, es el que por cada petición, comprueba el token y pasa
        //del usuario al contexto de autenticación
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
