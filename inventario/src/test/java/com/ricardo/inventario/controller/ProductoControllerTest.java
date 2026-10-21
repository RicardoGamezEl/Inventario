package com.ricardo.inventario.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import com.ricardo.inventario.dto.ProductoRequestDTO;
import com.ricardo.inventario.dto.ProductoResponseDTO;
import com.ricardo.inventario.exception.RecursoNoEncontradoException;
import com.ricardo.inventario.model.Producto;
import com.ricardo.inventario.service.ProductoService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService; 

   @Test
   void obtenerProductos_deberiaDevolverListaDeProductos() throws Exception{
        ProductoResponseDTO producto = new ProductoResponseDTO(
            1L,
            "Audifonos",
            1554.00,
            13,
            "Audio",
            1L
        );

        when(productoService.obtenerProductos())
            .thenReturn(List.of(producto));

        mockMvc.perform(get("/productos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Audifonos"))
            .andExpect(jsonPath("$[0].price").value(1554.00))
            .andExpect(jsonPath("$[0].stock").value(13))
            .andExpect(jsonPath("$[0].categoria").value("Audio"))
            .andExpect(jsonPath("$[0].categoriaId").value(1));
   }

   @Test
   void obtenerProductosPorId_deberiaDevolverProductoEspecifico() throws Exception{
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setName("Asus Tuf Gaming A16");
        producto.setPrice(24365.50);
        producto.setStock(25);

        when(productoService.obtenerProductoPorId(1L))
            .thenReturn((producto));

        mockMvc.perform(get("/productos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Asus Tuf Gaming A16"))
            .andExpect(jsonPath("$.price").value(24365.50))
            .andExpect(jsonPath("$.stock").value(25))
            .andExpect(jsonPath("$.categoria").value("Sin categoria"))
            .andExpect(jsonPath("$.categoriaId").doesNotExist());
   }

   @Test
   void agregarProdcucto_deveriaCrearYDevolverProducto() throws Exception{
        ProductoResponseDTO producto = new ProductoResponseDTO(
            1L,
            "Audifonos",
            1554.00,
            13,
            "Audio",
            1L
        );

        when(productoService.agregarProducto(any(ProductoRequestDTO.class)))
            .thenReturn(producto);


            mockMvc.perform(post("/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                        "name": "Audifonos",
                        "price": 1554.00,
                        "stock": 13,
                        "categoriaId": 1
                    }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Audifonos"))
            .andExpect(jsonPath("$.price").value(1554.00))
            .andExpect(jsonPath("$.stock").value(13))
            .andExpect(jsonPath("$.categoria").value("Audio"))
            .andExpect(jsonPath("$.categoriaId").value(1));
   }

   @Test
   void agregarProducto_conPrecioNegativo_deberiaDevolver400() throws Exception{
        mockMvc.perform(post("/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                        "name": "Audifonos",
                        "price": -100,
                        "stock": 13,
                        "categoriaId": 1
                    }
                    """))
            .andExpect(status().isBadRequest());
   }

   @Test
   void agregarProducto_conNombreVacio_deberiaDevolver400() throws Exception{
        mockMvc.perform(post("/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                        "name": "",
                        "price": 100,
                        "stock": 13,
                        "categoriaId":1,
                    }
                    """))
            .andExpect(status().isBadRequest());
   }

   @Test 
   void agregarProducto_conStockNegativo_deberiaDevolver400() throws Exception{
        mockMvc.perform(post("/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                        "name": "Audifonos",
                        "price": 100,
                        "stock": -8,
                        "categoriaId": 1
                    }
                    """))
            .andExpect(status().isBadRequest());
   }

   @Test
    void actualizarProducto_deberiaDevolverProductoActualizado() throws Exception {

        ProductoResponseDTO producto = new ProductoResponseDTO(
                1L,
                "Audifonos Gamer",
                1500.00,
                20,
                "Audio",
                1L
        );

        when(productoService.actualizarProducto(
                org.mockito.ArgumentMatchers.eq(1L),
                any(ProductoRequestDTO.class)
        )).thenReturn(producto);

        mockMvc.perform(put("/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Audifonos Gamer",
                            "price": 1500.00,
                            "stock": 20,
                            "categoriaId": 1
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Audifonos Gamer"))
                .andExpect(jsonPath("$.price").value(1500.00))
                .andExpect(jsonPath("$.stock").value(20))
                .andExpect(jsonPath("$.categoria").value("Audio"))
                .andExpect(jsonPath("$.categoriaId").value(1));
    }

    @Test
    void actualizarProducto_conNombreVacio_deberiaDevolver400() throws Exception {

    mockMvc.perform(put("/productos/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                        "name": "",
                        "price": 1500,
                        "stock": 20,
                        "categoriaId": 1
                    }
                    """))
            .andExpect(status().isBadRequest());
    }

    @Test
    void actualizarProducto_conStockNegativo_deberiaDevolver400() throws Exception {

    mockMvc.perform(put("/productos/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                        "name": "Audifonos Gamer",
                        "price": 1500,
                        "stock": -5,
                        "categoriaId": 1
                    }
                    """))
            .andExpect(status().isBadRequest());
    }

    @Test
    void eliminarProducto_deberiaDevolverMensajeDeConfirmacion() throws Exception {

    mockMvc.perform(delete("/productos/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Producto Eliminado"));

    verify(productoService).eliminarProducto(1L);
    }
    
    @Test 
    void obtenerProductoPorId_deberiaDevolverExcepcionSiNoExiste() throws Exception {
        when(productoService.obtenerProductoPorId(99L))
                .thenThrow(new RecursoNoEncontradoException("Producto No Encontrado"));

        mockMvc.perform(get("/productos/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje").value("Producto No Encontrado"));
    }
}
