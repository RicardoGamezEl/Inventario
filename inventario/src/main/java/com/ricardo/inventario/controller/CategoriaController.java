package com.ricardo.inventario.controller;

import com.ricardo.inventario.dto.CategoriaReponseDTO;
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
    public List<CategoriaReponseDTO> obtenerCategorias(){
        return categoriaService.obtenerCategorias();
    }

    @PostMapping
    @Operation(summary = "Crear Categorias")
    public CategoriaReponseDTO crearCategoria(@Valid @RequestBody CategoriaRequestDTO dto){
        return categoriaService.crearCategoria(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminacion De Una Categoria Por Id")
    public String eliminarCategoria(Long id){
        categoriaService.eliminarCategoria(id);
        return "La Categoria Se Elimino Correctamente";
    }
}
