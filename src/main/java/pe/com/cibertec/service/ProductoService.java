package pe.com.cibertec.service;

import java.util.List;
import pe.com.cibertec.model.ProductoEntity;

public interface ProductoService {
    List<ProductoEntity> buscarTodosProductos();
    ProductoEntity buscarProductoPorId(Integer id);
    void crearProducto(ProductoEntity producto);
    void actualizarProducto(ProductoEntity producto);
    void eliminarProducto(Integer id);
}
