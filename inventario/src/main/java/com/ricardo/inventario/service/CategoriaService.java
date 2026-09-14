package com.ricardo.inventario.service;
import com.ricardo.inventario.dto.CategoriaResponseDTO;
import com.ricardo.inventario.dto.CategoriaRequestDTO;
import com.ricardo.inventario.model.Categoria;
import com.ricardo.inventario.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaResponseDTO> obtenerCategorias(){
        return categoriaRepository.findAll()
                .stream()
                .map(categoria -> new CategoriaResponseDTO(
                        categoria.getId(),
                        categoria.getName()
                ))
                .toList();
    }
    public CategoriaResponseDTO obtenerCategoriaPorId(Long id){
        Categoria categoria = categoriaRepository
                .findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Categoria no encontrada"));
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getName()
        );
    }
    public CategoriaResponseDTO crearCategoria(CategoriaRequestDTO dto){
        Categoria categoria = new Categoria();

        categoria.setName(dto.getName());
        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return new CategoriaResponseDTO(
                categoriaGuardada.getId(),
                categoriaGuardada.getName()
        );
    }
    public CategoriaResponseDTO actualizarCategoria(Long id, CategoriaRequestDTO dto){
        Categoria categoria = categoriaRepository
                .findById(id)
                .orElseThrow(()->
                        new RuntimeException("Categoria No Encontrada"));
        categoria.setName(dto.getName());

        Categoria actualizada = categoriaRepository.save(categoria);
        return new CategoriaResponseDTO(
                actualizada.getId(),
                actualizada.getName()
        );
    }

    public void eliminarCategoria(Long id){
       categoriaRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Categoría no encontrada"));
        categoriaRepository.deleteById(id);
    }
}
