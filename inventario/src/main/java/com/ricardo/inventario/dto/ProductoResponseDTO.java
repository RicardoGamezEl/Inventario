package com.ricardo.inventario.dto;

import com.ricardo.inventario.model.Producto;

public class ProductoResponseDTO {
    private Long id;
    private String name;
    private Double price;
    private String categoria;

    public ProductoResponseDTO(Long id,String name, Double price, String categoria) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoria = categoria;

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
    public String getCategoria(){return categoria;}
}
