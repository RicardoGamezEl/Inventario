package com.ricardo.inventario.dto;

import com.ricardo.inventario.model.Producto;

public class ProductoResponseDTO {
    private Long id;
    private String name;
    private Double price;

    public ProductoResponseDTO(Long id,String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;

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
}
