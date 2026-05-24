package com.ricardo.inventario.service;

import com.ricardo.inventario.model.Categoria;
import com.ricardo.inventario.model.Producto;
import com.ricardo.inventario.repository.ProductoRepository;
import com.ricardo.inventario.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import com.ricardo.inventario.dto.ProductoRequestDTO;
import com.ricardo.inventario.dto.ProductoResponseDTO;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository){
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<ProductoResponseDTO> obtenerProductos(){
        return productoRepository.findAll()
                .stream()
                .map(producto -> new ProductoResponseDTO(
                        producto.getId(),
                        producto.getName(),
                        producto.getPrice(),
                        producto.getCategoria() != null ?
                                producto.getCategoria().getName():
                                "Sin categoria"
                ))
                .toList();
    }

    public ProductoResponseDTO agregarProducto(ProductoRequestDTO dto){
        Categoria categoria = categoriaRepository
                .findById(dto.getCategoriaId())
                        .orElseThrow(()->
                            new RuntimeException("Categoria No Encontrada"));

        Producto producto = new Producto();

        producto.setName(dto.getName());
        producto.setPrice(dto.getPrice());
        producto.setCategoria(categoria);

        Producto productoGuardado =
                productoRepository.save(producto);
        return new ProductoResponseDTO(
                productoGuardado.getId(),
                productoGuardado.getName(),
                productoGuardado.getPrice(),
                productoGuardado.getCategoria().getName()
        );
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
            producto.setCategoria(productoActualizado.getCategoria());
            return  productoRepository.save(producto);
        }
        return null;
    }
}
