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
                        producto.getStock(),
                        producto.getCategoria() != null ?
                                producto.getCategoria().getName():
                                "Sin categoria",
                        producto.getCategoria() != null ?
                                producto.getCategoria().getId():
                                null
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
        producto.setStock(dto.getStock());
        producto.setCategoria(categoria);

        Producto productoGuardado =
                productoRepository.save(producto);
        return new ProductoResponseDTO(
                productoGuardado.getId(),
                productoGuardado.getName(),
                productoGuardado.getPrice(),
                productoGuardado.getStock(),
                productoGuardado.getCategoria() != null ? productoGuardado.getCategoria().getName() : "Sin categoria",
                productoGuardado.getCategoria() != null ? productoGuardado.getCategoria().getId() : null
        );
    }
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    public Producto obtenerProductoPorId(Long id){
        Optional<Producto> producto = productoRepository.findById(id);
        return  producto.orElse(null);
    }

    public ProductoResponseDTO actualizarProducto(Long id, ProductoRequestDTO dto){
        Optional<Producto> productoExistente = productoRepository.findById(id);
        if(productoExistente.isPresent()){
            Producto producto = productoExistente.get();
            producto.setName(dto.getName());
            producto.setPrice(dto.getPrice());
            producto.setStock(dto.getStock());

            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoria No Encontrada"));
            producto.setCategoria(categoria);

            Producto productoGuardado = productoRepository.save(producto);
            return new ProductoResponseDTO(
                    productoGuardado.getId(),
                    productoGuardado.getName(),
                    productoGuardado.getPrice(),
                    productoGuardado.getStock(),
                    productoGuardado.getCategoria() != null ? productoGuardado.getCategoria().getName() : "Sin categoria",
                    productoGuardado.getCategoria() != null ? productoGuardado.getCategoria().getId() : null
            );
        }
        return null;
    }

}
