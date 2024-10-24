package pe.com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import pe.com.cibertec.model.ProductoEntity;
import pe.com.cibertec.service.ProductoService;
import pe.com.cibertec.service.UsuarioService;
import pe.com.cibertec.service.impl.PdfService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    
    private UsuarioService usuarioService;
    private PdfService pdfService;

    @GetMapping("/menu")
    public String listarProductos(Model model) {
        List<ProductoEntity> productos = productoService.buscarTodosProductos();
        model.addAttribute("productos", productos);
        return "menu";
    }

    @GetMapping("/ver/{id}")
    public String verProducto(@PathVariable("id") Integer id, Model model) {
        ProductoEntity producto = productoService.buscarProductoPorId(id);
        model.addAttribute("producto", producto);
        return "ver_producto";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("producto", new ProductoEntity());
        return "crear_producto";
    }

    @PostMapping("/crear")
    public String crearProducto(@ModelAttribute("producto") ProductoEntity producto) {
        productoService.crearProducto(producto);
        return "redirect:/productos/menu";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model) {
        ProductoEntity producto = productoService.buscarProductoPorId(id);
        model.addAttribute("producto", producto);
        return "editar_producto";
    }

    @PostMapping("/editar")
    public String actualizarProducto(@ModelAttribute("producto") ProductoEntity producto) {
        productoService.actualizarProducto(producto);
        return "redirect:/productos/menu";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Integer id) {
        productoService.eliminarProducto(id);
        return "redirect:/productos/menu";
    }

    @GetMapping("/generar_pdf")
    public ResponseEntity<InputStreamResource> generarPdf() throws IOException {
        List<ProductoEntity> productos = productoService.buscarTodosProductos();
        Map<String, Object> datosPdf = new HashMap<>();
        datosPdf.put("productos", productos);

        ByteArrayInputStream pdfBytes = pdfService.generarPdf("template_pdf", datosPdf);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=productos.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfBytes));
    }
}
