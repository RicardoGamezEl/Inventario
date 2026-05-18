package com.ricardo.inventario.repository;
import com.ricardo.inventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends  JpaRepository<Producto,Long>{
}
