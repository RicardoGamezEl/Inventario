package com.ricardo.inventario.model;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos;

    public Categoria(){
    }
    public Categoria(Long id,String name){
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public String getName(){
        return name;
    }
    public List<Producto> getProductos(){
        return productos;
    }
    public void setId(Long id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setProductos(List<Producto> productos){
        this.productos = productos;
    }
}
