package APIFourparks.Backend.Login.Controladores;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import APIFourparks.Backend.Login.Controladores.Repositorios.AdminRespository;
import APIFourparks.Backend.Login.Controladores.Repositorios.UsuarioRepository;
import APIFourparks.Backend.Login.Logica.Usuario;

@RestController
@CrossOrigin
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    public AdminRespository adminRespository;
    @Autowired
    public UsuarioRepository usuarioRepository;
    
    @GetMapping("/obtener/{user}")
    public Map<String,Object> obtenerAdmin(@PathVariable(value = "user") String user){
        return adminRespository.obtenerAdmin(user);
    }

    @GetMapping("/obtenerUsuarios")
    public List<Map<String,Object>> obtenerUsuarios(){
        System.out.println("aaaaaa");
        return adminRespository.obtenerUsuariosEstado();
    }

    @GetMapping("/desbloquear/{usuario}")
    public Map<String,Object> desbloquearusuario(@PathVariable(value = "usuario") String user){
        usuarioRepository.desbloquear(user);
        return Map.of("response","el usuario se ha desbloqueado");
    }


}
