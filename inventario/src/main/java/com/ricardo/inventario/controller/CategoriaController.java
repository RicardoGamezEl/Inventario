package com.ricardo.inventario.controller;
import com.ricardo.inventario.dto.CategoriaResponseDTO;
import com.ricardo.inventario.dto.CategoriaRequestDTO;
import com.ricardo.inventario.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@Tag(name = "categorias", description = "API De Categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;
    public CategoriaController(CategoriaService categoriaService){
        this.categoriaService = categoriaService;
    }

    @GetMapping
    @Operation(summary = "Obtener Categorias")
    public List<CategoriaResponseDTO> obtenerCategorias(){
        return categoriaService.obtenerCategorias();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener Categoria Por Id")
    public CategoriaResponseDTO obtenerCategoriaPorId(@PathVariable Long id){
        return categoriaService.obtenerCategoriaPorId(id);
    }
    @PostMapping
    @Operation(summary = "Crear Categorias")
    public CategoriaResponseDTO crearCategoria(@Valid @RequestBody CategoriaRequestDTO dto){
        return categoriaService.crearCategoria(dto);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Categoria")
    public CategoriaResponseDTO actualizarCategoria(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO dto){
        return categoriaService.actualizarCategoria(id,dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminacion De Una Categoria Por Id")
    public void eliminarCategoria(@PathVariable Long id){
        categoriaService.eliminarCategoria(id);
    }

}
