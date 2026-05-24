package com.ricardo.inventario.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductoRequestDTO {
    @NotBlank(message = "El Nombre Es Obligatorio")
    private String name;

    @NotNull(message = "El Precio Es Obligatorio")
    @Positive(message = "El Precio Debe Ser Mayor a 0")
    private Double price;

    @NotNull(message = "La Categoría Es Obligatoria")
    private Long categoriaId;

    public String getName(){
        return name;
    }
    public Double getPrice(){
        return price;
    }
    public Long getCategoriaId(){
        return categoriaId;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPrice(Double price){
        this.price = price;
    }
    public void setCategoriaId(Long categoriaId){
        this.categoriaId = categoriaId;
    }
}
