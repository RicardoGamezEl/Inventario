package com.ricardo.inventario.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Min;

public class ProductoRequestDTO {
    @NotBlank(message = "El Nombre Es Obligatorio")
    private String name;

    @NotNull(message = "El Precio Es Obligatorio")
    @Positive(message = "El Precio Debe Ser Mayor a 0")
    private Double price;

    @NotNull(message = "La Categoría Es Obligatoria")
    private Long categoriaId;

    @NotNull(message = "El Stock Es Obligatorio")
    @Min(value = 0, message = "El Stock No Puede Ser Negativo")
    private Integer stock;

    public String getName(){
        return name;
    }
    public Double getPrice(){
        return price;
    }
    public Long getCategoriaId(){
        return categoriaId;
    }
    public Integer getStock() {
        return stock;
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
    public void setStock(Integer stock) {
        this.stock = stock;
    }
}

