package pe.com.cibertec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import pe.com.cibertec.model.UsuarioEntity;
import pe.com.cibertec.service.UsuarioService;

@Controller
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping("/registrar_usuario")
    public String mostrarRegistrarUsuario(Model model) {
        model.addAttribute("usuario", new UsuarioEntity());
        return "registrar_usuario";
    }

    @PostMapping("/registrar_usuario")
    public String registrarUsuario(@ModelAttribute("usuario") UsuarioEntity usuarioEntity,
                                   Model model, @RequestParam("foto") MultipartFile foto) {
        usuarioService.crearUsuario(usuarioEntity, foto);
        return "registrar_usuario";
    }

    @GetMapping("/")
    public String mostrarLogin(Model model) {
        model.addAttribute("usuario", new UsuarioEntity());
        return "login";
    }

    @GetMapping("/menu")
    public String mostrarMenu() {
        return "redirect:/productos/menu";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("usuario") UsuarioEntity usuarioFormulario,
                        Model model, HttpSession session) {
        boolean usuarioValidado = usuarioService.validarUsuario(usuarioFormulario);
        if (usuarioValidado) {
            session.setAttribute("usuario", usuarioFormulario.getCorreo());
            return "redirect:/menu";
        }

        model.addAttribute("loginInvalido", "No existe el usuario");
        model.addAttribute("usuario", new UsuarioEntity());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
