package com.ricardo.inventario.dto;

import com.ricardo.inventario.model.Producto;

public class ProductoResponseDTO {
    private Long id;
    private String name;
    private Double price;
    private Integer stock;
    private String categoria;
    private Long categoriaId;

    public ProductoResponseDTO(Long id, String name, Double price, Integer stock, String categoria, Long categoriaId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.categoria = categoria;
        this.categoriaId = categoriaId;
    }
    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public Double getPrice(){
        return price;
    }
    public Integer getStock(){return stock;}
    public String getCategoria(){return categoria;}
    public Long getCategoriaId(){return categoriaId;}
}

