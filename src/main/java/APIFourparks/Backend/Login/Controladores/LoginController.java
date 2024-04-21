package APIFourparks.Backend.Login.Controladores;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import APIFourparks.Backend.Login.Controladores.RequestBody.LoginBody;



@RestController
@RequestMapping("")
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> loggearUsuario(@RequestBody LoginBody body){
        return ResponseEntity.ok().body(Map.of("mensaje","el usuario es "+body.user+" y la contraseña ultra super secreta es "+body.password));
    }
    
}
