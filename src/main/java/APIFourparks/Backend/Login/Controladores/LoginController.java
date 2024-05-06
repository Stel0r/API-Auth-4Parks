package APIFourparks.Backend.Login.Controladores;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import APIFourparks.Backend.Login.Controladores.RequestBody.LoginBody;
import APIFourparks.Backend.Login.Controladores.RequestBody.RegistroClienteBody;
import APIFourparks.Backend.Login.Services.ConexionService;


@RestController
@CrossOrigin
@RequestMapping("")
public class LoginController {

    

    private ConexionService DBServicio = ConexionService.obtenerServicio();

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> loggearUsuario(@RequestBody LoginBody body){
        try {
            return ResponseEntity.ok().body(Map.of("mensaje",DBServicio.logearUsuario(body.user, body.password)));
        } catch (Exception e) {
            return ResponseEntity.status(403).body(Map.of("mensaje",e.getMessage()));
        }
    }

    @CrossOrigin
    @PostMapping("/registrar")
    public ResponseEntity<Map<String,Object>> registrarUsuario(@RequestBody RegistroClienteBody body){
        try {
            return ResponseEntity.ok().body(Map.of("message",DBServicio.logearUsuario(body)));
        } catch (Exception e) {
            return ResponseEntity.status(403).body(Map.of("message",e.getMessage()));
        }
    }

    
}
