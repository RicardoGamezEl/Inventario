package com.ricardo.inventario.service;
import com.ricardo.inventario.model.Producto;
import com.ricardo.inventario.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    public ProductoService(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerProductos(){
        return productoRepository.findAll();
    }

    public Producto agregarProducto(Producto producto){
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    public Producto obtenerProductoPorId(Long id){
        Optional<Producto> producto = productoRepository.findById(id);
        return  producto.orElse(null);
    }

    public Producto actualizarProducto(Long id,Producto productoActualizado){
        Optional<Producto> productoExistente = productoRepository.findById(id);
        if(productoExistente.isPresent()){
            Producto producto = productoExistente.get();
            producto.setName(productoActualizado.getName());
            producto.setPrice(productoActualizado.getPrice());
            return  productoRepository.save(producto);
        }
        return null;
    }
}
