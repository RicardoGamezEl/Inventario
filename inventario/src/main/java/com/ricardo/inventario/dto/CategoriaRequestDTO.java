package com.ricardo.inventario.dto;
import jakarta.validation.constraints.NotBlank;

public class CategoriaRequestDTO {
    @NotBlank(message = "El Nombre Es Obligatorio")
    private String name;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}
