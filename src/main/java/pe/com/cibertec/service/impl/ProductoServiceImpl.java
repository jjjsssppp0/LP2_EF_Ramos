package pe.com.cibertec.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.cibertec.model.ProductoEntity;
import pe.com.cibertec.repository.ProductoRepository;
import pe.com.cibertec.service.ProductoService;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<ProductoEntity> buscarTodosProductos() {
        return productoRepository.findAll(); // Retorna todos los productos
    }

    @Override
    public ProductoEntity buscarProductoPorId(Integer id) {
        return productoRepository.findById(id).orElse(null); // Retorna el producto por ID, o null si no existe
    }

    @Override
    public void crearProducto(ProductoEntity producto) {
        productoRepository.save(producto); // Guarda el nuevo producto
    }

    @Override
    public void actualizarProducto(ProductoEntity producto) {
        productoRepository.save(producto); // Actualiza el producto
    }

    @Override
    public void eliminarProducto(Integer id) {
        productoRepository.deleteById(id); // Elimina el producto por ID
    }
}
