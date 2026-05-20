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
    public Producto obtenerProductoPorId(@PathVariable Long id){
        return productoService.obtenerProductoPorId(id);
    }

    @PostMapping
    @Operation(summary = "Añadir nuevos productos")
    public ProductoResponseDTO agregarProducto(@Valid @RequestBody ProductoRequestDTO dto){
        return productoService.agregarProducto(dto);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Actualizacion o Modificacion de productos")
    public Producto actualizarProducto(@PathVariable Long id,@RequestBody Producto producto){
        return productoService.actualizarProducto(id,producto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar productos")
    public String eliminarProducto(@Valid @PathVariable Long id){
        productoService.eliminarProducto(id);
        return "Producto Eliminado";
    }
}
