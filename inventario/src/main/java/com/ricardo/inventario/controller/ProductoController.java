package com.ricardo.inventario.controller;
import com.ricardo.inventario.model.Producto;
import org.springframework.web.bind.annotation.*;
import com.ricardo.inventario.service.ProductoService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService){
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> obtenerProductos(){
        return productoService.obtenerProductos();
    }
    @GetMapping("/{id}")
    public Producto obtenerProductoPorId(@PathVariable Long id){
        return productoService.obtenerProductoPorId(id);
    }

    @PostMapping
    public Producto agregarProducto(@Valid @RequestBody Producto producto){
        return productoService.agregarProducto(producto);
    }
    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable Long id,@RequestBody Producto producto){
        return productoService.actualizarProducto(id,producto);
    }

    @DeleteMapping("/{id}")
    public String eliminarProducto(@Valid @PathVariable Long id){
        productoService.eliminarProducto(id);
        return "Producto Eliminado";
    }
}
