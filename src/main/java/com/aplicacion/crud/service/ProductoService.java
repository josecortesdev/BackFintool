package com.tutorial.crud.service;

import com.tutorial.crud.entity.Producto;
import com.tutorial.crud.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    public List<Producto> list(){
        return productoRepository.findAll();
    }

    public List<Producto> cartera(String idcartera) {

        List<Producto> lista;
        lista = productoRepository.findAll();

        List<Producto> listaSecundaria = new ArrayList<Producto>();

        for (Producto producto : lista) {
            //float miInt = 15;

            if (Objects.equals(producto.getIdcartera(), idcartera)) {
                listaSecundaria.add(producto);
            }
        }
    return listaSecundaria;
}

        public List<Producto> nombreidcartera(int id){

            List<Producto> NombreCartera; // Lista de productos
            NombreCartera = productoRepository.findAll(); // Aquí tengo todos

            Producto productoelegido = productoRepository.getOne(id);  // Aquí tengo uno con id

            List<Producto> nuevo = new ArrayList<Producto>();

            //Si este producto del id, tiene nombre e idcartera iguales a los del array nombrecartera, añadimos

            for(Producto producto : NombreCartera) {

                //Si uno de estos tiene idcartera de idcartera...
                if(productoelegido.getIdcartera().equals(producto.getIdcartera())){ // Si el producto elegido, tiene idcartera igual
                    // al del for, entra al siguiente if
                    if(productoelegido.getNombre().equals(producto.getNombre())){ // Si el elegido, tiene nombre igual al
                        // del for
                        nuevo.add(producto);  // Añade este del for, al nuevo array
                    }
                }
            }
            return nuevo;
        }



    public Optional<Producto> getOne(int id){
        return productoRepository.findById(id);
    }

    public Optional<Producto> getByNombre(String nombre){
        return productoRepository.findByNombre(nombre);
    }

    public void  save(Producto producto){
        productoRepository.save(producto);
    }

    public void delete(int id){
        productoRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return productoRepository.existsById(id);
    }

    public boolean existsByNombre(String nombre){
        return productoRepository.existsByNombre(nombre);
    }


    public boolean existsByIdcartera(String idcartera){
        return productoRepository.existsByIdcartera(idcartera);
    }
}
