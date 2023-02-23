package com.tutorial.crud.security.controller;

import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.security.dto.JwtDto;
import com.tutorial.crud.security.dto.LoginUsuario;
import com.tutorial.crud.security.dto.NuevoUsuario;
import com.tutorial.crud.security.entity.Rol;
import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.enums.RolNombre;
import com.tutorial.crud.security.jwt.JwtProvider;
import com.tutorial.crud.security.service.RolService;
import com.tutorial.crud.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")   // En el mainsecurity para loguearnos pusimos auth/** , va a ser auth
@CrossOrigin(origins = "*")  // si lo dejamos sin ningún paréntesis es que podemos accerder desde cualquier url pero
public class AuthController {

    //inyectamos los componente que nos hacen falta
    //passwordenc., authenticationman., usuarioservice, rolservice y un jowtprovider para crear los tokens
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

 //Este método es para nuevo usuario
    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
 //Le pasamos como parámetro un nuevousuario
 //Es un REQUESTBODY porque es un json. Lo que hace es que una vez que se ejecuta el método, lo convierte en
 //una clase java tipo nuevousuario
//para validarlo, bindingresult

        //si tiene errores
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
        //si ya existe el campo
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        //si ya existe el email
        if(usuarioService.existsByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity(new Mensaje("ese email ya existe"), HttpStatus.BAD_REQUEST);

        //si está bien, creamos un nuevo usuario
        Usuario usuario =
                new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getApellido(), nuevoUsuario.getNombreUsuario(),
                        nuevoUsuario.getEmail(),
                        passwordEncoder.encode(nuevoUsuario.getPassword()));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());   // rol user. Ponemos get debido al optional
//COMENTO EL IF PARA CREAR UN ADMIN
        if(nuevoUsuario.getRoles().contains("admin"))  // si contiene "admin"
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());  // rol admin

        usuario.setRoles(roles); // estos roles, se los asignamos al usuario

        usuarioService.save(usuario); // guardamos el usuario

        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);  // devolvemos el ResponseEntity
    }


 //Este método es para login
    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
  // Con el responseentity<jwtdto> va a delvover un token

 //Si bindingresult tiene errores... (copiamos de arriba para no repetir)
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
        Authentication authentication =   // authentication, autenticamos al usuario. Pondremos en marcha el jwttokenfilter
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication); //string jwt es el mismo token que vamos a devolver
        //pasamos authentication: a partir de la autenticación, generamos el token
        //si vas al provider,


        //por último, el jwtdto
        JwtDto jwtDto = new JwtDto(jwt);
// el primer parámetro es el token, el siguiente es el nombre de usuario y las authorities

        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
}
