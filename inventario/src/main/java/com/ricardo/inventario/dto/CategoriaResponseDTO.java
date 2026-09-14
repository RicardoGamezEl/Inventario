package com.ricardo.inventario.dto;
public class CategoriaResponseDTO {
    private Long id;
    private String name;

    public CategoriaResponseDTO(Long id, String name){
        this.id = id;
        this.name = name;
    }
    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
}
