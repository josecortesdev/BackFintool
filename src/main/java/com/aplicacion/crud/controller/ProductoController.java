package com.tutorial.crud.controller;

import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.dto.ProductoDto;
import com.tutorial.crud.entity.Producto;
import com.tutorial.crud.service.ProductoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto")
//@CrossOrigin(origins = "http://localhost:4200")  PUERTO LOCAL, SI CAMBIAS A HEROKU, PON EL DE ABAJO, EL ASTERISCO
@CrossOrigin(origins = "*") // PON ESTE SI QUIERES DESPLEGARLO EN HEROKU
public class ProductoController {

    @Autowired
    ProductoService productoService;


    @GetMapping("/lista")
    public ResponseEntity<List<Producto>> list(){
        List<Producto> list = productoService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
 //ESTA ERA LA CORRECTA


    //ESTE ES EL DE PRUEBA
    @GetMapping("/cartera/{idcartera}")   // como recibe variable...
    public ResponseEntity<List<Producto>> list(@PathVariable("idcartera") String idcartera){
        List<Producto> list = productoService.cartera(idcartera);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    //PARA BUSCAR DUPLICADOS, QUE TENGA NOMBRE E IDCARTERA IGUALES
 /*   @GetMapping("/duplicado/{id}")   // como recibe variable...
    public ResponseEntity<List<Producto>> NombreIdcartera(@PathVariable("id") int id){

        List<Producto> NombreIdcartera = productoService.nombreidcartera(id);

        return new ResponseEntity(NombreIdcartera, HttpStatus.OK);
    }*/
//EL BUENO

    //PARA BUSCAR DUPLICADOS, QUE TENGA NOMBRE E IDCARTERA IGUALES
 /*   @GetMapping("/duplicado/{id}")   // como recibe variable...
    public ResponseEntity<?> NombreIdcartera(@PathVariable("id") int id){

        Producto productoE = productoService.getOne(id).get();  // ojo con el .get

        if(productoService.existsByNombre(productoE.getNombre()) && productoService.existsByIdcartera(productoE.getIdcartera()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);



        List<Producto> NombreIdcartera = productoService.nombreidcartera(id);

        return new ResponseEntity(NombreIdcartera, HttpStatus.OK);
    }*/

    @GetMapping("/detail/{id}")
    public ResponseEntity<Producto> getById(@PathVariable("id") int id){
        if(!productoService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Producto producto = productoService.getOne(id).get();
        return new ResponseEntity(producto, HttpStatus.OK);
    }

    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Producto> getByNombre(@PathVariable("nombre") String nombre){
        if(!productoService.existsByNombre(nombre))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Producto producto = productoService.getByNombre(nombre).get();
        return new ResponseEntity(producto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductoDto productoDto){
        if(StringUtils.isBlank(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(productoDto.getTer()==null || productoDto.getTer()<0 )
            return new ResponseEntity(new Mensaje("el Ter debe ser mayor que 0"), HttpStatus.BAD_REQUEST);
   /*     if(productoService.existsByNombre(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);  */

       //QUITO EL COMENTARIO, PARA QUE NO SE PUEDA DUPLICAR. pero creo que no vale porque busca por nombre?
        Producto producto = new Producto(productoDto.getNombre(), productoDto.getIsin(), productoDto.getTicker(),
                productoDto.getTer(),
                productoDto.getIdcartera()); // AÑADIMOS IDCARTERA --------------------------------

        productoService.save(producto);
        return new ResponseEntity(new Mensaje("producto creado"), HttpStatus.OK);
    }
 //EL BUENO



    @PreAuthorize("hasRole('USER')")
    @PostMapping("/addacartera")
    public ResponseEntity<?> addacartera(@RequestBody ProductoDto productoDto){

        if(StringUtils.isBlank(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);

        if(productoDto.getTer()==null || productoDto.getTer()<0 )
            return new ResponseEntity(new Mensaje("el Ter debe ser mayor que 0"), HttpStatus.BAD_REQUEST);
        //SI EXISTE POR NOMBRE Y TAMBIÉN EXISTE POR IDCARTERA

   //     int idbuscado = productoService.getByNombre(productoDto.getNombre()).get().getId();
        // Ya tengo el ID del producto de ese nombre, llamado idbuscado

        //PASOS
        //1. BUSCAR EL PRODUCTO CON ESTE ID
        //2. HACER EL IF: SI SUS CAMPOS NOMBRE E IDCARTERA COINCIDEN...

   //    Producto buscado = productoService.getOne(idbuscado).get(); // .get()
        //YA TIENES EL PRODUCTO DE LA BBDDD CON ESTE ID


        //Si este producto tiene el mismo nombre y ADEMÁS, tiene el mismo idcartera, no se puede crear
   //      if(buscado.getNombre().equals(productoDto.getNombre())    &&    buscado.getIdcartera().equals(productoDto
 //   .getIdcartera()))

   //         return new ResponseEntity(new Mensaje("este producto ya se ha añadido"), HttpStatus.BAD_REQUEST);

        List<Producto> list = productoService.list();
        for (Producto buscaproducto: list ) {
            if ( buscaproducto.getNombre().equals(productoDto.getNombre())
                    && buscaproducto.getIdcartera().equals(productoDto
                    .getIdcartera())) {
                return new ResponseEntity(new Mensaje("Este producto ya se ha añadido"), HttpStatus.BAD_REQUEST);
            }
        }
        
        Producto producto = new Producto(productoDto.getNombre(), productoDto.getIsin(),
                productoDto.getTicker(), productoDto.getTer(),
                productoDto.getIdcartera()); // AÑADIMOS IDCARTERA --------------------------------

        productoService.save(producto);
        return new ResponseEntity(new Mensaje("producto creado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody ProductoDto productoDto){
        if(!productoService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        if(productoService.existsByNombre(productoDto.getNombre()) && productoService.getByNombre(productoDto.getNombre()).get().getId() != id)
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(productoDto.getTer()==null || productoDto.getTer()<0 )
            return new ResponseEntity(new Mensaje("el Ter debe ser mayor que 0"), HttpStatus.BAD_REQUEST);

        Producto producto = productoService.getOne(id).get();
        producto.setNombre(productoDto.getNombre());
        producto.setIsin(productoDto.getIsin());
        producto.setTicker(productoDto.getTicker());
        producto.setTer(productoDto.getTer());
        productoService.save(producto);
        return new ResponseEntity(new Mensaje("producto actualizado"), HttpStatus.OK);
    }

 //   @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!productoService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        productoService.delete(id);
        return new ResponseEntity(new Mensaje("producto eliminado"), HttpStatus.OK);
    }


}
