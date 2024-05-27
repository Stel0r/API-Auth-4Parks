package APIFourparks.Backend.Login.Controladores;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import APIFourparks.Backend.Login.Controladores.RequestBody.LoginBody;
import APIFourparks.Backend.Login.Controladores.RequestBody.RegistroClienteBody;
import APIFourparks.Backend.Login.Services.ConexionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

class InfoSendPassword {
    public String nameUser;
    public String password;
}

@RestController
@CrossOrigin
public class LoginController {

    

    private ConexionService DBServicio = ConexionService.obtenerServicio();


    @Operation( summary  = "Iniciar sesión de usuario", description =  "Este endpoint permite a un usuario iniciar sesión en el sistema.")
    @ApiResponses(value = {
        @ApiResponse( responseCode = "200", description   = "OK - Inicio de sesión exitoso"),
        @ApiResponse(responseCode = "403", description  = "Forbidden - Error al iniciar sesión")
    })
    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> loggearUsuario(@RequestBody LoginBody body){
        try {
            return ResponseEntity.ok().body(DBServicio.logearUsuario(body.user, body.password));
        } catch (Exception e) {
            return ResponseEntity.status(403).body(Map.of("mensaje",e.getMessage()));
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<Map<String,Object>> registrarUsuario(@RequestBody RegistroClienteBody body){
        try {
            return ResponseEntity.ok().body(DBServicio.registrarUsuario(body));
        } catch (Exception e) {
            return ResponseEntity.status(403).body(Map.of("message",e.getMessage()));
        }
    }

    @PatchMapping("/actualizarContra")
    public ResponseEntity<Map<String,Object>> cambiarContrasena(@RequestBody InfoSendPassword body){
        try {
            return ResponseEntity.ok().body(Map.of("message",DBServicio.cambiarContrasena(body.nameUser,body.password)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message",e.getMessage()));
        }
    }
    
}
