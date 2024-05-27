package APIFourparks.Backend.Login.Controladores;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import APIFourparks.Backend.Login.Controladores.Repositorios.AdminRespository;
import APIFourparks.Backend.Login.Controladores.Repositorios.GerenteRepository;
import APIFourparks.Backend.Login.Controladores.Repositorios.UsuarioRepository;
import APIFourparks.Backend.Login.Logica.Gerente;
import APIFourparks.Backend.Login.Logica.Usuario;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.transaction.Transactional;

class GerenteInterface{
    public String userName;
    public String pass;
    public String passSha;
    public String email;
    public String primerNombre;
    public String segundoNombre;
    public String primerApellido;
    public String segundoApellido;
    public String codGerente;
}

@RestController
@CrossOrigin
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    public AdminRespository adminRespository;
    @Autowired
    public UsuarioRepository usuarioRepository;
    
    @Autowired
    public GerenteRepository gerenteRepository;

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

    @PostMapping("/registrarGerente")
    @Transactional
    public ResponseEntity<Map<String,Object>> registrarGerente(@RequestBody GerenteInterface body){
        
        try {
            Usuario user = new Usuario();
            user.userName = body.userName;
            user.pass = body.pass;
            user.email = body.email;
            user.primerNombre = body.primerNombre;
            user.segundoNombre = body.segundoNombre;
            user.primerApellido = body.primerApellido;
            user.segundoApellido = body.segundoApellido;
            usuarioRepository.save(user);
            Gerente gerente = new Gerente();
            gerente.user = body.userName;
            gerente.idGerente = body.codGerente;
            gerenteRepository.save(gerente);
            return ResponseEntity.ok().body(Map.of("Response","se ha agregado de forma exitosa al Gerente nuevo"));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("Response",e.getMessage()));

        }
        
    }



}
