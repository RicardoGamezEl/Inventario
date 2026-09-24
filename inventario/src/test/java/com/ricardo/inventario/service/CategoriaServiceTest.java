package com.ricardo.inventario.service;

import com.ricardo.inventario.dto.CategoriaRequestDTO;
import com.ricardo.inventario.dto.CategoriaResponseDTO;
import com.ricardo.inventario.model.Categoria;
import com.ricardo.inventario.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    void obtenerCategorias_deberiaDevolverCategorias() {

        Categoria categoria1 = new Categoria();
        categoria1.setId(1L);
        categoria1.setName("Audio");

        Categoria categoria2 = new Categoria();
        categoria2.setId(2L);
        categoria2.setName("Computación");

        when(categoriaRepository.findAll())
                .thenReturn(List.of(categoria1, categoria2));

        List<CategoriaResponseDTO> resultado =
                categoriaService.obtenerCategorias();

        assertEquals(2, resultado.size());

        assertEquals(1L, resultado.get(0).getId());
        assertEquals("Audio", resultado.get(0).getName());

        assertEquals(2L, resultado.get(1).getId());
        assertEquals("Computación", resultado.get(1).getName());
    }

    @Test 
    void obtenerCategoriaPorId_deberiaLanzarExcepcionSiNoExiste(){
        when(categoriaRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> categoriaService.obtenerCategoriaPorId(99L)
        );

        assertEquals("Categoria no encontrada", exception.getMessage());
    }

    @Test
    void obtenerCategoriaPorId_deberiaDevolverCategoriaSiExiste(){
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setName("Electronica");

        when(categoriaRepository.findById(1L))
            .thenReturn(Optional.of(categoria));

        CategoriaResponseDTO resultado = categoriaService.obtenerCategoriaPorId(1L);

        assertEquals(1L, resultado.getId());
        assertEquals("Electronica", resultado.getName());
    }

    @Test
    void crearCategoria_deberiaGuardarYDevolverCategoria(){
        CategoriaRequestDTO dto = new CategoriaRequestDTO();
        dto.setName("Gaming");

        Categoria categoriaGuardada = new Categoria();
        categoriaGuardada.setId(1L);
        categoriaGuardada.setName("Gaming");

        when(categoriaRepository.save(any(Categoria.class)))
            .thenReturn(categoriaGuardada);
        
        CategoriaResponseDTO resultado = categoriaService.crearCategoria(dto);
        assertEquals(1L, resultado.getId());
        assertEquals("Gaming", resultado.getName());

        verify(categoriaRepository).save(any(Categoria.class));
    }

    @Test
    void actualizarCategoria_deberiaGuardarCambiosEnCategoria(){
        CategoriaRequestDTO dto = new CategoriaRequestDTO();
        dto.setName("Audio Gamer");

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setName("Audio");

        when(categoriaRepository.findById(1L))
            .thenReturn(Optional.of(categoria));
        
        when(categoriaRepository.save(categoria))
            .thenReturn(categoria);
        
        CategoriaResponseDTO resultado = categoriaService.actualizarCategoria(1L, dto);

        assertEquals(1L, resultado.getId());
        assertEquals("Audio Gamer", resultado.getName());

        verify(categoriaRepository)
            .save(categoria);
    }

    @Test 
    void eliminarCategoria_deberiaEliminarCategoriaSiExiste(){
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setName("Audio");

        when(categoriaRepository.findById(1L))
            .thenReturn(Optional.of(categoria));

        categoriaService.eliminarCategoria(1L);

        verify(categoriaRepository)
            .deleteById(1L);
    }

    @Test
    void eliminarCategoria_deberiaLanzarExcepcionSiNoExiste(){
        when(categoriaRepository.findById(99L))
            .thenReturn(Optional.empty());
        
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> categoriaService.eliminarCategoria(99L)
        );

        assertEquals(
            "Categoría no encontrada",
            exception.getMessage());
        
        verify(categoriaRepository, never())
            .deleteById(99L);
    }
}