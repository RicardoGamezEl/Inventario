package com.ricardo.inventario.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ricardo.inventario.dto.ProductoRequestDTO;
import com.ricardo.inventario.dto.ProductoResponseDTO;
import com.ricardo.inventario.model.Categoria;
import com.ricardo.inventario.model.Producto;
import com.ricardo.inventario.repository.CategoriaRepository;
import com.ricardo.inventario.repository.ProductoRepository;


@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {
    @Mock
    ProductoRepository productoRepository;

    @Mock
    CategoriaRepository categoriaRepository;

    ProductoService productoService;

    @BeforeEach
    void setUp(){
        productoService =
            new ProductoService(
                productoRepository,
                categoriaRepository
            );
    }

    @Test 
    void obtenerProductos_deberiaDevolverProductosComoDTO(){
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setName("Audio");

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setName("Audifonos");
        producto.setPrice(1554.00);
        producto.setStock(13);
        producto.setCategoria(categoria);

        when(productoRepository.findAll())
            .thenReturn(List.of(producto));

        List<ProductoResponseDTO> resultado = productoService.obtenerProductos();

        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        assertEquals("Audifonos", resultado.get(0).getName());
        assertEquals(1554.00, resultado.get(0).getPrice());
        assertEquals(13, resultado.get(0).getStock());
        assertEquals("Audio", resultado.get(0).getCategoria());
        assertEquals(1L, resultado.get(0).getCategoriaId());
    }

    @Test
    void agregarProducto_deberiaCrearYDevolcerProductoComoDTO(){
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setName("Audio");

        ProductoRequestDTO dto = new ProductoRequestDTO(
            "Audifonos",
            1554.00,
            13,
            1L
        );
        when(categoriaRepository.findById(1L))
            .thenReturn(Optional.of(categoria));

        Producto productoGuardado = new Producto();
        productoGuardado.setId(1L);
        productoGuardado.setName("Audifonos");
        productoGuardado.setPrice(1554.00);
        productoGuardado.setStock(13);
        productoGuardado.setCategoria(categoria);

        when(productoRepository.save(any(Producto.class)))
            .thenReturn(productoGuardado);
        
        ProductoResponseDTO resultado =
            productoService.agregarProducto(dto);
        assertEquals(1L, resultado.getId());
        assertEquals("Audifonos", resultado.getName());
        assertEquals(1554.00, resultado.getPrice());
        assertEquals(13, resultado.getStock());
        assertEquals("Audio", resultado.getCategoria());
        assertEquals(1L, resultado.getCategoriaId());

    }

    @Test
    void agregarProducto_deberiaLanzarExcepcionSiCategoriaNoExiste(){
        ProductoRequestDTO dto = new ProductoRequestDTO(
            "Audifonos",
            1554.00,
            13,
            99L
        );

        when(categoriaRepository.findById(99L))
            .thenReturn(Optional.empty());
        
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> productoService.agregarProducto(dto)
        );

        assertEquals("Categoria No Encontrada", exception.getMessage());
    }

    @Test
    void eliminarProducto_deberiaEliminarProductoSiExiste(){
        Producto producto = new Producto();
        producto.setId(1L);

        when(productoRepository.findById(1L))
            .thenReturn(Optional.of(producto));
        
        productoService.eliminarProducto(1L);
        
        verify(productoRepository).deleteById(1L);
    }

    @Test
    void eliminarProducto_deberiaLanzarExcepcionSiProductoNoExiste(){
        when(productoRepository.findById(99L))
            .thenReturn(Optional.empty());
        
        RuntimeException exception = assertThrows(RuntimeException.class,() -> productoService.eliminarProducto(99L));

        assertEquals("Producto No Encontrado", exception.getMessage());

        verify(productoRepository, never())
            .deleteById(99L);
    }

    @Test
    void obtenerProductoPorId_deberiaDevolverProducto(){
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setName("Audifonos");
        producto.setPrice(1554.00);
        producto.setStock(13);

        when(productoRepository.findById(1L))
            .thenReturn(Optional.of(producto));
        
        Producto resultado = productoService.obtenerProductoPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Audifonos", resultado.getName());
        assertEquals(1554.00, resultado.getPrice());
        assertEquals(13, resultado.getStock());
    }
    
    @Test
    void obtenerProductoPorId_deberiaDevolverNullSiNoExiste(){
        when(productoRepository.findById(99L))
            .thenReturn(Optional.empty());

        Producto resultado = productoService.obtenerProductoPorId(99L);

        assertNull(resultado);
    }

    @Test 
    void actualizarProducto_deberiaActualizarProducto(){
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setName("Audio");

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setName("Audifonos");
        producto.setPrice(1554.00);
        producto.setStock(13);
        producto.setCategoria(categoria);

        ProductoRequestDTO dto = new ProductoRequestDTO(
            "Audifonos Gamer", 2500.00, 5, 1L);
        
        when(productoRepository.findById(1L))
            .thenReturn(Optional.of(producto));

        when(categoriaRepository.findById(1L))
            .thenReturn(Optional.of(categoria));
        
        when(productoRepository.save(producto))
            .thenReturn(producto);
            
        ProductoResponseDTO resultado = productoService.actualizarProducto(1L, dto);
    
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Audifonos Gamer", resultado.getName());
        assertEquals(2500.00, resultado.getPrice());
        assertEquals(5, resultado.getStock());
        assertEquals("Audio", resultado.getCategoria());
        assertEquals(1L, resultado.getCategoriaId());
    }

    @Test
    void actualizarProducto_productoNoExiste_deberiaDevolverNull(){
        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setName("Audifonos Gamer");
        dto.setPrice(1500.00);
        dto.setStock(20);
        dto.setCategoriaId(1L);
        
        when(productoRepository.findById(99L))
                .thenReturn(Optional.empty());
        
        ProductoResponseDTO resultado =
                productoService.actualizarProducto(99L, dto);
        
        assertNull(resultado);
    }

    @Test
    void actualizarProducto_deberiaLanzarExcepcionSiCategoriaNoExiste(){
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setName("Audifonos");
        producto.setPrice(1000.00);
        producto.setStock(10);

        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setName("Audifonos Gamer");
        dto.setPrice(1500.00);
        dto.setStock(20);
        dto.setCategoriaId(99L);

        when(productoRepository.findById(1L))
                .thenReturn(Optional.of(producto));

        when(categoriaRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException excepcion = assertThrows(
                RuntimeException.class,
                () -> productoService.actualizarProducto(1L, dto)
        );

        assertEquals(
                "Categoria No Encontrada",
                excepcion.getMessage()
        );

        verify(productoRepository, never())
            .save(any(Producto.class));
        
    }
}