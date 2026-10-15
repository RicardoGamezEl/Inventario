package com.ricardo.inventario.controller;

import com.ricardo.inventario.dto.CategoriaRequestDTO;
import com.ricardo.inventario.dto.CategoriaResponseDTO;
import com.ricardo.inventario.service.CategoriaService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CategoriaController.class)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriaService categoriaService;

    @Test
    void obtenerCategorias_deberiaDevolverListaDeCategorias() throws Exception {

        CategoriaResponseDTO categoria = new CategoriaResponseDTO(
                1L,
                "Audio"
        );

        when(categoriaService.obtenerCategorias())
                .thenReturn(List.of(categoria));

        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Audio"));
    }

    @Test
    void obtenerCategoriaPorId_deberiaDevolverCategoria() throws Exception {

        CategoriaResponseDTO categoria = new CategoriaResponseDTO(
                1L,
                "Audio"
        );

        when(categoriaService.obtenerCategoriaPorId(1L))
                .thenReturn(categoria);

        mockMvc.perform(get("/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Audio"));
    }

    @Test
    void crearCategoria_deberiaDevolverCategoriaCreada() throws Exception {

        CategoriaResponseDTO categoria = new CategoriaResponseDTO(
                1L,
                "Audio"
        );

        when(categoriaService.crearCategoria(
                any(CategoriaRequestDTO.class)
        )).thenReturn(categoria);

        mockMvc.perform(post("/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Audio"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Audio"));
    }

    @Test
    void crearCategoria_conNombreVacio_deberiaDevolver400() throws Exception {

        mockMvc.perform(post("/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": ""
                        }
                        """))
                .andExpect(status().isBadRequest());

        verify(categoriaService, never())
                .crearCategoria(any(CategoriaRequestDTO.class));
    }

    @Test
    void actualizarCategoria_deberiaDevolverCategoriaActualizada() throws Exception {

        CategoriaResponseDTO categoria = new CategoriaResponseDTO(
                1L,
                "Electronica"
        );

        when(categoriaService.actualizarCategoria(
                eq(1L),
                any(CategoriaRequestDTO.class)
        )).thenReturn(categoria);

        mockMvc.perform(put("/categorias/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Electronica"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Electronica"));
    }

    @Test
    void actualizarCategoria_conNombreVacio_deberiaDevolver400() throws Exception {

        mockMvc.perform(put("/categorias/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": ""
                        }
                        """))
                .andExpect(status().isBadRequest());

        verify(categoriaService, never())
                .actualizarCategoria(
                        eq(1L),
                        any(CategoriaRequestDTO.class)
                );
    }

    @Test
    void eliminarCategoria_deberiaEliminarCategoria() throws Exception {

        mockMvc.perform(delete("/categorias/1"))
                .andExpect(status().isOk());

        verify(categoriaService).eliminarCategoria(1L);
    }
}