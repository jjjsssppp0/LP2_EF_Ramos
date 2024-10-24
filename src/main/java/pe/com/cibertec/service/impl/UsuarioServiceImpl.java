package pe.com.cibertec.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import pe.com.cibertec.model.UsuarioEntity;
import pe.com.cibertec.repository.UsuarioRepository;
import pe.com.cibertec.service.UsuarioService;
import pe.com.cibertec.utils.Utilitarios;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public void crearUsuario(UsuarioEntity usuarioEntity, MultipartFile foto) {
        // 1. Guardar foto en el servidor
        String nombreFoto = Utilitarios.guardarImagen(foto);
        usuarioEntity.setUrlImagen(nombreFoto);

        // 2. Extraer Hash del password
        String passwordHash = Utilitarios.extraerHash(usuarioEntity.getPassword());
        usuarioEntity.setPassword(passwordHash);

        // 3. Guardar usuario en la base de datos
        usuarioRepository.save(usuarioEntity);
    }

    @Override
    public boolean validarUsuario(UsuarioEntity usuarioFormulario) {
        // 1. Recuperar el usuario por correo
        UsuarioEntity usuarioEncontrado = usuarioRepository.findByCorreo(usuarioFormulario.getCorreo());
        
        // Verificar si el correo existe en la base de datos
        if (usuarioEncontrado == null) {
            return false;
        }

        // 2. Validar password ingresada con el hash almacenado
        if (!Utilitarios.checkPassword(usuarioFormulario.getPassword(), usuarioEncontrado.getPassword())) {
            return false;
        }

        // 3. Login exitoso
        return true;
    }

    @Override
    public UsuarioEntity buscarUsuarioPorCorreo(String correo) {
        // Buscar y retornar el usuario por correo
        return usuarioRepository.findByCorreo(correo);
    }
}
