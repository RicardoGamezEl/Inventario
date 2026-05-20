package com.ricardo.inventario.service;
import com.ricardo.inventario.dto.CategoriaReponseDTO;
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

    public List<CategoriaReponseDTO> obtenerCategorias(){
        return categoriaRepository.findAll()
                .stream()
                .map(categoria -> new CategoriaReponseDTO(
                        categoria.getId(),
                        categoria.getName()
                ))
                .toList();
    }
    public CategoriaReponseDTO crearCategoria(CategoriaRequestDTO dto){
        Categoria categoria = new Categoria();

        categoria.setName(dto.getName());
        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return new CategoriaReponseDTO(
                categoriaGuardada.getId(),
                categoriaGuardada.getName()
        );
    }

    public void eliminarCategoria(Long id){
        categoriaRepository.deleteById(id);
    }
}
