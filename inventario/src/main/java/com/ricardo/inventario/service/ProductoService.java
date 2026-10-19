package com.ricardo.inventario.service;

import com.ricardo.inventario.model.Categoria;
import com.ricardo.inventario.model.Producto;
import com.ricardo.inventario.repository.ProductoRepository;
import com.ricardo.inventario.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import com.ricardo.inventario.exception.RecursoNoEncontradoException;

import com.ricardo.inventario.dto.ProductoRequestDTO;
import com.ricardo.inventario.dto.ProductoResponseDTO;

import java.util.List;

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
        productoRepository
            .findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Producto No Encontrado"));
        productoRepository.deleteById(id);
    }

    public Producto obtenerProductoPorId(Long id){
        return productoRepository.findById(id)
            .orElseThrow(() ->
                new RecursoNoEncontradoException("Producto No Encontrado"));
    }

   public ProductoResponseDTO actualizarProducto(Long id, ProductoRequestDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Producto No Encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Categoria No Encontrada"));

        producto.setName(dto.getName());
        producto.setPrice(dto.getPrice());
        producto.setStock(dto.getStock());
        producto.setCategoria(categoria);

        Producto productoGuardado = productoRepository.save(producto);

        return new ProductoResponseDTO(
                productoGuardado.getId(),
                productoGuardado.getName(),
                productoGuardado.getPrice(),
                productoGuardado.getStock(),
                productoGuardado.getCategoria() != null
                        ? productoGuardado.getCategoria().getName()
                        : "Sin categoria",
                productoGuardado.getCategoria() != null
                        ? productoGuardado.getCategoria().getId()
                        : null
        );
    }
}
