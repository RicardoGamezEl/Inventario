package com.ricardo.inventario.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import java.util.Optional;
@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El Nombre Es Obligatorio")
    private String name;

    @NotNull(message = "EL Precio Es Obligatorio")
    @Positive(message = "El Precion Debe Ser Mayor A 0")
    private  Double price;

    private int stock;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Producto(){
    }
    public Producto(Long id, String name, Double price, int stock){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
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
    public int getStock(){
        return stock;
    }
    public Categoria getCategoria(){
        return categoria;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public void setStock(int stock){
        this.stock = stock;
    }
    public void setCategoria(Categoria categoria){
        this.categoria = categoria;
    }
}
