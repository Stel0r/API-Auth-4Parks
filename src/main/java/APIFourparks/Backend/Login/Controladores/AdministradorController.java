package APIFourparks.Backend.Login.Controladores;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import APIFourparks.Backend.Login.Controladores.Repositorios.AdminRespository;
import APIFourparks.Backend.Login.Controladores.Repositorios.GerenteRepository;
import APIFourparks.Backend.Login.Controladores.Repositorios.UsuarioRepository;
import APIFourparks.Backend.Login.Logica.Gerente;
import APIFourparks.Backend.Login.Logica.Usuario;
import APIFourparks.Backend.Login.Services.ConexionService;
import APIFourparks.Backend.Login.Services.MailService;

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
    public String codParqueadero;
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

    private MailService mailService = MailService.obtenerServicio();

    private ConexionService DBservicio = ConexionService.obtenerServicio();

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

    @Transactional
    @PostMapping("/registrarGerente")
    public ResponseEntity<Map<String,Object>> registrarGerente(@RequestBody GerenteInterface body){
        
        try {
            Usuario user = usuarioRepository.findById(body.userName).orElse(null);
            if (user != null) {
                return ResponseEntity.badRequest().body(Map.of("Response","El usuario ya existe"));
            }
            user = new Usuario();
            user.userName = body.userName;
            user.pass = body.passSha;
            user.email = body.email;
            user.primerNombre = body.primerNombre;
            user.segundoNombre = body.segundoNombre;
            user.primerApellido = body.primerApellido;
            user.segundoApellido = body.segundoApellido;
            user.rol = "G";
            user.estado = "A";
            System.out.println(body.userName);
            usuarioRepository.save(user);
            Gerente gerente = new Gerente();
            gerente.user = body.userName;
            gerente.idGerente = body.codGerente;

            gerenteRepository.save(gerente);
            mailService.mandarCorreonuevoRegistro(body.email, body.userName, body.pass);
            DBservicio.asingnarParqueadero(body.codGerente, body.codParqueadero);
            gerenteRepository.insertarLoginGerente(user.userName);

            return ResponseEntity.ok().body(Map.of("Response","se ha agregado de forma exitosa al Gerente nuevo"));
        } catch (Exception e) {
            TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
            if(e.getMessage().contains("Duplicate entry")){
                if(e.getMessage().contains("UK_EMAIL")){
                    return ResponseEntity.badRequest().body(Map.of("Response","El correo ya esta registrado")); 
                }
            }
            return ResponseEntity.badRequest().body(Map.of("Response",e.getMessage()));
        }
        
    }



}
