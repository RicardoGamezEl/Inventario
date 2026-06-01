package com.ricardo.inventario.controller;
import com.ricardo.inventario.dto.ProductoRequestDTO;
import com.ricardo.inventario.dto.ProductoResponseDTO;
import com.ricardo.inventario.model.Producto;
import com.ricardo.inventario.repository.ProductoRepository;
import jakarta.persistence.Table;
import org.springframework.web.bind.annotation.*;
import com.ricardo.inventario.service.ProductoService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/productos")
@Tag(name = "Productos", description = "API de gestión de productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService){
        this.productoService = productoService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los productos")
    public List<ProductoResponseDTO> obtenerProductos(){
        return productoService.obtenerProductos();
    }
    @GetMapping("/{id}")
    @Operation(summary = "Obtener productos por ID")
    public ProductoResponseDTO obtenerProductoPorId(@PathVariable Long id){

        Producto producto = productoService.obtenerProductoPorId(id);

        return new ProductoResponseDTO(
                producto.getId(),
                producto.getName(),
                producto.getPrice(),
                producto.getStock(),
                producto.getCategoria() != null ? producto.getCategoria().getName() : "Sin categoria",
                producto.getCategoria() != null ? producto.getCategoria().getId() : null
        );
    }

    @PostMapping
    @Operation(summary = "Añadir nuevos productos")
    public ProductoResponseDTO agregarProducto(@Valid @RequestBody ProductoRequestDTO dto){
        return productoService.agregarProducto(dto);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Actualizacion o Modificacion de productos")
    public ProductoResponseDTO actualizarProducto(@PathVariable Long id, @Valid @RequestBody ProductoRequestDTO dto){
        return productoService.actualizarProducto(id, dto);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar productos")
    public String eliminarProducto(@Valid @PathVariable Long id){
        productoService.eliminarProducto(id);
        return "Producto Eliminado";
    }
}
